package com.zte.smartlink.deliver;

import com.zte.mos.util.msg.IndicateMsg;

import java.util.Date;

/**
 * Created by zhangwei on 16-10-20.
 */
class IndicateMsgWithTime
{
    public final IndicateMsg msg;
    private final Date intoListTime;
    private Date startRunTime = new Date();
    private Date endRunTime = new Date();

    IndicateMsgWithTime(IndicateMsg msg, Date intoListTime)
    {
        this.msg = msg;
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
