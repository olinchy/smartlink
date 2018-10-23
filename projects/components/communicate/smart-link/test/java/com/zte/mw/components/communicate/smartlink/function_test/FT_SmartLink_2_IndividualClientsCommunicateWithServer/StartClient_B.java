/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.function_test.FT_SmartLink_2_IndividualClientsCommunicateWithServer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.zte.mw.components.communicate.smartlink.Deliver;
import com.zte.mw.components.communicate.smartlink.LinkRepository;
import com.zte.mw.components.communicate.smartlink.function_test.FT_SmartLink_2_IndividualClientsCommunicateWithServer.messages.SimpleMsg;
import com.zte.mw.components.communicate.smartlink.model.Link;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNodeAdaptor;
import com.zte.mw.components.communicate.smartlink.stub.RMIAddress;
import com.zte.mw.components.communicate.smartlink.stub.SpyClient;

public class StartClient_B extends StartClientBase {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    public static void main(String[] args) {
        LinkRepository.add(new Link("sender", "show msg", "receiver"));
        SpyClient client = new SpyClient(
                new RMIAddress("localhost", 54323, "client_b"), new SimpleNode("node_b_1"),
                new SmartLinkNodeAdaptor() {
                    @Override
                    public void start() {
                        Timer timer = new Timer("sending txt", true);
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                new Deliver(new SmartLinkNodeAdaptor() {
                                    @Override
                                    public String name() {
                                        return "sender";
                                    }
                                }).send(new SimpleMsg("sending msg at " + format.format(new Date())));
                            }
                        }, 20000, 20000);
                    }

                    @Override
                    public String name() {
                        return "sender";
                    }
                });
    }
}
