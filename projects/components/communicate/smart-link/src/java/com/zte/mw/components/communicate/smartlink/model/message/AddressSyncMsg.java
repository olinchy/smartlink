/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.model.message;

import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;
import com.zte.mw.components.communicate.smartlink.model.Message;

public abstract class AddressSyncMsg implements Message {
    public AddressSyncMsg(final AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    protected final AddressBook addressBook;

    public abstract void update(final AddressBook addressBook);
}
