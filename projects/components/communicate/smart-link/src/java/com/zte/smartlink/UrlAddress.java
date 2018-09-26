package com.zte.smartlink;

import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.MOSRMIException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.jms.JMSRegister;
import com.zte.mos.jms.JMSSender;
import com.zte.mos.message.MsgService;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.util.basic.Logger;
import com.zte.smartlink.deliver.MsgMask;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class UrlAddress implements Address, Comparable<UrlAddress>, Serializable
{
    private static final long serialVersionUID = 1L;
    protected final String url;

    public UrlAddress(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        UrlAddress that = (UrlAddress) o;
        return getUrl().equals(that.getUrl());
    }

    @Override
    public int hashCode()
    {
        return 0;
    }

    @Override
    public int compareTo(UrlAddress other)
    {
        return getUrl().compareTo(other.getUrl());
    }

    @Override
    public String toString()
    {
        return url;
    }

    @Override
    public Result on(final MoMsg msg) throws MOSException
    {
        try
        {
            if (MsgMask.mask(msg).isRpc())
            {
                return sendViaRMI(msg);
            }
            else
            {
                // TODO: 17-2-17 send async msg via jms
                JMSSender.send(toString(), msg);
                return new Successful();
            }
        }
        catch (RemoteException e)
        {
            throw new MOSRMIException(e, toString());
        }
    }

    private Result sendViaRMI(final MoMsg msg) throws MOSRMIException
    {
        final MsgService service;
        try
        {
            Logger.logger(getClass()).debug(
                    "send " + msg + " to " + this);
            service = (MsgService) Naming.lookup(getUrl());
            return Counter.count(new Executing()
            {
                @Override
                public Result<?> execute() throws MOSException, RemoteException
                {
                    return service.onMsg(msg);
                }

                @Override
                public MoMsg getMsg()
                {
                    return msg;
                }
            }, "sent");
        }
        catch (Throwable throwable)
        {
            throw new MOSRMIException(throwable, toString());
        }
    }

    @Override
    public Result on(ArrayList<? extends MoMsg> msg) throws MOSRMIException
    {
        try
        {
            Logger.logger(getClass()).debug(
                    "send " + msg + " to " + this);
            // TODO: 17-2-17  send indication in packs via jms
            JMSSender.send(toString(), msg.toArray(new MoMsg[msg.size()]));
            return new Successful();
        }
        catch (Throwable throwable)
        {
            throw new MOSRMIException(throwable, toString());
        }
    }

    @Override
    public void publish(MsgService remote) throws MalformedURLException, RemoteException, MOSException
    {
        Naming.rebind(url, remote);
        JMSRegister.register(url, remote);
    }

    @Override
    public Address runWith(String name)
    {
        return new UrlAddress(url + "/" + name);
    }

    @Override
    public boolean isOn()
    {
        try
        {
            Naming.lookup(url);
            return true;
        }
        catch (Throwable e)
        {
            return false;
        }
    }

    @Override
    public void terminate() throws MOSException
    {
        JMSRegister.unregister(url);
    }
}
