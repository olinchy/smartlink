/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.stub;

import com.zte.mw.components.communicate.smartlink.model.Request;

public class FakeRequest implements Request<FakeResponse> {
    @Override
    public String key() {
        return "trail-run";
    }
}

