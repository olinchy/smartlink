/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful;

import java.io.Serializable;

import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.Request;
import com.zte.mw.components.communicate.smartlink.model.Response;

public abstract class RestfulAddress implements Address<Request<Response>, Response>, Serializable {
    @Override
    public Response on(final Request<Response> msg) {
        return null;
    }
}
