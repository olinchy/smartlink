package com.zte.smartlink.addressbook.service;

import com.zte.smartlink.Address;
import com.zte.smartlink.addressbook.AddressBook;

import java.io.Serializable;

/**
 * Created by olinchy on 16-1-8.
 */
public class ClientAddress implements Serializable
{
    public ClientAddress(Address clientAddress, AddressBook book)
    {
        this.address = clientAddress;
        this.addressBook = book;
    }

    private static final long serialVersionUID = 1L;
    public Address address;
    public AddressBook addressBook;

    @Override
    public String toString()
    {
        return "ClientAddress{" +
                "address=" + address +
                ", addressBook=" + addressBook +
                '}';
    }
}
