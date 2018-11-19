/*
 * Copyright © 2016 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

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
        addressBook("smart-link client").add("smart-link server", new Address() {
            @Override
            public void bind(final Service service) {

            }

            @Override
            public Address publish(final SmartLinkNode smartLinkNode) {
                return null;
            }

            @Override
            public Response on(final Request msg) {
//                WebResource resource = Client.create().resource(url);
//                return resource.get(msg.getRespClass());
                return null;
            }
        });

        SpringApplication.run(RestfulSmartLinkClient.class, args);
    }
}
