/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.Callback;
import com.zte.mw.components.communicate.smartlink.model.Link;
import com.zte.mw.components.communicate.smartlink.model.Message;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.tools.environment.TestBuilder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestDeliver extends TestBuilder {
    @Before
    public void setUpByCase() throws Exception {
        LinkRepository.add(new Link("fake", "trail-run", "test"));
    }

    @Test
    public void name() {
        List<Response> responses = new Deliver("fake").send(new FakeMessage());
    }

    @Test
    public void test_groupBy() {
        HashMap<String, List<Address>> mapTarget = new HashMap<>();
        mapTarget.computeIfAbsent("test2", s -> Arrays.asList(sa("111"), sa("222")));
        mapTarget.computeIfAbsent("test3", s -> Arrays.asList(sa("333"), sa("444"), sa("555")));
        mapTarget.computeIfAbsent("test1", s -> Arrays.asList(sa("777"), sa("888"), sa("999")));
        LinkRepository.init(new AddressBook() {
            @Override
            public List<Address> get(final String name) {
                return mapTarget.get(name);
            }
        });

        LinkRepository.add(new Link("fake", "trail-run", "test1"));
        LinkRepository.add(new Link("fake1", "trail-run", "test2"));
        LinkRepository.add(new Link("fake1", "trail-run", "test3"));
        LinkRepository.add(new Link("fake2", "trail-run2", "test3"));
        LinkRepository.add(new Link("fake1", "trail-run2", "test4"));
        LinkRepository.add(new Link("fake1", "trail-run3", "test5"));
        LinkRepository.add(new Link("fake1", "trail-run4", "test6"));

        List<String> targets = LinkRepository.get("fake1", "trail-run").stream()
                .map(Object::toString).collect(Collectors.toCollection(ArrayList::new));

        assertTrue(targets.contains("111"));
        assertTrue(targets.contains("222"));
        assertTrue(targets.contains("333"));
        assertTrue(targets.contains("444"));
        assertTrue(targets.contains("555"));
        assertFalse(targets.contains("777"));
        assertFalse(targets.contains("888"));
        assertFalse(targets.contains("999"));
    }

    private StubAddress sa(final String name) {
        return new StubAddress(name);
    }

    private class FakeMessage implements Message {
        @Override
        public String key() {
            return "trail-run";
        }
    }

    private class StubAddress implements Address {
        public StubAddress(final String name) {
            this.name = name;
        }

        private final String name;

        @Override
        public void on(final Message message, final Callback callback) {

        }

        @Override
        public void publish() {

        }

        @Override
        public Response on(final Message msg) {
            return null;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
