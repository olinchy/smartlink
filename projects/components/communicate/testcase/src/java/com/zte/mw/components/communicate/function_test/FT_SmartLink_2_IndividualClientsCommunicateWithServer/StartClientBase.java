/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.function_test.FT_SmartLink_2_IndividualClientsCommunicateWithServer;

import com.zte.mw.components.communicate.function_test.stub.StubService;
import com.zte.mw.components.communicate.rmi.RMIAddress;
import com.zte.mw.components.communicate.smartlink.LinkRepository;
import com.zte.mw.components.communicate.smartlink.model.Link;
import com.zte.mw.components.communicate.smartlink.model.MsgService;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;
import com.zte.mw.components.tools.environment.TestBuilder;

import static com.zte.mw.components.communicate.smartlink.addressBook.AddressBookHolder.addressBook;

public class StartClientBase {
    static {
        try {
            setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void setup() throws Exception {
        TestBuilder.setUp();
        // add links
        LinkRepository.add(new Link("smart-link client", "address sync", "smart-link server"));
        addressBook("smart-link client").add("smart-link server", new RMIAddress("127.0.0.1", 33445, "server"));
    }

    protected static class SimpleNode implements SmartLinkNode {
        public SimpleNode(final String name) {
            this.name = name;
        }

        private final String name;

        @Override
        public void start() {

        }

        @Override
        public void post() {

        }

        @Override
        public MsgService service() {
            return new StubService(name);
        }

        @Override
        public String depends() {
            return "";
        }

        @Override
        public String name() {
            return name;
        }
    }
}
