package com.zte.smartlink.addressbook.client;

import com.zte.smartlink.Address;
import com.zte.smartlink.addressbook.NodeAddress;

import static com.zte.mos.util.Singleton.getInstance;

public class DelSender extends AddSender
{

    public DelSender(Address clientAddress, NodeAddress... nodes)
    {
        super(clientAddress, nodes);
    }

    @Override
    public void send() throws Exception
    {
        getInstance(AddressServiceStore.class).getService().delNode(
                clientAddress, nodes);
    }

}
