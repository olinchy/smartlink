/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.stub;

import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.Successful;

public class FakeResponse implements Response {
    @Override
    public <T> T fetch(final String name, final Class<T> tClass) {
        return null;
    }

    @Override
    public Successful result() {
        return null;
    }
}
