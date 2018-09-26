/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.zte.smartlink;

import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.util.basic.Log4JRegister;
import com.zte.mos.util.tools.Prop;
import com.zte.smartlink.deliver.MsgServiceInCacheAdaptor;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 17-1-9.
 */
public class TestMsgServiceWithCache
{
    @BeforeClass
    public static void setUp() throws Exception
    {
        Properties prop;
        Prop.set(prop = new Properties());

        prop.put("storage_path", "./msgdb/");
        prop.put("cache_size", "15");
        Log4JRegister.init();
    }

    private static class ObserverCounter
    {
        public ObserverCounter(int expected)
        {
            this.expected = expected;
        }

        private int count = 0;
        private int expected;

        public synchronized void update()
        {
            count++;
        }

        public boolean finished()
        {
            return count == expected;
        }

        public int getCount()
        {
            return count;
        }
    }

    private ObserverCounter observerGroup;
    private ObserverCounter observerMsg;

    public TestMsgServiceWithCache() throws RemoteException, BerkeleyDBException
    {
    }

    MsgServiceInCacheAdaptor service = new MsgServiceInCacheAdaptor()
    {
        @Override
        public Result<?> onSingleMsg(MoMsg msg) throws MOSException, RemoteException
        {
            try
            {
                Thread.sleep(1);
                if (observerMsg != null)
                {
                    observerMsg.update();
                    if (observerMsg.finished())
                        logger.info("finished all singleMsg at " + observerMsg.getCount() + " " + new Date());
                }
            }
            catch (InterruptedException e)
            {
                logger(TestMsgServiceWithCache.class).warn("", e);
            }
            return new Successful();
        }

        @Override
        public Result<?> onBatchMsg(ArrayList<? extends MoMsg> msg) throws MOSException, RemoteException
        {
            try
            {
                Thread.sleep(1000);
                if (observerGroup != null)
                {
                    observerGroup.update();
                    if (observerGroup.finished())
                    {
                        logger.info("finished all group at " + observerGroup.getCount() + " " + new Date());
                    }
                }
            }
            catch (InterruptedException e)
            {
                logger(TestMsgServiceWithCache.class).warn("", e);
            }
            return new Successful();
        }
    };

    @Test
    public void test() throws Exception
    {
        observerGroup = new ObserverCounter(100);
        observerMsg = new ObserverCounter(100 * 1000);

        for (int i = 0; i < 100; i++)
        {
            ArrayList<MoMsg> msgList = new ArrayList<MoMsg>();

            for (int j = 0; j < 1000; j++)
            {
                msgList.add(new StubMsg(i, j));
            }

            service.onMsg(msgList);
        }

        Thread.sleep(600000);
    }

    @Test
    public void test_restart() throws Exception
    {
        System.out.println("started");

        Thread.sleep(100000);
    }
}
