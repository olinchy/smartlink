package com.zte.mw.components.communicate.smartlink.stub;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.zte.mw.components.communicate.smartlink.exception.SmartLinkException;
import com.zte.mw.components.communicate.smartlink.model.Message;
import com.zte.mw.components.communicate.smartlink.model.Response;

public interface RMIMsgService extends Remote {
    Response on(Message msg) throws SmartLinkException, RemoteException;
}
