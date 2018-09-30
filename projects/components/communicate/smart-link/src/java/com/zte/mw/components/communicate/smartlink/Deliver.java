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

import com.zte.mw.components.communicate.smartlink.model.Callback;
import com.zte.mw.components.communicate.smartlink.model.Message;
import com.zte.mw.components.communicate.smartlink.model.Response;

public class Deliver {
    public Deliver(final String senderName) {
        this.senderName = senderName;
    }

    private String senderName;

    public void notify(Message msg, Callback callback) {
        Objects.requireNonNull(LinkRepository.get(senderName, msg.key())).forEach(target -> target.on(msg, callback));
    }

    public List<Response> send(Message msg) {
        return Objects.requireNonNull(LinkRepository.get(senderName, msg.key())).stream().map(
                address -> address.on(msg)).collect(Collectors.toCollection(ArrayList::new));
    }
}
