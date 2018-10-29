/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful;

import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.Request;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.Service;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;

public abstract class AddressAdaptor implements Address<Request<Response>, Response> {
    @Override
    public void bind(final Service service) {

    }

    @Override
    public Address publish(final SmartLinkNode smartLinkNode) {
        return null;
    }

    @Override
    public Response on(
            final Request<Response> msg) {
        return null;
    }
}
