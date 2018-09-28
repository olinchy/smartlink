/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.tools.infrastructure.ServiceLocator;
import com.zte.mw.components.tools.logger.Logger;
import com.zte.mw.components.tools.logger.LoggingService;
import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;
import com.zte.mw.components.communicate.smartlink.model.message.AddNodeMsg;
import com.zte.mw.components.communicate.smartlink.model.message.AddressSyncMsg;
import com.zte.mw.components.communicate.smartlink.model.message.RegisterMsg;

public class AddressServer {
    public AddressServer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("current clients are : " + clients);
            }
        }, 20, 600000);
    }

    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(
            2, 2, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    private static Timer timer = new Timer("address-server", true);
    private static Logger log = ServiceLocator.find(LoggingService.class).logger(AddressServer.class);
    private ConcurrentHashMap<Address<AddressSyncMsg>, AddressBook> clients = new ConcurrentHashMap<>();

    public void register(final RegisterMsg message) {
        clients.keySet().stream().filter(
                addressSyncMsgAddress -> !addressSyncMsgAddress.equals(message.clientAddress())).forEach(
                address -> pool.execute(() -> address.on(new AddNodeMsg(message.addressBook()))));

        clients.put(message.clientAddress(), message.addressBook());
    }
}
