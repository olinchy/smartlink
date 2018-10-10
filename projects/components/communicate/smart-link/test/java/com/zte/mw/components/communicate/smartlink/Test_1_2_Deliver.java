/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.zte.mw.components.communicate.smartlink.model.Link;
import com.zte.mw.components.communicate.smartlink.model.Message;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.tools.environment.TestBuilder;

import static com.zte.mw.components.communicate.smartlink.addressBook.AddressBookHolder.addressBook;
import static com.zte.mw.components.communicate.smartlink.stub.StubAddress.sa;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Test_1_2_Deliver extends TestBuilder {
    @Before
    public void setUpByCase() throws Exception {
        addressBook().add("test1", sa("111"), sa("222"));
        addressBook().add("test2", sa("333"), sa("444"), sa("555"));
        addressBook().add("test3", sa("777"), sa("888"), sa("999"));

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
        List<Response> responses = new Deliver("fake").send(new FakeMessage());
        ArrayList<String> reps = responses.stream().map(Object::toString).collect(
                Collectors.toCollection(ArrayList::new));
        assertTrue(reps.contains("111"));
        assertTrue(reps.contains("222"));
    }

    @Test
    public void test_groupBy() {
        List<String> targets = LinkRepository.get("fake1", "trail-run").stream()
                .map(Object::toString).collect(Collectors.toCollection(ArrayList::new));

        assertTrue(targets.contains("333"));

        assertTrue(targets.contains("444"));
        assertTrue(targets.contains("555"));
        assertFalse(targets.contains("777"));
        assertFalse(targets.contains("888"));
        assertFalse(targets.contains("999"));
    }

    private class FakeMessage implements Message {
        @Override
        public String key() {
            return "trail-run";
        }
    }
}
