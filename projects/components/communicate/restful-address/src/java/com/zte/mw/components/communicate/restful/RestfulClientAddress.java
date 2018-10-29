/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful;

import java.io.Serializable;

import org.eclipse.jetty.servlet.ServletContextHandler;

import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.Request;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.Service;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;

public class RestfulClientAddress implements Address<Request<Response>, Response>, Serializable {
    private ServletContextHandler context;

    @Override
    public void bind(final Service service) {
        try {
            context = JettyServer.start();
            context.addServlet(ClientServlet.class, "/client");
        } catch (Exception ignored) {
        }
    }

    @Override
    public Address publish(final SmartLinkNode smartLinkNode) {
        String url;
        context.addServlet(ApplicationServlet.class, url = "/client/" + smartLinkNode.name());
        return new RestfulAppAddress(url, smartLinkNode.service());
    }

    @Override
    public Response on(final Request msg) {
        // TODO: 10/29/18 toObject(jerseyClient.send(toJson(Request)))
        return null;
    }
}
