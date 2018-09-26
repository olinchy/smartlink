/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.smartlink.deliver;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.CursorConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.MsgServiceAdapter;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.util.msg.IndicateMsg;
import com.zte.mos.util.tools.IDAllocator;

/**
 * Created by olinchy on 17-1-9.
 */
public abstract class MsgServiceInCacheAdaptor extends MsgServiceAdapter {
    public MsgServiceInCacheAdaptor() throws RemoteException, BerkeleyDBException {
        holder = new IdBasedObjectHolder<>(msgServiceName, MsgHolder.class);

        final long[] index = {0};
        holder.all(indexer -> {
            CursorConfig config = new CursorConfig();
            config.setReadUncommitted(true);
            Cursor cursor = null;
            try {

                cursor = indexer.getDatabase().openCursor(null, config);
                DatabaseEntry keyEntry = new DatabaseEntry();
                DatabaseEntry dataEntry = new DatabaseEntry();
                while (cursor.getNext(keyEntry, dataEntry, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
                    index[0] = Long.parseLong(StringBinding.entryToString(keyEntry));
                    logger.test("adding " + index[0] + " into batch queue");
                    batchService.execute(new CachedMsgTask(index[0], singleService));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                allocator = new IDAllocator((int) index[0], 20000000);
            }
        });
    }

    private IdBasedObjectHolder<MsgHolder> holder;
    private IDAllocator allocator;
    private Object lock = new Object();

    @Override
    public Result<?> onMsg(final ArrayList<? extends MoMsg> msg) throws MOSException {
        batchService.execute(new CachedMsgTask(save(msg), singleService));

        return new Successful<>();
    }

    private long save(ArrayList<? extends MoMsg> msg) throws MOSException {
        synchronized (lock) {
            MsgHolder holder = new MsgHolder(allocator.allocate(), msg);
            this.holder.put(holder);
            return holder.id();
        }
    }

    @Override
    public Result<?> onMsg(final MoMsg msg) throws RemoteException, MOSException {
        if (msg instanceof IndicateMsg) {
            return new Successful<>();
        } else {
            return super.onMsg(msg);
        }
    }

    private class CachedMsgTask implements Runnable {
        public CachedMsgTask(long key, ExecutorService singleService) {
            this.key = key;
            this.singleService = singleService;
        }

        private final long key;
        private final ExecutorService singleService;

        @Override
        public void run() {
            try {
                MsgHolder holder = MsgServiceInCacheAdaptor.this.holder.poll(key);
                onBatchMsg(holder.getMsg());
                for (MoMsg moMsg : holder.getMsg()) {
                    singleService.execute(new SingleMsgTask(moMsg));
                }
                MsgServiceInCacheAdaptor.this.holder.remove(key);
            } catch (Throwable e) {
                logger.warn("fetch task failed!", e);
            }
        }
    }
}
