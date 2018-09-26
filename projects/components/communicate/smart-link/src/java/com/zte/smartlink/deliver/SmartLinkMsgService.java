package com.zte.smartlink.deliver;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.smartlink.Address;

/**
 * Created by root on 14-11-6.
 */
public class SmartLinkMsgService
{
    private static Deliver deliver = new DefaultDeliver();

    public static Result send(String msgSrc, MoMsg msg) throws MOSException
    {
        return deliver.send(msgSrc, msg);
    }

    public static Result send(String msgSrc, MoMsg msg, Address... exclude) throws MOSException
    {
        return deliver.send(msgSrc, msg, exclude);
    }

    public static void setDeliver(Deliver userDefine)
    {
        SmartLinkMsgService.deliver = userDefine;
    }
}
