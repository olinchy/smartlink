package com.zte.smartlink.addressbook.client;

import com.zte.smartlink.Address;

public class ClientAddr
{
    private Address addr;

    private ClientAddr()
    {

    }

    public void setAddr(Address addr)
    {
        this.addr = addr;
    }

    public Address getAddr()
    {
        return addr;
    }
}
