/*
 * Copyright © 2016 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful;

import com.zte.mw.components.communicate.restful.RestfulAddress;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.Service;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;

public class ShrankRestfulAddress extends RestfulAddress {
    public ShrankRestfulAddress() {}

    @Override
    public void bind(final Service service) {
        // nothing to do
    }

    @Override
    public Address publish(final SmartLinkNode smartLinkNode) {
        // nothing to do
        return null;
    }
}
