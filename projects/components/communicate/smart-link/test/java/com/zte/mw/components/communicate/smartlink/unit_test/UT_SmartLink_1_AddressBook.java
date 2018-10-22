/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.unit_test;

import org.junit.Test;

import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;

import static com.zte.mw.components.communicate.smartlink.addressBook.AddressBookHolder.addressBook;
import static com.zte.mw.components.communicate.smartlink.stub.StubAddress.sa;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UT_SmartLink_1_AddressBook {
    @Test
    public void test_purge() {
        AddressBook bookBase = new AddressBook();
        bookBase.add("app_ne", sa("111"), sa("222"), sa("333"));

        AddressBook bookToMerge = new AddressBook();
        bookToMerge.add("app_pm", sa("444"), sa("555"), sa("666"));

        bookBase.merge(bookToMerge);

        AddressBook bookToPurge = new AddressBook();
        bookToPurge.add("app_pm", sa("444"), sa("555"), sa("666"));

        bookBase.purge(bookToPurge);
        assertEquals(3, bookBase.get("app_ne").size());
        assertEquals(0, bookBase.get("app_pm").size());
    }

    @Test
    public void test_get() {
        AddressBook addressBook = new AddressBook();
        addressBook.add("test1", sa("111"), sa("222"));
        addressBook.add("test1", sa("333"), sa("444"));

        assertEquals(4, addressBook.get("test1").size());
        assertTrue(addressBook.get("test1").contains(sa("111")));
        assertTrue(addressBook.get("test1").contains(sa("222")));
        assertTrue(addressBook.get("test1").contains(sa("333")));
        assertTrue(addressBook.get("test1").contains(sa("444")));
    }

    @Test
    public void test_get_null() {

        assertEquals(0, new AddressBook().get("nothing").size());
    }

    @Test
    public void test_merge() {
        AddressBook bookBase = new AddressBook();
        bookBase.add("app_ne", sa("111"), sa("222"), sa("333"));

        AddressBook bookToMerge = new AddressBook();
        bookToMerge.add("app_pm", sa("444"), sa("555"), sa("666"));

        bookBase.merge(bookToMerge);

        assertEquals(3, bookBase.get("app_ne").size());
        assertEquals(3, bookBase.get("app_pm").size());
        assertTrue(bookBase.get("app_pm").contains(sa("444")));
        assertTrue(bookBase.get("app_pm").contains(sa("555")));
        assertTrue(bookBase.get("app_pm").contains(sa("666")));
    }
}
