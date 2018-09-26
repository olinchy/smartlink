package com.zte.smartlink.deliver;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.zte.mos.exception.MOSException;
import com.zte.mos.util.Singleton;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.IndicateMsg;
import com.zte.smartlink.Address;
import com.zte.smartlink.UrlAddress;
import com.zte.smartlink.addressbook.client.LocalAddressBook;
import com.zte.smartlink.log.BatchIndicationCostObj;
import com.zte.smartlink.log.BatchIndicationLog;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 15-5-18.
 */
public class IndicationSender {
    private final static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            10, 20, 5,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
            r -> new Thread(r, "Indication Sending Thread"));
    private static HashMap<Address, BatchSendingTask> mapBatchTask = new HashMap<>();
    private static Timer timer = new Timer("Indication Sending Timer");

    public static void addJob(HashMap<Address, ArrayList<IndicateMsg>> jobList) {
        for (Address address : jobList.keySet()) {
            addPackedJob(address, jobList.get(address));
        }
    }

    private static void addPackedJob(Address address, ArrayList<IndicateMsg> indicateMsgs) {
        logger(IndicationSender.class).debug(" adding " + indicateMsgs + " to task of " + address);
        BatchSendingTask task = mapBatchTask.get(address);
        if (task == null) {
            task = new BatchSendingTask();
            TimerTask timerTask = task.getTimerTask(address);
            timer.schedule(timerTask, 20, 10);
            mapBatchTask.put(address, task);
        }
        task.put(indicateMsgs);
    }

    private static class BatchSendingTask {
        public BatchSendingTask() {
        }

        private final static Logger logger = logger(BatchSendingTask.class);
        private LinkedBlockingQueue<BatchIndicateMsgWithTime> msgList = new LinkedBlockingQueue<>();

        public TimerTask getTimerTask(final Address address) {
            return new TimerTask() {
                @Override
                public void run() {
                    threadPool.execute(() -> {
                        try {
                            if (!Singleton.getInstance(LocalAddressBook.class).contains(address)) {
                                logger.info(
                                        "this address " + address
                                                + " is not exist any more, cancel sending task timer");
                                this.cancel();
                                address.terminate();
                                mapBatchTask.remove(address);
                                return;
                            }
                        } catch (MOSException e) {
                            logger.warn("singleton throws exception", e);
                        }

                        BatchIndicateMsgWithTime msgWithTime = msgList.poll();
                        if (msgWithTime != null) {
                            try {
                                msgWithTime.setStartRunTime(new Date());
                                ArrayList<IndicateMsg> msg = msgWithTime.msgs;

                                logger.debug("ind " + msg.toString() + " to " + address.toString() + " in package");
                                address.on(msg);

                                msgWithTime.setEndRunTime(new Date());
                                insertIntoLogMonitor(msgWithTime);
                            } catch (Throwable throwable) {
                                logger.error(
                                        "send " + msgWithTime.msgs.toString() + " to " + address.toString()
                                                + "in package caught exception!",
                                        throwable);
                            }
                        }
                    });
                }

                private void insertIntoLogMonitor(BatchIndicateMsgWithTime msgWithTime) {
                    if (address instanceof UrlAddress) {
                        BatchIndicationCostObj obj = new BatchIndicationCostObj(
                                ((UrlAddress) address).getUrl(),
                                msgWithTime.msgs.size(),
                                msgWithTime.getRunningTime(),
                                msgWithTime.getScheduleCostTime());
                        BatchIndicationLog.instance().insert(obj);
                    }
                }
            };
        }

        public void put(ArrayList<IndicateMsg> indicateMsgs) {
            try {
                msgList.put(new BatchIndicateMsgWithTime(indicateMsgs, new Date()));
            } catch (InterruptedException ignored) {
            }
        }
    }
}
