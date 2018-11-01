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
import com.zte.mw.components.communicate.smartlink.model.Request;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;

public class RestfulAppAddress extends AddressAdaptor {
    public RestfulAppAddress(final EurekaClient discoveryClient, final SmartLinkNode smartLinkNode) {

        this.discoveryClient = discoveryClient;
        this.smartLinkNode = smartLinkNode;
    }

    private final SmartLinkNode smartLinkNode;
    private final EurekaClient discoveryClient;

    @Override
    public Response on(final Request<Response> msg) {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("client", false);
        String url = instance.getHomePageUrl();
        // TODO: 11/1/18 send msg via jersey to locate service
        return null;
    }
}
