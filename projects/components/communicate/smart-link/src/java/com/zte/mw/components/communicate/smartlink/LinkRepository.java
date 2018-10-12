/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.zte.mw.components.communicate.smartlink.model.Link;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toCollection;

public class LinkRepository {
    private static ConcurrentLinkedQueue<Link> links = new ConcurrentLinkedQueue<>();

    public static List<String> get(final String senderName, final String key) {

        Map<String, Map<String, HashSet<String>>> map = links.stream().collect(
                groupingByConcurrent(
                        Link::from, groupingBy(Link::keyword, mapping(Link::to, toCollection(HashSet::new)))));
        return new ArrayList<>(map.get(senderName).get(key));
    }

    public static void add(Link link) {
        LinkRepository.links.add(link);
    }
}
