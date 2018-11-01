/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.MsgService;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.Service;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;
import com.zte.mw.components.communicate.smartlink.model.message.DelNodeMsg;

public class RestfulClientAddress implements Address<DelNodeMsg, Response> {
    public RestfulClientAddress(final EurekaClient discoveryClient) {this.discoveryClient = discoveryClient;}

    private EurekaClient discoveryClient;
    private MsgService<DelNodeMsg, Response> msgService;

    @Override
    @SuppressWarnings("unchecked")
    public void bind(final Service service) {
        this.msgService = (MsgService<DelNodeMsg, Response>) service;
    }

    @Override
    public Address publish(final SmartLinkNode smartLinkNode) {
        return new RestfulAppAddress(discoveryClient, smartLinkNode);
    }

    @Override
    public Response on(final DelNodeMsg msg) {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("client", false);
        String url = instance.getHomePageUrl();
        // TODO: 10/31/18 send msg via jersey to sync address

        return null;
    }
}
