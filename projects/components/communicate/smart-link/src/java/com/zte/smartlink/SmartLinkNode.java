package com.zte.smartlink;

import com.zte.mos.exception.MOSException;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by olinchy on 15-5-13.
 */
public abstract class SmartLinkNode implements Dependable
{
    public void start(String[] args) throws MOSException
    {

    }

    public Remote getService() throws MOSException, RemoteException
    {
        return null;
    }

    @Override
    public String getName()
    {
        return null;
    }

    public void post() throws MOSException
    {

    }

    @Override
    public String depends()
    {
        return "";
    }
}
