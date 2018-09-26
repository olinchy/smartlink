package com.zte.smartlink.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangwei on 16-10-24.
 */
public class BatchIndicationLog extends IndiAndEventLogBase
{
    private final static String RUN_COST = "runCost";
    private final static String SCHEDULE_COST = "scheduleCost";

    private static BatchIndicationLog ins = new BatchIndicationLog();

    private BatchIndicationLog(){
    }

    public HashMap<String ,List<BatchIndicationCostObj>> indicationMap = new HashMap<String ,List<BatchIndicationCostObj>>();

    public static BatchIndicationLog instance(String processName){
        if(ins == null){
            ins = new BatchIndicationLog();
        }
        ins.processName = processName;
        return ins;
    }

    public static BatchIndicationLog instance(){
        return ins;
    }

    @Override
    public void insert(IndAndEventCostObj obj)
    {
        BatchIndicationCostObj batchObj = (BatchIndicationCostObj)obj;
        String address = batchObj.appName;
        if(!indicationMap.containsKey(address)){
            List<BatchIndicationCostObj> lst = Collections.synchronizedList(new ArrayList<BatchIndicationCostObj>());
            lst.add(batchObj);
            indicationMap.put(address, lst);
        }else {
            indicationMap.get(address).add(batchObj);
        }
    }

    @Override
    protected String msgType()
    {
        return processName + " " + BATCHINDICATION;
    }

    @Override
    protected String logExtraInfo()
    {
        HashMap<String ,List<BatchIndicationCostObj>> mapClone = cloneIndiMap();
        StringBuffer res = new StringBuffer(100);
        for(String address : mapClone.keySet()){
            List<BatchIndicationCostObj> lst = mapClone.get(address);
            if(lst.size() > 0){
                HashMap<String, Long> costMap = getCostValue(lst);
                long runCost = costMap.get(RUN_COST);
                long scheduleCost = costMap.get(SCHEDULE_COST);
                res.append("\r\n").append(address)
                        .append(": meanRunCostTime is ").append(runCost).append("ms")
                        .append(", meanScheduleCost is ").append(scheduleCost).append("ms");
            }
        }
        return res.toString();
    }

    @Override
    protected void computeMeanCostTime()
    {
        HashMap<String ,List<BatchIndicationCostObj>> mapClone = cloneIndiMap();
        ArrayList<BatchIndicationCostObj> allIndications = new ArrayList<BatchIndicationCostObj>();
        for (String address : mapClone.keySet()){
            for(BatchIndicationCostObj tmp : mapClone.get(address)){
                allIndications.add(tmp);
            }
        }
        long allrunTime = 0;
        long allscheduleTime = 0;
        for(BatchIndicationCostObj tmp : allIndications){
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
        indicationMap = new HashMap<String ,List<BatchIndicationCostObj>>();
    }

    private HashMap<String, Long> getCostValue(List<BatchIndicationCostObj> lst){
        long runCostSum = 0;
        long scheduleCostSum = 0;
        for (BatchIndicationCostObj tmp : lst){
            runCostSum += tmp.runCostTime;
            scheduleCostSum += tmp.scheduleCostTime;
        }
        HashMap<String, Long> res = new HashMap<String, Long>();
        res.put(RUN_COST, Long.valueOf(runCostSum));
        res.put(SCHEDULE_COST, Long.valueOf(scheduleCostSum));
        return res;
    }

    private HashMap<String ,List<BatchIndicationCostObj>> cloneIndiMap(){
        HashMap<String ,List<BatchIndicationCostObj>> res = new HashMap<String ,List<BatchIndicationCostObj>>();
        for(String address : indicationMap.keySet()){
            List<BatchIndicationCostObj> tmpLst = cloneLst(indicationMap.get(address));
            res.put(address, tmpLst);
        }
        return res;
    }

    private List<BatchIndicationCostObj> cloneLst(List<BatchIndicationCostObj> lst){
        List<BatchIndicationCostObj> res = Collections.synchronizedList(new ArrayList<BatchIndicationCostObj>());
        for (BatchIndicationCostObj tmp : lst){
            res.add(tmp);
        }
        return res;
    }
}
