/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.model.message;

import java.util.ArrayList;
import java.util.List;

import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;
import com.zte.mw.components.communicate.smartlink.model.Successful;

public class RegisterResponse extends Successful<AddressBook> {
    public RegisterResponse(final ArrayList<AddressBook> list) {
        this.content = list;
    }

    private ArrayList<AddressBook> content;

    @Override
    public List<AddressBook> getContent() {
        return content;
    }
}
