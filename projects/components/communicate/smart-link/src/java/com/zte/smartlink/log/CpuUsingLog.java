package com.zte.smartlink.log;

import com.zte.mos.logging_service.Log;

/**
 * Created by zhangwei on 16-9-6.
 */
public class CpuUsingLog implements Log
{
    private static final long serialVersionUID = 2323464946030120015L;
    public final String using;
    public final String processName;

    public CpuUsingLog(String using)
    {
        this(using,"ems");
    }
    public CpuUsingLog(String using,String processName)
    {
        this.using = parseRate(using);
        this.processName=processName;
    }

    @Override
    public String toString()
    {
        return processName+" CPU utilization is " + using;
    }

    private static String parseRate(String rate){
        String rateInt = rate.substring(rate.indexOf("=")+1, rate.indexOf("%"));
        if(rateInt.equals("")){
            rateInt = "0";
        }
        return rateInt + "%";
    }
}
