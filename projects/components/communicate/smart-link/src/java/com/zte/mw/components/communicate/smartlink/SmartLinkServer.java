package com.zte.mw.components.communicate.smartlink;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;
import com.zte.mw.components.communicate.smartlink.model.message.AddNodeMsg;
import com.zte.mw.components.communicate.smartlink.model.message.RegisterMsg;
import com.zte.mw.components.tools.environment.ResourceProvider;
import com.zte.mw.components.tools.environment.ServiceLocator;
import com.zte.mw.components.tools.logger.Logger;

import static com.zte.mw.components.tools.infrastructure.LoggerLocator.logger;

public class SmartLinkServer implements SmartLinkNode {
    public SmartLinkServer(final Address address) {
        this.address = address;
    }

    private static ThreadPoolExecutor pool = Objects.requireNonNull(ServiceLocator.find(ResourceProvider.class)).get(
            "address-server thread pool", ThreadPoolExecutor.class,
            () -> new ThreadPoolExecutor(2, 3, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<>()));
    private static Timer timer = Objects.requireNonNull(ServiceLocator.find(ResourceProvider.class)).get(
            "smart-link timer", Timer.class, () -> new Timer("smart-link timer", true));
    private static Logger log = logger(SmartLinkServer.class);
    private Address address;
    private ConcurrentHashMap<Address, AddressBook> clients = new ConcurrentHashMap<>();

    public void start() {
        address.publish();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("current clients are : " + clients);
            }
        }, 20, 600000);
    }

    @Override
    public void post() {

    }

    @Override
    public String depends() {
        return "";
    }

    @Override
    public String name() {
        return "smart-link server";
    }

    public void register(final RegisterMsg message) {
        clients.keySet().stream().filter(
                addressSyncMsgAddress -> !addressSyncMsgAddress.equals(message.clientAddress())).forEach(
                address -> pool.execute(() -> address.on(new AddNodeMsg(message.addressBook()))));

        clients.put(message.clientAddress(), message.addressBook());
    }
}
