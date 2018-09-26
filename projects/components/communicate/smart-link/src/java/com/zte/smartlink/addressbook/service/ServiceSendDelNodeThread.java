package com.zte.smartlink.addressbook.service;

import com.zte.mos.inf.MoMsg;
import com.zte.smartlink.Address;
import com.zte.smartlink.addressbook.NodeAddress;

class ServiceSendDelNodeThread extends ServiceSendAddNodeThread
{
    public ServiceSendDelNodeThread(NodeAddress[] nodes, Address exclude)
    {
        super(nodes, exclude);
    }

    @Override
    protected MoMsg createMsg()
    {
        return new DelNodeMsg(nodes);
    }
}