package com.zte.smartlink.log;

import com.zte.mos.logging_service.Log;

/**
 * Created by olinchy on 16-7-22.
 */
public class MemoryLog implements Log
{
    public MemoryLog(String processName, long used, long total)
    {
        this.processName = processName;
        this.used = used;
        this.total = total;
    }

    public final long used;
    public final long total;
    public final String processName;

    @Override
    public String toString()
    {
        return processName + " current memory{" +
                "used=" + used +
                ", total=" + total +
                '}';
    }
}
