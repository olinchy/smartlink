/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.unit_test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;

import com.zte.mw.components.communicate.smartlink.Deliver;
import com.zte.mw.components.communicate.smartlink.LinkRepository;
import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;
import com.zte.mw.components.communicate.smartlink.addressBook.AddressBookHolder;
import com.zte.mw.components.communicate.smartlink.model.Link;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNodeAdaptor;
import com.zte.mw.components.communicate.smartlink.stub.FakeRequest;
import com.zte.mw.components.communicate.smartlink.stub.FakeResponse;
import com.zte.mw.components.tools.environment.TestBuilder;

import static com.zte.mw.components.communicate.smartlink.addressBook.AddressBookHolder.addressBook;
import static com.zte.mw.components.communicate.smartlink.stub.StubAddress.sa;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UT_SmartLink_2_Deliver {
    @BeforeClass
    public static void setUp() throws Exception {
        TestBuilder.setUp();
        AddressBookHolder.register("fake", new AddressBook());
        addressBook("fake").add("test1", sa("111"), sa("222"));
        addressBook("fake").add("test2", sa("333"), sa("444"), sa("555"));
        addressBook("fake").add("test3", sa("777"), sa("888"), sa("999"));

        LinkRepository.add(new Link("fake", "trail-run", "test"));
        LinkRepository.add(new Link("fake", "trail-run", "test1"));
        LinkRepository.add(new Link("fake1", "trail-run", "test2"));
        LinkRepository.add(new Link("fake1", "trail-run", "test3"));
        LinkRepository.add(new Link("fake2", "trail-run2", "test3"));
        LinkRepository.add(new Link("fake1", "trail-run2", "test4"));
        LinkRepository.add(new Link("fake1", "trail-run3", "test5"));
        LinkRepository.add(new Link("fake1", "trail-run4", "test6"));
    }

    @Test
    public void name() {
        Response<FakeResponse> responses = new Deliver(new SmartLinkNodeAdaptor() {
            @Override
            public String name() {
                return "fake";
            }
        }).send(new FakeRequest());
        ArrayList<String> reps = responses.getContent().stream().map(Object::toString).collect(
                Collectors.toCollection(ArrayList::new));
        assertTrue(reps.contains("111"));
        assertTrue(reps.contains("222"));
    }

    @Test
    public void test_groupBy() {
        List<String> targets = LinkRepository.get("fake1", "trail-run").stream()
                .map(Object::toString).collect(Collectors.toCollection(ArrayList::new));

        assertTrue(targets.contains("test2"));
        assertTrue(targets.contains("test3"));

        assertFalse(targets.contains("test1"));
    }
}
