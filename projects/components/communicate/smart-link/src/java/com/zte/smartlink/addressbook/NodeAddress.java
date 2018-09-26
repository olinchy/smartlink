package com.zte.smartlink.addressbook;

import com.zte.smartlink.Address;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class NodeAddress implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final Set<Address> addresses = new TreeSet<Address>();
    private String name = new String();

    public NodeAddress()
    {
    }

    public NodeAddress(String name, Address addr)
    {
        this.addAddress(name, addr);
    }

    @Override public String toString()
    {
        return "NodeAddress{" +
                "addresses=" + addresses +
                ", name='" + name + '\'' +
                '}';
    }

    public void addAddress(String name, Address address)
    {
        this.name = name;
        addresses.add(address);
    }

    public void addAddress(Address... addAddresses)
    {
        for (int loop = 0; loop < addAddresses.length; loop++)
        {
            addresses.add(addAddresses[loop]);
        }
    }

    public void delAddress(Address... delAddress)
    {
        for (int loop = 0; loop < delAddress.length; loop++)
        {
            addresses.remove(delAddress[loop]);
        }
    }

    public String getName()
    {
        return name;
    }

    public Address[] getAddress()
    {
        return addresses.toArray(new Address[addresses.size()]);
    }

    public boolean contains(Address address)
    {
        return addresses.contains(address);
    }
}
