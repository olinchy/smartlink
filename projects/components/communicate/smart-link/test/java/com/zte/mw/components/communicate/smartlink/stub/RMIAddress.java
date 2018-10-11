/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.stub;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import com.zte.mw.components.communicate.smartlink.exception.SmartLinkException;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.Message;
import com.zte.mw.components.communicate.smartlink.model.MsgService;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.Service;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;

public class RMIAddress implements Address, Serializable {
    public RMIAddress(String ip, int port, final String name) {
        this(port, "//" + ip + ":" + port + "/" + name);
    }

    public RMIAddress(int port, final String url) {
        this.port = port;
        this.url = url;
    }

    private final int port;
    private String url;

    @Override
    public Response on(final Message msg) throws SmartLinkException {
        try {
            return ((RMIMsgService) Naming.lookup(this.url)).on(msg);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            throw new SmartLinkException();
        }
    }

    @Override
    public int hashCode() {
        int result = port;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }    @Override
    public void bind(final Service service) {
        try {
            LocateRegistry.createRegistry(port);
        } catch (RemoteException e) {
        }
        try {
            Naming.rebind(this.url, new RMIProxyService((MsgService) service));
        } catch (MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final RMIAddress that = (RMIAddress) o;

        if (port != that.port) {
            return false;
        }
        return url != null ? url.equals(that.url) : that.url == null;
    }    @Override
    public Address publish(final SmartLinkNode node) {
        RMIAddress address = new RMIAddress(port, this.url + "/" + node.name());
        address.bind(node.service());
        return address;
    }

    @Override
    public String toString() {
        return "RMIAddress{"
                + " url='" + url + '\''
                + '}';
    }




}
