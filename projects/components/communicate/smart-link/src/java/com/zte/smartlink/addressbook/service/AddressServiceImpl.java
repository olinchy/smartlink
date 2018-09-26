package com.zte.smartlink.addressbook.service;

import com.zte.mos.exception.MOSException;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.tools.RefThread;
import com.zte.smartlink.Address;
import com.zte.smartlink.addressbook.AddressBook;
import com.zte.smartlink.addressbook.NodeAddress;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Date;

import static com.zte.mos.util.Singleton.getInstance;

public class AddressServiceImpl extends UnicastRemoteObject implements
        AddressService
{
    private static final long serialVersionUID = 1L;

    public AddressServiceImpl() throws RemoteException
    {
        super();
    }

    @Override
    public NodeAddress[] clientRegister(Address clientAddress, AddressBook book) throws RemoteException, MOSException
    {
        Logger.logger(this.getClass()).info("clientRegister: " + clientAddress + ", " + book);

        // add register date
        ClientAliveTimerTask.map.put(clientAddress, new Date());

        NodeAddress[] addresses = getInstance(ServiceAddressBook.class)
                .addClientBook(clientAddress, book);

        Thread threadTemp = RefThread.newThread(new ServiceSendAddNodeThread(book.getNodeAddress(), clientAddress));
        RefThread.start(threadTemp);
        return addresses;
    }

    @Override
    public void addNode(Address clientAddress, NodeAddress... nodes) throws RemoteException, MOSException
    {
        Logger.logger(this.getClass()).info("addNode: " + clientAddress + ", " + Arrays.asList(nodes));

        getInstance(ServiceAddressBook.class).addNode(clientAddress, nodes);

        Thread threadTemp = RefThread.newThread(new ServiceSendAddNodeThread(nodes, clientAddress));
        RefThread.start(threadTemp);
    }

    @Override
    public void delNode(Address clientAddress, NodeAddress... nodes) throws RemoteException, MOSException
    {
        Logger.logger(this.getClass()).info("delNode: " + clientAddress + ", " + nodes);

        getInstance(ServiceAddressBook.class).delNode(clientAddress, nodes);

        Thread threadTemp = RefThread.newThread(new ServiceSendDelNodeThread(nodes, clientAddress));
        RefThread.start(threadTemp);
    }

    @Override
    public String getClientIp() throws RemoteException, ServerNotActiveException
    {
        return getClientHost();
    }

    @Override
    public void test(String url) throws RemoteException, NotBoundException, MalformedURLException
    {
        Naming.lookup(url);
    }
}
