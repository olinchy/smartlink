package com.zte.smartlink.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhangwei on 16-10-19.
 */
public class EventMonitorLog extends IndiAndEventLogBase
{
    private static EventMonitorLog ins = new EventMonitorLog();

    private EventMonitorLog(){
    }

    public List<IndAndEventCostObj> events = Collections.synchronizedList(new ArrayList<IndAndEventCostObj>());

    public static EventMonitorLog instance(String processName){
        if(ins == null){
            ins = new EventMonitorLog();
        }
        ins.processName = processName;
        return ins;
    }

    public static EventMonitorLog instance(){
        return ins;
    }

    @Override
    public void insert(IndAndEventCostObj obj)
    {
        events.add(obj);
    }

    @Override
    protected void computeMeanCostTime(){
        long allrunTime = 0;
        long allscheduleTime = 0;
        for(IndAndEventCostObj tmp : events){
            allrunTime += tmp.runCostTime;
            allscheduleTime += tmp.scheduleCostTime;
            size++;
        }
        if(size > 0){
            meanRunCostTime = allrunTime / size;
            meanScheduleCostTime = allscheduleTime / size;
        }
    }

    @Override
    protected void clearEventsOrIndications()
    {
        events = Collections.synchronizedList(new ArrayList<IndAndEventCostObj>());
    }

    @Override
    protected String msgType()
    {
        return processName + " " + EVENT;
    }

    @Override
    protected String logExtraInfo()
    {
        return "";
    }
}
