package com.zte.smartlink.addressbook.client;

import com.zte.smartlink.Address;
import com.zte.smartlink.addressbook.NodeAddress;

import static com.zte.mos.util.Singleton.getInstance;

public class AddSender implements CommandSender
{
    protected Address clientAddress;
    protected NodeAddress[] nodes;

    public AddSender(Address clientAddress, NodeAddress... nodes)
    {
        this.clientAddress = clientAddress;
        this.nodes = nodes;
    }

    @Override
    public void send() throws Exception
    {
        getInstance(AddressServiceStore.class).getService().addNode(
                clientAddress, nodes);
    }

}
