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

import org.glassfish.jersey.client.ClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.zte.mw.components.communicate.restful.JsonUtil;
import com.zte.mw.components.communicate.smartlink.LinkRepository;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.Link;
import com.zte.mw.components.communicate.smartlink.model.Request;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.Service;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;
import com.zte.mw.components.tools.environment.TestBuilder;

import static com.zte.mw.components.communicate.smartlink.addressBook.AddressBookHolder.addressBook;

@EnableDiscoveryClient
@SpringBootApplication
public class RestfulSmartLinkClient {
    public RestfulSmartLinkClient(final DiscoveryClient discoveryClient) {
        LinkRepository.add(new Link("smart-link client", "address sync", "smart-link server"));
        addressBook("smart-link client").add("smart-link server", new Address<Request<Response>, Response>() {
            @Override
            public Response on(final Request<Response> msg) {
                ClientConfig config = new ClientConfig();
                config.register(JsonProvider.class);
                Client client = ClientBuilder.newClient(config);
                WebTarget target = client.target(discoveryClient.getInstances("smartlink-eureka-server").get(
                        0).getUri());
                try {
                    return target.path("on").queryParam("name", JsonUtil.toString(msg)).request().accept(
                            MediaType.APPLICATION_JSON).get(msg.getRespClass());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void bind(final Service service) {

            }

            @Override
            public Address publish(final SmartLinkNode smartLinkNode) {
                return null;
            }
        });
    }

    public static void main(String[] args) throws Exception {
        TestBuilder.setUp();
        SpringApplication.run(RestfulSmartLinkClient.class, args);
    }
}
