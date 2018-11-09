/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful.client;

import java.util.concurrent.ConcurrentHashMap;
import javax.management.ServiceNotFoundException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zte.mw.components.communicate.restful.server.ShrankRestfulAddress;
import com.zte.mw.components.communicate.smartlink.Client;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.Failure;
import com.zte.mw.components.communicate.smartlink.model.MsgService;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;
import com.zte.mw.components.communicate.smartlink.model.message.DelNodeMsg;

import static com.zte.mw.components.tools.infrastructure.LoggerLocator.logger;

@RestController
public class RestfulController implements MsgService<DelNodeMsg, Response> {
    private Client client = new Client(new ShrankRestfulAddress() {
        {
            this.url = "";
        }

        @Override
        public Address publish(
                final SmartLinkNode smartLinkNode) {
            register(smartLinkNode.name(), smartLinkNode.service());
            return new ShrankRestfulAddress() {
                {
                    this.url = "" + "/" + smartLinkNode.name();
                }
            };
        }
    });
    private ConcurrentHashMap<String, MsgService> services = new ConcurrentHashMap<>();

    private void register(final String name, final MsgService service) {
        services.put(name, service);
    }

    @RequestMapping("/on")
    @Override
    public Response on(@RequestParam final DelNodeMsg msg) {
        return client.on(msg);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/onMsg")
    public Response onMsg(@RequestParam RequestWrapper request) {
        return services.getOrDefault(request.name(), (MsgService) msg -> {
            logger(RestfulController.class).warn("cannot found service of " + request.name());
            return new Failure(new ServiceNotFoundException("cannot found service of " + request.name()));
        }).on(request.content());
    }
}
