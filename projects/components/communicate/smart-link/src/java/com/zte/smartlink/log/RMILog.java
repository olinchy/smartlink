package com.zte.smartlink.log;

import com.zte.mos.logging_service.Log;

/**
 * Created by olinchy on 16-7-22.
 */
public class RMILog implements Log
{
    public RMILog(int count)
    {
        this.count = count;
    }

    public final int count;

    @Override
    public String toString()
    {
        return "rmi connection count{" +
                "count=" + count +
                '}';
    }
}
