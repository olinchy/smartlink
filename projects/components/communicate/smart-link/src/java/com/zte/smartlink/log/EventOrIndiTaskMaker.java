package com.zte.smartlink.log;

import com.zte.smartlink.LoggerToRemote;

import java.util.TimerTask;

/**
 * Created by zhangwei on 16-11-30.
 */
public class EventOrIndiTaskMaker
{
    public static TimerTask getEventMonitorTask(final String processName){
        return new TimerTask()
        {
            @Override
            public void run()
            {
                EventMonitorLog log = EventMonitorLog.instance(processName);
                LoggerToRemote.log(log);
                log.resetData();
            }
        };
    }

    public static TimerTask getBatchIndicationMonitorTask(final String processName){
        return new TimerTask()
        {
            @Override
            public void run()
            {
                BatchIndicationLog log = BatchIndicationLog.instance(processName);
                LoggerToRemote.log(log);
                log.resetData();
            }
        };
    }
}
