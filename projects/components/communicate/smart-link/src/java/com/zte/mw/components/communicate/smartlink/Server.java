package com.zte.mw.components.communicate.smartlink;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.MsgService;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.message.DelNodeMsg;
import com.zte.mw.components.communicate.smartlink.model.message.RegisterMsg;
import com.zte.mw.components.communicate.smartlink.model.message.RegisterResponse;
import com.zte.mw.components.tools.environment.ResourceProvider;
import com.zte.mw.components.tools.environment.ServiceLocator;
import com.zte.mw.components.tools.infrastructure.leaking_bucket.Fusing;
import com.zte.mw.components.tools.logger.Logger;

import static com.zte.mw.components.tools.infrastructure.LoggerLocator.logger;

public class Server implements MsgService<RegisterMsg, RegisterResponse> {
    public Server(final Address address) {
        address.bind(this);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("current clients are : " + clients);
            }
        }, 20, 10000);
    }

    private static ThreadPoolExecutor pool = Objects.requireNonNull(ServiceLocator.find(ResourceProvider.class)).get(
            "address-server thread pool", ThreadPoolExecutor.class,
            () -> new ThreadPoolExecutor(2, 3, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<>()));
    private static Timer timer = Objects.requireNonNull(ServiceLocator.find(ResourceProvider.class)).get(
            "smart-link timer", Timer.class, () -> new Timer("smart-link timer", true));
    private static Logger log = logger(Server.class);
    private ConcurrentHashMap<Address, AddressBook> clients = new ConcurrentHashMap<>();
    private Fusing<Address> fusing = new Fusing<>(1800000, address -> {
        clients.keySet().stream().filter(
                storedAddress -> !address.equals(storedAddress)).forEach(
                storedAddress -> {
                    Response response = storedAddress.on(new DelNodeMsg(clients.get(address)));
                    if (!response.isSuccess()) {
                        log.warn("send del node to " + storedAddress.toString() + " failed", response.ex());
                    }
                });
        clients.remove(address);
    });

    @Override
    public RegisterResponse on(final RegisterMsg msg) {
        return register(msg);
    }

    private RegisterResponse register(final RegisterMsg message) {
        final ArrayList<AddressBook> list = new ArrayList<>();
        clients.keySet().stream().filter(
                addressSyncMsgAddress -> !addressSyncMsgAddress.equals(message.clientAddress())).forEach(
                client -> list.add(clients.get(client)));

        clients.put(message.clientAddress(), message.addressBook());
        fusing.push(message.clientAddress());

        return new RegisterResponse(list);
    }
}
