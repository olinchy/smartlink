package com.zte.smartlink.deliver;

import com.zte.smartlink.Sending;
import com.zte.smartlink.log.EventMonitorLog;
import com.zte.smartlink.log.IndAndEventCostObj;

import java.util.Date;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by zhangwei on 16-10-19.
 */
public class EventAsyncSendThread implements Runnable{
    private Date instanceDate = new Date();
    private Date runStart = new Date();
    private Date runEnd = new Date();

    private Sending sending = null;

    EventAsyncSendThread(Sending sending){
        this.sending = sending;
    }

    @Override
    public void run()
    {
        try
        {
            runStart = new Date();
            sending.send();
            runEnd = new Date();
            EventMonitorLog.instance().insert(new IndAndEventCostObj(getRunTimeCost(), getScheduleTimeCost()));
        }
        catch (Throwable e)
        {
            logger(this.getClass()).warn("send msg " + sending.toString() + " caught exception!", e);
        }
    }

    private long getScheduleTimeCost(){
        return runStart.getTime() - instanceDate.getTime();
    }

    private long getRunTimeCost(){
        return runEnd.getTime() - runStart.getTime();
    }
}

