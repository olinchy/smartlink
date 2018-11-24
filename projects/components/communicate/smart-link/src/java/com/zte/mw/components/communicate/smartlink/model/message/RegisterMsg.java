/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.model.message;

import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.Request;

public class RegisterMsg implements Request<RegisterResponse> {
    public RegisterMsg(
            final AddressBook addressBook, final Address clientAddress) {
        this.addressBook = addressBook;
        this.clientAddress = clientAddress;
    }

    private AddressBook addressBook;
    private Address clientAddress;

    public Address getClientAddress() {
        return clientAddress;
    }

    public AddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public String key() {
        return "address sync";
    }
}
