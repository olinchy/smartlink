package com.zte.smartlink.testlog;

import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.MOSRMIException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.smartlink.UrlAddress;

import java.util.ArrayList;

/**
 * Created by zhangwei on 16-11-30.
 */
class AddressMock extends UrlAddress
{
    public AddressMock(String url)
    {
        super(url);
    }

    @Override
    public Result on(MoMsg msg) throws MOSException
    {
        return null;
    }

    @Override
    public Result on(ArrayList<? extends MoMsg> msg) throws MOSRMIException
    {
        return null;
    }
}
