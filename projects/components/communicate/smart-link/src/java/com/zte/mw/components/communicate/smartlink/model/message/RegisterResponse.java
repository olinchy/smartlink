/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.model.message;

import java.util.ArrayList;

import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;
import com.zte.mw.components.communicate.smartlink.exception.SmartLinkException;
import com.zte.mw.components.communicate.smartlink.model.Failure;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.Successful;

public class RegisterResponse implements Response {
    public RegisterResponse(final ArrayList<AddressBook> list) {
        this.content = list;
    }

    public RegisterResponse(SmartLinkException exception) {
        this.ex = exception;
    }

    private ArrayList<AddressBook> content;
    private SmartLinkException ex;

    @Override
    public <T> T fetch(final String name, final Class<T> tClass) {
        return null;
    }

    @Override
    public Successful result() {
        return ex == null ? new Successful() : new Failure(ex);
    }
}
