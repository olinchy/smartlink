package com.zte.smartlink.deliver;

import com.zte.mos.exception.MOSException;
import com.zte.mos.message.Result;
import com.zte.smartlink.Sending;

import java.rmi.RemoteException;

/**
 * Created by olinchy on 15-12-31.
 */
public class RpcMethod implements SendMethod
{
    @Override
    public Result send(Sending sending) throws MOSException, RemoteException
    {
        return sending.send();
    }
}
