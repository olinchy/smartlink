package com.zte.smartlink;

import com.zte.mos.exception.MOSException;
import com.zte.mos.message.Result;

import java.rmi.RemoteException;

/**
 * Created by olinchy on 15-12-31.
 */
public interface Sending
{
    Result send() throws MOSException, RemoteException;
}
