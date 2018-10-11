/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.stub;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.zte.mw.components.communicate.smartlink.exception.SmartLinkException;
import com.zte.mw.components.communicate.smartlink.model.Callback;
import com.zte.mw.components.communicate.smartlink.model.Message;
import com.zte.mw.components.communicate.smartlink.model.MsgService;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.Service;

public class RMIProxyService extends UnicastRemoteObject implements Service, RMIMsgService, Serializable {
    public RMIProxyService(final MsgService service) throws RemoteException {
        super();
        this.service = service;
    }

    private static final long serialVersionUID = 1L;
    private final MsgService service;

    @Override
    public Response on(final Message msg) throws SmartLinkException, RemoteException {
        return service.on(msg);
    }
}
