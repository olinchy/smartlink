package com.zte.smartlink.addressbook.service;

import com.zte.mos.exception.MOSException;
import com.zte.smartlink.Address;
import com.zte.smartlink.addressbook.AddressBook;
import com.zte.smartlink.addressbook.NodeAddress;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public interface AddressService extends Remote
{
    NodeAddress[] clientRegister(Address clientAddress, AddressBook book)
            throws RemoteException, MOSException;

    void addNode(Address clientAddress, NodeAddress... nodes)
            throws RemoteException, MOSException;

    void delNode(Address clientAddress, NodeAddress... nodes)
            throws RemoteException, MOSException;

    String getClientIp() throws RemoteException, ServerNotActiveException;

    void test(String url) throws RemoteException, NotBoundException, MalformedURLException;
}
