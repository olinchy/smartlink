package com.zte.smartlink.deliver;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.smartlink.Address;

/**
 * Created by root on 14-11-6.
 */
public class DefaultDeliver implements Deliver
{
    @Override
    public Result send(String msgSrc, MoMsg msg) throws MOSException
    {
        return new DeliverService(msgSrc).send(msg);
    }

    @Override
    public Result send(String msgSrc, MoMsg msg, Address... exclude) throws MOSException
    {
        return new DeliverService(msgSrc).send(msg, exclude);
    }
}
