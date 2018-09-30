/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink;

import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;
import com.zte.mw.components.communicate.smartlink.model.Suite;
import com.zte.mw.components.communicate.smartlink.model.Then;

public class LocalSuite implements Suite {
    @Override
    public Address startClient(final Then then) {
        return null;
    }

    @Override
    public Address start(final SmartLinkNode nodeClass) {
        return null;
    }
}
