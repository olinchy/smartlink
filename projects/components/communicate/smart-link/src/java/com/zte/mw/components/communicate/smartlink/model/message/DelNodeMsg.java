/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.model.message;

import java.util.List;

import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;
import com.zte.mw.components.communicate.smartlink.model.Request;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.Successful;

public class DelNodeMsg implements Request<Response> {
    public DelNodeMsg(final AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    private final AddressBook addressBook;

    public Response update(final AddressBook addressBook) {
        addressBook.purge(this.addressBook);
        return new Successful() {
            @Override
            public List getContent() {
                return null;
            }
        };
    }

    @Override
    public String key() {
        return "del node";
    }

    @Override
    public String toString() {
        return "DelNodeMsg{"
                + " addressBook=" + addressBook
                + '}';
    }
}
