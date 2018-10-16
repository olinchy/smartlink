/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.model.message;

import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;
import com.zte.mw.components.communicate.smartlink.model.Request;

public abstract class AddressSyncMsg implements Request {
    public AddressSyncMsg(final AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    protected final AddressBook addressBook;

    public abstract void update(final AddressBook addressBook);
}
