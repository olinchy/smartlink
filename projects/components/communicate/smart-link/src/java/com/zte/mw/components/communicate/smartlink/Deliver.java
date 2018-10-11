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
import java.util.Objects;
import java.util.stream.Collectors;

import com.zte.mw.components.communicate.smartlink.exception.SmartLinkException;
import com.zte.mw.components.communicate.smartlink.model.Message;
import com.zte.mw.components.communicate.smartlink.model.Response;

public class Deliver {
    public Deliver(final String senderName) {
        this.senderName = senderName;
    }

    private String senderName;

    public void notify(Message msg) {
        Objects.requireNonNull(LinkRepository.get(senderName, msg.key())).forEach(target -> {
            try {
                target.on(msg);
            } catch (SmartLinkException e) {
                e.printStackTrace();
            }
        });
    }

    public List<Response> send(Message msg) {
        return LinkRepository.get(senderName, msg.key()).stream().map(
                address -> {
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
                }).collect(Collectors.toCollection(ArrayList::new));
    }
}
