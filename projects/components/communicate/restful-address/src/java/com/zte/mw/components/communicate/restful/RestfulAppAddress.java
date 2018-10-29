/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful;

import com.zte.mw.components.communicate.smartlink.model.MsgService;
import com.zte.mw.components.communicate.smartlink.model.Request;
import com.zte.mw.components.communicate.smartlink.model.Response;

public class RestfulAppAddress extends AddressAdaptor {
    public RestfulAppAddress(final String url, final MsgService service) {
        this.service = service;
        this.url = url;
    }

    private final MsgService service;
    private final String url;

    @Override
    public Response on(final Request<Response> msg) {
        // TODO: 10/29/18 using jersey
        return null;
    }
}
