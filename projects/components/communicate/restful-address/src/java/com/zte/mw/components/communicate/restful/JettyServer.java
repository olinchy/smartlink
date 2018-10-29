/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import com.zte.mw.components.tools.infrastructure.Prop;

public class JettyServer {
    private static Server server;

    public static ServletContextHandler start() throws Exception {
        int port = Integer.parseInt(Prop.get("httpport"));
        server = new Server();
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setLowResourcesConnections(5000);
        connector.setLowResourcesMaxIdleTime(5000);
        connector.setAcceptors(3);
        connector.setPort(port);
        server.addConnector(connector);

        server.setThreadPool(new QueuedThreadPool(50));
        ServletContextHandler context =
                new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/client");
        server.setHandler(context);
        server.start();
        return context;
    }
}
