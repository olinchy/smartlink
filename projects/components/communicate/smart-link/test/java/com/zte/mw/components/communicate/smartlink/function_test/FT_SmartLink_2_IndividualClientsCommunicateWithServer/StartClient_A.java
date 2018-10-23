/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.function_test.FT_SmartLink_2_IndividualClientsCommunicateWithServer;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.*;

import com.zte.mw.components.communicate.smartlink.function_test.FT_SmartLink_2_IndividualClientsCommunicateWithServer.messages.SimpleMsg;
import com.zte.mw.components.communicate.smartlink.model.MsgService;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;
import com.zte.mw.components.communicate.smartlink.model.Successful;
import com.zte.mw.components.communicate.smartlink.stub.RMIAddress;
import com.zte.mw.components.communicate.smartlink.stub.SpyClient;

public class StartClient_A extends StartClientBase {
    public static void main(String[] args) throws Exception {
        final AtomicReference<JTextPane> pane = new AtomicReference<>();
        pane.set(new JTextPane());
        SpyClient client =
                new SpyClient(
                        new RMIAddress("localhost", 54322, "client_a"),
                        new SimpleNode("node_a_1"),
                        new SmartLinkNode() {
                            @Override
                            public void start() {
                                new Thread(() -> {
                                    JFrame frame = new JFrame();
                                    frame.setTitle("receiver");
                                    frame.setBounds(40, 40, 200, 200);
                                    frame.setLayout(new BorderLayout());
                                    frame.getContentPane().add(pane.get(), BorderLayout.CENTER);

                                    frame.setVisible(true);
                                }).start();
                            }

                            @Override
                            public void post() {

                            }

                            @Override
                            public MsgService service() {
                                return (MsgService<SimpleMsg, Response>) msg -> {
                                    pane.get().setText(msg.getText());
                                    return new Successful<String>() {
                                        @Override
                                        public List<String> getContent() {
                                            return Collections.singletonList("Succeeded in sending msg");
                                        }
                                    };
                                };
                            }

                            @Override
                            public String depends() {
                                return "";
                            }

                            @Override
                            public String name() {
                                return "receiver";
                            }
                        });
    }
}
