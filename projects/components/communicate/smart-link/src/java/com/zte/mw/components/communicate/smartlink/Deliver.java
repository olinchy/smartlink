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
import com.zte.mw.components.communicate.smartlink.exception.SmartLinkException;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.Message;
import com.zte.mw.components.communicate.smartlink.model.Response;

import static java.util.stream.Collectors.toCollection;

public class Deliver {
    public Deliver(final String senderName) {
        this.senderName = senderName;
    }

    private String senderName;

    public List<Response> send(Message msg) {
        return LinkRepository.get(senderName, msg.key()).stream().map(
                nodeName -> AddressBookHolder.addressBook().get(nodeName))
                .collect(ArrayList<Address>::new, ArrayList::addAll, ArrayList::addAll).stream().map(address -> {
                    try {
                        return address.on(msg);
                    } catch (SmartLinkException e) {
                        e.printStackTrace();
                        return new Response() {
                            @Override
                            public String toString() {
                                return super.toString();
                            }
                        };
                    }
                }).collect(toCollection(ArrayList::new));
    }
}
