package com.zte.mw.components.communicate.smartlink;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;
import com.zte.mw.components.communicate.smartlink.exception.SmartLinkException;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.Callback;
import com.zte.mw.components.communicate.smartlink.model.Message;
import com.zte.mw.components.communicate.smartlink.model.MsgService;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.message.AddNodeMsg;
import com.zte.mw.components.communicate.smartlink.model.message.RegisterMsg;
import com.zte.mw.components.tools.environment.ResourceProvider;
import com.zte.mw.components.tools.environment.ServiceLocator;
import com.zte.mw.components.tools.logger.Logger;

import static com.zte.mw.components.tools.infrastructure.LoggerLocator.logger;

public class Server implements MsgService {
    public Server(final Address address) {
        address.bind(this);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("current clients are : " + clients);
            }
        }, 20, 600000);
    }

    private static ThreadPoolExecutor pool = Objects.requireNonNull(ServiceLocator.find(ResourceProvider.class)).get(
            "address-server thread pool", ThreadPoolExecutor.class,
            () -> new ThreadPoolExecutor(2, 3, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<>()));
    private static Timer timer = Objects.requireNonNull(ServiceLocator.find(ResourceProvider.class)).get(
            "smart-link timer", Timer.class, () -> new Timer("smart-link timer", true));
    private static Logger log = logger(Server.class);
    private ConcurrentHashMap<Address, AddressBook> clients = new ConcurrentHashMap<>();

    @Override
    public void on(final Message msg, final Callback callback) {
        if (msg instanceof RegisterMsg) {
            register(((RegisterMsg) msg));
        }
    }

    private void register(final RegisterMsg message) {
        clients.keySet().stream().filter(
                addressSyncMsgAddress -> !addressSyncMsgAddress.equals(message.clientAddress())).forEach(
                address -> pool.execute(() -> {
                    try {
                        address.on(new AddNodeMsg(message.addressBook()));
                    } catch (SmartLinkException e) {
                        e.printStackTrace();
                    }
                }));

        clients.put(message.clientAddress(), message.addressBook());
    }

    @Override
    public Response on(final Message msg) {
        if (msg instanceof RegisterMsg) {
            register(((RegisterMsg) msg));
        }
        return new Response() {
            @Override
            public String toString() {
                return super.toString();
            }
        };
    }
}
