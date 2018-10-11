/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.stub;

import com.zte.mw.components.communicate.smartlink.exception.SmartLinkException;
import com.zte.mw.components.communicate.smartlink.model.Message;
import com.zte.mw.components.communicate.smartlink.model.MsgService;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.tools.infrastructure.LoggerLocator;

public class StubService implements MsgService {
    public StubService(final String name) {
        this.name = name;
    }

    private final String name;

    @Override
    public Response on(final Message msg) throws SmartLinkException {
        LoggerLocator.logger(StubService.class).info("executing " + msg + " with callback");
        return new Response() {
            @Override
            public String toString() {
                return super.toString();
            }
        };
    }
}
