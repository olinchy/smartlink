package com.zte.smartlink.addressbook.service;

import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.LambdaConverter;
import com.zte.mos.util.To;
import com.zte.smartlink.Address;
import com.zte.smartlink.addressbook.AddressBook;
import com.zte.smartlink.addressbook.NodeAddress;

import java.util.HashMap;
import java.util.List;

import static com.zte.mos.util.tools.DummyLogger.doLog;

public class ServiceAddressBook
{
    private ServiceAddressBook()
    {
    }

    private HashMap<Address, AddressBook> books = new HashMap<Address, AddressBook>();

    public NodeAddress[] addClientBook(Address clientAddress, AddressBook book) throws MOSException
    {
        NodeAddress[] nodeAddr = getAllClientNodeAddress();

        books.put(clientAddress, book);

        return nodeAddr;
    }

    public void delClientBook(Address clientAddress) throws BerkeleyDBException
    {
        books.remove(clientAddress);
    }

    public void addNode(Address clientAddress, NodeAddress[] nodes) throws MOSException
    {
        AddressBook cache = books.get(clientAddress);
        if (cache == null)
        {
            cache = new AddressBook();
        }
        cache.addAddress(nodes);
        books.put(clientAddress, cache);
    }

    public void delNode(Address clientAddress, NodeAddress[] nodes) throws MOSException
    {
        AddressBook cache = books.get(clientAddress);
        if (cache == null)
        {
            return;
        }
        else
        {
            cache.delAddress(nodes);
            books.put(clientAddress, cache);
        }
    }

    public Address[] getClient() throws MOSException
    {
        List<Address> list = To.map(books.keySet(), new LambdaConverter<Address, Address>()
        {
            @Override
            public Address to(Address content)
            {
                return content;
            }
        });
        return list.toArray(new Address[list.size()]);
    }

    public NodeAddress[] getClientNodeAddress(Address clientAddress) throws BerkeleyDBException
    {
        AddressBook cache = books.get(clientAddress);
        if (cache != null)
        {
            return cache.getNodeAddress();
        }
        return null;
    }

    public void reset()
    {
        books.clear();
    }

    private NodeAddress[] getAllClientNodeAddress() throws MOSException
    {
        final NodeAddressVisitor visitor = new NodeAddressVisitor();
        To.map(books.values(), new LambdaConverter<Object, AddressBook>()
        {
            @Override
            public Object to(AddressBook content)
            {
                try
                {
                    visitor.visit(content);
                }
                catch (MOSException ignored)
                {
                    doLog(this,ignored);

                }
                return null;
            }
        });
        return visitor.getNodeAddress();
    }
}
