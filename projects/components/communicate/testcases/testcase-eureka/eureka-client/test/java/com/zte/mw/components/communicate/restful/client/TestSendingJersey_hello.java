/*
 * Copyright © 2016 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.zte.mw.components.communicate.restful.Hello;
import com.zte.mw.components.tools.environment.TestBuilder;

public class TestSendingJersey_hello {
    public static void main(String[] args) throws Exception {
        TestBuilder.setUp();
        ClientConfig config = new ClientConfig();
        config.register(JsonProvider.class);
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(UriBuilder.fromUri("http://localhost:8077").build());

        String response = target.path("hello").queryParam("name", "Liuwei").request().accept(
                MediaType.APPLICATION_JSON).get(Hello.class).toString();
        System.out.println(response);
    }
}
