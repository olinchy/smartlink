/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.function_test.FT_SmartLink_2_IndividualClientsCommunicateWithServer;

import com.zte.mw.components.communicate.smartlink.Client;
import com.zte.mw.components.communicate.smartlink.stub.RMIAddress;

public class StartClient_B extends StartClientBase {
    public static void main(String[] args) {
        Client client = new Client(
                new RMIAddress("localhost", 54323, "client_a"), new SimpleNode("node_b_1"), new SimpleNode("node_b_2"));
    }
}
