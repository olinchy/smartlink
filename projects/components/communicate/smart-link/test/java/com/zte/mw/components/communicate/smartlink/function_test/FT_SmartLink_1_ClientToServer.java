/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.function_test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;

import com.zte.mw.components.communicate.smartlink.LinkRepository;
import com.zte.mw.components.communicate.smartlink.Server;
import com.zte.mw.components.communicate.smartlink.model.Link;
import com.zte.mw.components.communicate.smartlink.model.MsgService;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;
import com.zte.mw.components.communicate.smartlink.stub.RMIAddress;
import com.zte.mw.components.communicate.smartlink.stub.SpyClient;
import com.zte.mw.components.communicate.smartlink.stub.StubService;
import com.zte.mw.components.tools.environment.TestBuilder;

import static com.zte.mw.components.communicate.smartlink.addressBook.AddressBookHolder.addressBook;

public class FT_SmartLink_1_ClientToServer {
    private static Server server;

    @BeforeClass
    public static void setUp() throws Exception {
        TestBuilder.setUp();
        RMIAddress address;
        server = new Server(address = new RMIAddress("127.0.0.1", 33445, "server"));
        LinkRepository.add(new Link("smart-link client", "address sync", "smart-link server"));
        addressBook("smart-link client").add("smart-link server", address);
    }

    @Test
    public void test_all_clients_know_each_other() throws InterruptedException {
        SpyClient client1 = startClient("1", "node1-1", "node1-2");
        //        SpyClient client2 = startClient("2", "node2-1", "node2-2");

        client1.has("node2-1");
        client1.has("node2-2");
        //        client2.has("node1-1");
        //        client2.has("node1-2");

        Thread.sleep(100000);
    }

    private SpyClient startClient(final String name, final String... nodes) {
        return new SpyClient(new RMIAddress("localhost", 54322, name), Arrays.stream(nodes).map(
                x -> new SmartLinkNode() {
                    @Override
                    public void start() {

                    }

                    @Override
                    public void post() {

                    }

                    @Override
                    public MsgService service() {
                        return new StubService(x);
                    }

                    @Override
                    public String depends() {
                        return null;
                    }

                    @Override
                    public String name() {
                        return x;
                    }
                }).collect(Collectors.toCollection(ArrayList::new)));
    }

    @Test
    public void test_remove_1_client_should_clear_all() throws InterruptedException {
        SpyClient client1 = startClient("1", "node1-1", "node1-2");
        SpyClient client2 = startClient("2", "node2-1", "node2-2");

        client1.has("node2-1");
        client1.has("node2-2");
        client2.has("node1-1");
        client2.has("node1-2");

        client1.stop();
        // wait till server remove no responding client
        Thread.sleep(30000);

        client2.doNotHave("node1-2");
    }
}
