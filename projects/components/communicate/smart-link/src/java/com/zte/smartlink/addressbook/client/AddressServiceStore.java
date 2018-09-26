package com.zte.smartlink.addressbook.client;

import com.zte.smartlink.addressbook.service.AddressService;
import com.zte.smartlink.addressbook.service.ServiceNotAvaliableException;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class AddressServiceStore
{
    private AddressService service = null;

    private AddressServiceStore()
    {
    }

    public synchronized void setService(AddressService service)
    {
        this.service = service;
    }

    public synchronized AddressService getService()
            throws ServiceNotAvaliableException, MalformedURLException,
            RemoteException, NotBoundException
    {
        if (service == null)
        {
            return (AddressService) Naming.lookup(AddressClientProcess.serverUrl);
            // throw new ServiceNotAvaliableException();
        }
        return service;
    }
}
