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
import com.zte.mw.components.communicate.smartlink.model.Message;

public class RegisterMsg implements Message {
    public RegisterMsg(
            final AddressBook addressBook, final Address<AddressSyncMsg> message) {
        this.addressBook = addressBook;
        this.message = message;
    }

    private AddressBook addressBook;
    private Address<AddressSyncMsg> message;

    public Address<AddressSyncMsg> clientAddress() {
        return message;
    }

    public AddressBook addressBook() {
        return addressBook;
    }
}
