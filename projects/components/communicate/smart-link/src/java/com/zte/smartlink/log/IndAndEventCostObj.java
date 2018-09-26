package com.zte.smartlink.log;

import java.io.Serializable;

/**
 * Created by zhangwei on 16-10-20.
 */
public class IndAndEventCostObj implements Serializable
{
    public long runCostTime = 0;
    public long scheduleCostTime = 0;

    public IndAndEventCostObj(long runCostTime, long scheduleCostTime){
        this.runCostTime = runCostTime;
        this.scheduleCostTime = scheduleCostTime;
    }
}
