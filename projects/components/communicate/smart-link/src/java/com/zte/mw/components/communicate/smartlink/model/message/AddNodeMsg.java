/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.model.message;

import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;

public class AddNodeMsg extends AddressSyncMsg {
    public AddNodeMsg(final AddressBook addressBook) {
        super(addressBook);
    }

    @Override
    public void update(final AddressBook addressBook) {
        addressBook.merge(this.addressBook);
    }
}
