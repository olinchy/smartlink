package com.zte.smartlink;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;

import java.rmi.RemoteException;

/**
 * Created by olinchy on 16-6-21.
 */
public interface Executing
{
    Result<?> execute() throws MOSException, RemoteException;
    MoMsg getMsg();
}
