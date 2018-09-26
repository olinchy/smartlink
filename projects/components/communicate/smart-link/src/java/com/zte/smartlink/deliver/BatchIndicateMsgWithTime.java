package com.zte.smartlink.deliver;

import com.zte.mos.util.msg.IndicateMsg;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by zhangwei on 16-10-24.
 */
class BatchIndicateMsgWithTime {
    public final ArrayList<IndicateMsg> msgs;
    private final Date intoListTime;
    private Date startRunTime = new Date();
    private Date endRunTime = new Date();

    BatchIndicateMsgWithTime(ArrayList<IndicateMsg> msgs, Date intoListTime)
    {
        this.msgs = msgs;
        this.intoListTime = intoListTime;
    }

    void setStartRunTime(Date time){
        this.startRunTime = time;
    }

    void setEndRunTime(Date time){
        this.endRunTime = time;
    }

    long getScheduleCostTime(){
        return startRunTime.getTime() - intoListTime.getTime();
    }

    long getRunningTime(){
        return endRunTime.getTime() - startRunTime.getTime();
    }
}
