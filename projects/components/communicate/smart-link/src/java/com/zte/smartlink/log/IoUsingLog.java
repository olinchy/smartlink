package com.zte.smartlink.log;

import com.zte.mos.logging_service.Log;

/**
 * Created by meng on 17-8-23.
 */
public class IoUsingLog implements Log {
    public final String using;

    public IoUsingLog(String using){
        this.using = parseRate(using);
    }

    @Override
    public String toString()
    {
        return "IO utilization is " + using;
    }

    private static String parseRate(String rate){
        if(rate.equals("")){
            return "0%";
        } else {
            return rate + "%";
        }
    }
}
