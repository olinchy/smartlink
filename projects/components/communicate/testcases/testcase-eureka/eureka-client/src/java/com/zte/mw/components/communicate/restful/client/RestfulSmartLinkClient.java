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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
    public static void main(String[] args) throws Exception {
        TestBuilder.setUp();
        LinkRepository.add(new Link("smart-link client", "address sync", "smart-link server"));
        addressBook("smart-link client").add("smart-link server", new Address<Request<Response>, Response>() {
            @Override
            public Response on(final Request<Response> msg) {
                ClientConfig config = new ClientConfig();
                config.register(JsonProvider.class);
                Client client = ClientBuilder.newClient(config);
                WebTarget target = client.target(UriBuilder.fromUri("http://localhost:8077").build());

                try {
                    return target.path("on").queryParam("name", JsonUtil.toString(msg)).request().accept(
                            MediaType.APPLICATION_JSON).get(msg.getRespClass());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // TODO: 11/19/18 find server url via eureka
                // TODO: 11/19/18 send msg according to TestSendingJersey
                //                WebResource resource = Client.create().resource(url);
                //                return resource.get(msg.getRespClass());
                return null;
            }

            @Override
            public void bind(final Service service) {

            }

            @Override
            public Address publish(final SmartLinkNode smartLinkNode) {
                return null;
            }
        });

        SpringApplication.run(RestfulSmartLinkClient.class, args);
    }
}
