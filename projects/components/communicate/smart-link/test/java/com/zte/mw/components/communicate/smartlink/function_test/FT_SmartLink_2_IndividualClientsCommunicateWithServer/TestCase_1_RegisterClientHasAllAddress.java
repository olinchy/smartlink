/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.function_test.FT_SmartLink_2_IndividualClientsCommunicateWithServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.zte.mw.components.communicate.smartlink.model.MsgService;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;
import com.zte.mw.components.communicate.smartlink.stub.RMIAddress;
import com.zte.mw.components.communicate.smartlink.stub.SpyClient;
import com.zte.mw.components.communicate.smartlink.stub.StubService;

public class TestCase_1_RegisterClientHasAllAddress {
    @Before
    public void setUp() throws Exception {
        // TODO: 10/19/18 start server first
        // TODO: 10/19/18 start client_a
    }

    @Test
    public void test_all_clients_know_each_other() throws InterruptedException {
        // TODO: 10/19/18 start client_b as spuClient
        // TODO: client_b has node addresses from client_a
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
}
