/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.Request;
import com.zte.mw.components.communicate.smartlink.model.Response;

public abstract class RestfulAddress<T extends Request<R>, R extends Response> implements Address<T, R>, Serializable {
    private static Client client = Client.create();
    @JsonProperty
    protected String url;

    @Override
    public R on(final T msg) {
        WebResource resource = client.resource(url);
        return resource.get(msg.getRespClass());
    }
}
