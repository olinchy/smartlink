/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.addressBook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import com.zte.mw.components.communicate.smartlink.model.Address;

public class AddressBook implements Serializable {
    private ConcurrentHashMap<String, HashSet<Address>> local = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, HashSet<Address>> remote = new ConcurrentHashMap<>();

    public void merge(final AddressBook addressBook) {
        addressBook.local.forEach(
                (key, value) -> this.remote.computeIfAbsent(key, s -> new HashSet<>()).addAll(value));
    }

    public void purge(final AddressBook addressBook) {
        addressBook.local.forEach((key, value) -> this.remote.computeIfPresent(
                key, (s, addresses) -> {
                    addresses.removeAll(value);
                    return addresses;
                }));
    }

    public void add(final String name, final Address... addresses) {
        local.computeIfAbsent(name, s -> new HashSet<>()).addAll(Arrays.asList(addresses));
        AddressBookHolder.register(name, this);
    }

    public List<Address> get(final String name) {
        return new ArrayList<>(Stream.of(local, remote).map(map -> map.getOrDefault(name, new HashSet<>())).collect(
                HashSet::new, HashSet::addAll, HashSet::addAll));
    }

    @Override
    public String toString() {
        return "AddressBook{"
                + " local=" + local
                + ", remote=" + remote
                + '}';
    }
}
