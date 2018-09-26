/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.zte.smartlink.addressbook.service;

import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.util.tools.Prop;
import com.zte.smartlink.addressbook.NodeAddress;

import java.util.Arrays;

/**
 * Created by olinchy on 17-3-7.
 */
public class AddNodeMsg implements MoMsg
{
    public AddNodeMsg(NodeAddress[] nodes)
    {
        this.nodes = nodes;
        serverIP = Prop.get("serverIP");
    }

    public String getServerIP()
    {
        return serverIP;
    }

    private String serverIP;
    private final NodeAddress[] nodes;

    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoCreateRequest;
    }

    @Override
    public Maybe<Integer> getTransactionID()
    {
        return null;
    }

    @Override
    public void setTransId(Maybe<Integer> transId)
    {

    }

    @Override
    public String dn()
    {
        return null;
    }

    @Override
    public String[] dns()
    {
        return new String[0];
    }

    @Override
    public void setDN(String dn)
    {

    }

    public NodeAddress[] getNodes()
    {
        return nodes;
    }

    @Override
    public String toString()
    {
        return "AddNodeMsg{" +
                "nodes=" + Arrays.toString(nodes) +
                '}';
    }
}
