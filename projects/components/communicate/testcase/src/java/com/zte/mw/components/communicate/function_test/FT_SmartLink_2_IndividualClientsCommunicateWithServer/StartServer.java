/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.function_test.FT_SmartLink_2_IndividualClientsCommunicateWithServer;

import com.zte.mw.components.communicate.rmi.RMIAddress;
import com.zte.mw.components.communicate.smartlink.Server;
import com.zte.mw.components.tools.environment.TestBuilder;

public class StartServer {
    private static Server server;

    public static void main(String[] args) throws Exception {
        TestBuilder.setUp();
        server = new Server(new RMIAddress("127.0.0.1", 33445, "server"));
    }
}
