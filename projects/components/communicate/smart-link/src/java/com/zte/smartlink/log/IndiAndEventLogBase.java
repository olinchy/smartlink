package com.zte.smartlink.log;

import com.zte.mos.logging_service.Log;

/**
 * Created by zhangwei on 16-10-20.
 */
public abstract class IndiAndEventLogBase implements Log
{
    public static final String EVENT = "Event";
    public static final String BATCHINDICATION = "BatchIndication";

    public String processName = "";
    public long meanRunCostTime = 0;
    public long meanScheduleCostTime = 0;
    public int size = 0;

    public void resetData(){
        meanRunCostTime = 0;
        meanScheduleCostTime = 0;
        size = 0;
        clearEventsOrIndications();
    }

    public abstract void insert(IndAndEventCostObj obj);
    protected abstract String msgType();
    protected abstract String logExtraInfo();
    protected abstract void computeMeanCostTime();
    protected abstract void clearEventsOrIndications();

    private String buildLogString(){
        StringBuffer res = new StringBuffer(100);
        String totalInfo = msgType() + " msgs count is " + size + ", "
                + "runCostTime is " + meanRunCostTime
                + "ms, scheduleCostTime is " + meanScheduleCostTime + "ms";
        String extraInfo = logExtraInfo();
        res.append(totalInfo).append(extraInfo);
        return res.toString();
    }

    @Override
    public String toString()
    {
        computeMeanCostTime();
        return  buildLogString();
    }
}
