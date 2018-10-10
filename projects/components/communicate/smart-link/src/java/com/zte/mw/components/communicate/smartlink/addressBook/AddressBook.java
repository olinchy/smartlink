/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.addressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.zte.mw.components.communicate.smartlink.model.Address;

public class AddressBook {
    private ConcurrentHashMap<String, HashSet<Address>> addressMap = new ConcurrentHashMap<>();

    public void merge(final AddressBook addressBook) {
        addressBook.addressMap.forEach(
                (key, value) -> this.addressMap.computeIfAbsent(key, s -> new HashSet<>()).addAll(value));
    }

    public void purge(final AddressBook addressBook) {
        addressBook.addressMap.forEach((key, value) -> this.addressMap.computeIfPresent(
                key, (s, addresses) -> {
                    addresses.removeAll(value);
                    return addresses;
                }));
    }

    public void add(final String name, final Address... addresses) {
        addressMap.computeIfAbsent(name, s -> new HashSet<>()).addAll(Arrays.asList(addresses));
    }

    public List<Address> get(final String name) {
        return new ArrayList<>(addressMap.getOrDefault(name, new HashSet<>()));
    }
}
