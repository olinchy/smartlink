/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.zte.smartlink;

import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.MOSRMIException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.jms.JMSRegister;
import com.zte.mos.jms.JMSSender;
import com.zte.mos.message.MsgService;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

/**
 * Created by olinchy on 17-3-6.
 */
public class JMSAddress extends UrlAddress
{
    public JMSAddress(String url)
    {
        super(url);
    }

    @Override
    public Result on(MoMsg msg) throws MOSException
    {
        try
        {
            JMSSender.send(toString(), msg);
            return new Successful();
        }
        catch (RemoteException e)
        {
            throw new MOSRMIException(e, toString());
        }
    }

    @Override
    public void publish(MsgService remote) throws MalformedURLException, RemoteException, MOSException
    {
        JMSRegister.register(url, remote);
    }

    @Override
    public Address runWith(String name)
    {
        return new JMSAddress(url + "/" + name);
    }

    @Override
    public boolean isOn()
    {
        return true;
    }
}
