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
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.Link;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

public class LinkRepository {
    private static AddressBook addressBook;
    private static ConcurrentLinkedQueue<Link> links = new ConcurrentLinkedQueue<>();

    public static List<Address> get(final String senderName, final String key) {

        Map<String, Map<String, List<String>>> map = links.stream().collect(Collectors.groupingByConcurrent(
                Link::from, Collectors.groupingBy(Link::keyword, mapping(Link::to, toList()))));
        return map.get(senderName).get(key).stream().map(name -> addressBook.get(name)).collect(
                ArrayList::new, ArrayList::addAll, ArrayList::addAll);
    }

    public static void init(final AddressBook addressBook) {
        LinkRepository.addressBook = addressBook;
    }

    public static void add(Link link) {
        LinkRepository.links.add(link);
    }
}
