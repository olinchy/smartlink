/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.addressBook;

import java.util.concurrent.ConcurrentHashMap;

public abstract class AddressBookHolder {
    private static ConcurrentHashMap<String, AddressBook> addressBooks = new ConcurrentHashMap<>();

    public static AddressBook addressBook(final String name) {
        return addressBooks.computeIfAbsent(name, s -> new AddressBook());
    }

    public static void register(final String name, final AddressBook addressBook) {
        addressBooks.putIfAbsent(name, addressBook);
    }
}
