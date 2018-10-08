/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;

import com.zte.mw.components.communicate.smartlink.exception.SmartLinkException;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.Callback;
import com.zte.mw.components.communicate.smartlink.model.Message;
import com.zte.mw.components.communicate.smartlink.model.MsgService;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;

public class TestClientToServer {
    private static Server server;

    @BeforeClass
    public static void setUp() throws Exception {
        server = new Server(new RMIAddress(54321, "server"));
    }

    @Test
    public void name() {
        Client client1 = startClient("1", "node2-1", "node2-2");
    }

    private Client startClient(final String name, final String... nodes) {
        Client client = new Client(new RMIAddress("localhost", 54322, name), Arrays.stream(nodes).map(
                x -> new SmartLinkNode() {
                    @Override
                    public void start() {

                    }

                    @Override
                    public void post() {

                    }

                    @Override
                    public MsgService service() {
                        return null;
                    }

                    @Override
                    public String depends() {
                        return null;
                    }

                    @Override
                    public String name() {
                        return null;
                    }
                }).collect(Collectors.toCollection(ArrayList::new)));
        return client;
    }

    private static class RMIAddress implements Address, Serializable {
        public RMIAddress(String ip, int port, final String name) {
            this(port, "/" + ip + ":" + port + "/" + name);
        }

        public RMIAddress(int port, final String url) {
            this.port = port;
            this.url = url;
        }

        private final int port;
        private String url;

        @Override
        public void on(final Message msg, final Callback callback) {
            try {
                ((MsgService) Naming.lookup(this.url)).on(msg, callback);
            } catch (NotBoundException | MalformedURLException | RemoteException e) {
                e.printStackTrace();
            }
        }

        private class RMIProxyService implements MsgService, Remote {
            RMIProxyService(final MsgService service) {
                this.service = service;
            }

            private final MsgService service;

            @Override
            public void on(final Message msg, final Callback callback) {
                service.on(msg, callback);
            }

            @Override
            public Response on(final Message msg) throws SmartLinkException {
                return service.on(msg);
            }
        }

        @Override
        public void bind(final MsgService service) {
            try {
                if (LocateRegistry.getRegistry(port) == null) {
                    LocateRegistry.createRegistry(port);
                }
                Naming.bind(this.url, new RMIProxyService(service));
            } catch (AlreadyBoundException | MalformedURLException | RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public Address publish(final SmartLinkNode node) {
            RMIAddress address = new RMIAddress(port, this.url + "/" + node.name());
            address.bind(node.service());
            return address;
        }

        @Override
        public Response on(final Message msg) throws SmartLinkException {
            try {
                return ((MsgService) Naming.lookup(this.url)).on(msg);
            } catch (NotBoundException | MalformedURLException | RemoteException e) {
                throw new SmartLinkException();
            }
        }
    }
}
