package com.zte.smartlink.log;

import com.zte.mos.inf.MoMsg;
import com.zte.mos.logging_service.Log;

import java.io.Serializable;

/**
 * Created by olinchy on 16-7-20.
 */
public class MsgHandlingLog implements Log, Serializable
{
    public MsgHandlingLog(
            MoMsg msg, long timeCost, String name)
    {
        this.msg = msg;
        this.timeCost = timeCost;
        this.name = name;
    }

    public final MoMsg msg;
    public final long timeCost;
    public final String name;

    @Override
    public String toString()
    {
        return "MsgHandlingLog{" +
                "msg=" + msg +
                ", timeCost=" + timeCost +
                ", name='" + name + '\'' +
                '}';
    }
}
