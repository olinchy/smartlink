package com.zte.smartlink;

import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.MOSRMIException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.MsgService;
import com.zte.mos.message.Result;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Address
{
    Result on(MoMsg msg) throws MOSException;

    Result on(ArrayList<? extends MoMsg> msg) throws MOSRMIException;

    void publish(MsgService remote) throws MalformedURLException, RemoteException, MOSException;

    Address runWith(String name);

    boolean isOn();

    void terminate() throws MOSException;
}
