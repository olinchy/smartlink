package com.zte.mw.components.communicate.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.zte.mw.components.communicate.smartlink.exception.SmartLinkException;
import com.zte.mw.components.communicate.smartlink.model.Request;
import com.zte.mw.components.communicate.smartlink.model.Response;

public interface RMIMsgService extends Remote {
    <T extends Response> T on(Request<T> msg) throws SmartLinkException, RemoteException;
}
