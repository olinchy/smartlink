package com.zte.smartlink.addressbook;

import com.zte.smartlink.Address;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class AddressBook implements Serializable
{
    private static final long serialVersionUID = 1L;
    ConcurrentHashMap<String, NodeAddress> addresses = new ConcurrentHashMap<String, NodeAddress>();

    public void addAddress(String name, Address address)
    {
        addAddress(new NodeAddress(name, address));
    }

    public void addAddress(NodeAddress... nodes)
    {
        for (int index = 0; index < nodes.length; index++)
        {
            addAddress(nodes[index]);
        }
    }

    public void delAddress(String name, Address address)
    {
        delAddress(new NodeAddress(name, address));
    }

    public void delAddress(NodeAddress... nodes)
    {
        for (int index = 0; index < nodes.length; index++)
        {
            delAddress(nodes[index]);
        }
    }

    public Address[] getAddress(String name)
    {
        if (addresses.containsKey(name))
            return addresses.get(name)
                    .getAddress();
        return new Address[0];
    }

    public NodeAddress[] getNodeAddress()
    {
        ArrayList<NodeAddress> temp = new ArrayList<NodeAddress>();
        Iterator<Entry<String, NodeAddress>> iter = addresses.entrySet()
                .iterator();
        while (iter.hasNext())
        {
            Entry<String, NodeAddress> entry = iter.next();
            NodeAddress node = entry.getValue();
            temp.add(node);
        }
        return temp.toArray(new NodeAddress[temp.size()]);
    }

    public void reset()
    {
        addresses.clear();
    }

    private void addAddress(NodeAddress node)
    {
        if (addresses.containsKey(node.getName()))
            addresses
                    .get(node.getName()).addAddress(node.getAddress());
        else
            addresses.put(node.getName(), node);
    }

    private void delAddress(NodeAddress node)
    {
        if (addresses.containsKey(node.getName()))
        {
            addresses.get(node.getName()).delAddress(node.getAddress());
            if (0 == addresses.get(node.getName()).getAddress().length)
                addresses
                        .remove(node.getName());
        }
    }

    @Override
    public String toString()
    {
        return "AddressBook{" +
                "addresses=" + addresses +
                '}';
    }

    public void clear()
    {
        addresses.clear();
    }

    public boolean contains(Address address)
    {
        for (NodeAddress nodeAddress : addresses.values())
        {
            if (nodeAddress.contains(address))
                return true;
        }
        return false;
    }
}
