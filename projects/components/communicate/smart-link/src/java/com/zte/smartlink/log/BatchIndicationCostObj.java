package com.zte.smartlink.log;

/**
 * Created by zhangwei on 16-10-24.
 */
public class BatchIndicationCostObj extends IndAndEventCostObj
{
    public String appName = "";
    public int moCount = 0;

    public BatchIndicationCostObj(String appName, int moCount, long runCostTime, long scheduleCostTime)
    {
        super(runCostTime, scheduleCostTime);
        this.appName = appName;
        this.moCount = moCount;
    }

}
