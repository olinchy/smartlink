package com.zte.smartlink.log;

import com.zte.mos.logging_service.Log;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by zhangwei on 16-9-6.
 */
public class ThreadsMonitorLog implements Log
{
    private static final int THRESHOLD = 850;//打印门限，超过850个线程则全部打印

    public ArrayList<String[]> threadNameLst = new ArrayList<String[]>();
    public String processName = null;

    public ThreadsMonitorLog(String procName){
        processName = procName;
        Map<Thread, StackTraceElement[]> maps = Thread.getAllStackTraces();
        for (Thread thread : maps.keySet()){
            threadNameLst.add(new String[]{thread.getName(), thread.getState().toString()});
        }
    }

    @Override
    public String toString()
    {
        StringBuffer res = new StringBuffer(100);
        res.append(processName + " Thread list size is ").append(threadNameLst.size())
                .append(", Runnable threads size is ").append(getRunnableSize());
        if(threadNameLst.size() > THRESHOLD){
            res.append("\r\n").append(processName + " Threads are ")
                    .append("\r\n").append(printAllThreads())
                    .append("Print " + processName + "all threads end.");
        }
        return res.toString();
    }

    private int getRunnableSize(){
        int res = 0;
        for (String[] tmp: threadNameLst){
            if(tmp[1].equalsIgnoreCase("RUNNABLE")){
                res++;
            }
        }
        return res;
    }

    private String printAllThreads(){
        StringBuffer res = new StringBuffer(100);
        for(String[] tmp : threadNameLst){
            res.append(tmp[0]).append("(").append(tmp[1]).append(")").append("\r\n");
        }
        return res.toString();
    }
}
