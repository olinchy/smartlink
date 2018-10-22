/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.stub;

import java.util.Timer;
import java.util.TimerTask;

import com.zte.mw.components.communicate.smartlink.Client;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;
import com.zte.mw.components.communicate.smartlink.model.message.AddressSyncMsg;
import com.zte.mw.components.communicate.smartlink.model.message.AddressSyncResponse;

import static com.zte.mw.components.communicate.smartlink.addressBook.AddressBookHolder.addressBook;
import static com.zte.mw.components.tools.infrastructure.LoggerLocator.logger;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SpyClient extends Client {
    public SpyClient(final Address address, final SmartLinkNode... nodes) {
        super(address, nodes);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                logger(SpyClient.class).info(addressBook.toString());
            }
        }, 10, 1000);
    }

    private Timer timer = new Timer("client address printer", true);

    @Override
    public AddressSyncResponse on(
            final AddressSyncMsg msg) {
        logger(SpyClient.class).info("AddressSyncMsg received");
        return new AddressSyncResponse();
    }

    public void has(String nodeName) {
        assertNotNull(addressBook("smart-link client").get(nodeName));
    }

    public void doNotHave(String nodeName) {
        assertNull(addressBook("smart-link client").get(nodeName));
    }

    public void stop() {
        timer.cancel();
    }
}
