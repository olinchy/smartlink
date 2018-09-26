package com.zte.smartlink.deliver;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.smartlink.Address;

/**
 * Created by root on 14-11-6.
 */
public interface Deliver
{
    Result send(String msgSrc, MoMsg msg) throws MOSException;

    Result send(String msgSrc, MoMsg msg, Address... exclude) throws MOSException;
}
