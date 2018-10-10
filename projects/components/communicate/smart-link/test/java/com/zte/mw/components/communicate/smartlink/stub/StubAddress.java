/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.stub;

import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.Callback;
import com.zte.mw.components.communicate.smartlink.model.Message;
import com.zte.mw.components.communicate.smartlink.model.MsgService;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;

public class StubAddress implements Address {
    public StubAddress(final String name) {
        this.name = name;
    }

    private final String name;

    public static StubAddress sa(final String name) {
        return new StubAddress(name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final StubAddress that = (StubAddress) o;

        return name.equals(that.name);
    }

    @Override
    public void on(final Message message, final Callback callback) {

    }

    @Override
    public Response on(final Message msg) {
        return new Response() {
            @Override
            public String toString() {
                return name;
            }
        };
    }

    @Override
    public void bind(final MsgService service) {

    }

    @Override
    public Address publish(final SmartLinkNode smartLinkNode) {
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}

