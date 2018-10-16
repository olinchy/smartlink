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

import com.zte.mw.components.communicate.smartlink.addressBook.AddressBookHolder;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.Request;
import com.zte.mw.components.communicate.smartlink.model.Response;

import static java.util.stream.Collectors.toCollection;

public class Deliver {
    public Deliver(final String senderName) {
        this.senderName = senderName;
    }

    private String senderName;

    public <T extends Response> List<T> send(Request<T> msg) {
        return LinkRepository.get(senderName, msg.key()).stream().map(
                nodeName -> AddressBookHolder.addressBook().get(nodeName))
                .collect(ArrayList<Address>::new, ArrayList::addAll, ArrayList::addAll).stream().map(
                        address -> (T) address.on(msg)).collect(toCollection(ArrayList::new));
    }
}
