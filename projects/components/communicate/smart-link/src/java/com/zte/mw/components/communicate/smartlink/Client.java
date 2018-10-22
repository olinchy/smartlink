package com.zte.mw.components.communicate.smartlink;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;
import com.zte.mw.components.communicate.smartlink.addressBook.AddressBookHolder;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.MsgService;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNodeAdaptor;
import com.zte.mw.components.communicate.smartlink.model.message.AddressSyncMsg;
import com.zte.mw.components.communicate.smartlink.model.message.AddressSyncResponse;
import com.zte.mw.components.communicate.smartlink.model.message.RegisterMsg;
import com.zte.mw.components.communicate.smartlink.model.message.RegisterResponse;
import com.zte.mw.components.tools.environment.ResourceProvider;
import com.zte.mw.components.tools.environment.ServiceLocator;

import static com.zte.mw.components.communicate.smartlink.addressBook.AddressBookHolder.addressBook;
import static com.zte.mw.components.tools.infrastructure.LoggerLocator.logger;
import static com.zte.mw.components.tools.infrastructure.structure.DependableSorter.sort;
import static com.zte.mw.components.tools.infrastructure.structure.Pair.pair;
import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toCollection;

public class Client implements MsgService<AddressSyncMsg, AddressSyncResponse> {
    public Client(Address address, SmartLinkNode... nodes) {
        this(address, stream(nodes).collect(toCollection(ArrayList::new)));
    }

    public Client(Address address, List<SmartLinkNode> nodes) {
        addressBook = AddressBookHolder.addressBook(node.name());

        sort(requireNonNull(nodes).stream().filter(Objects::nonNull).collect(toCollection(ArrayList::new))).stream()
                .map(node -> pair(node.name(), address.publish(this.map.computeIfAbsent(node.name(), name -> node))))
                .forEach(pair -> addressBook.add(pair.first(), pair.second()));
        map.forEach((key, value) -> value.post());

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                AddressBook addressBook = addressBook(Client.this.node.name());
                Response<RegisterResponse> resp = new Deliver(Client.this.node).send(
                        new RegisterMsg(addressBook, address));
                if (resp.isSuccess()) {
                    resp.getContent().forEach(response -> response.getContent().forEach(addressBook::merge));
                } else {
                    logger(Client.class).warn("register to server failed", resp.ex());
                }
            }
        }, 1000, 30000);
    }

    protected static Timer timer = requireNonNull(
            ServiceLocator.find(ResourceProvider.class)).get(
            "smart-link timer", Timer.class, () -> new Timer("smart-link timer", true));
    protected AddressBook addressBook;
    private SmartLinkNode node = new SmartLinkNodeAdaptor() {
        @Override
        public MsgService service() {
            return Client.this;
        }

        @Override
        public String name() {
            return "smart-link client";
        }
    };
    private ConcurrentHashMap<String, SmartLinkNode> map = new ConcurrentHashMap<>();

    @Override
    public AddressSyncResponse on(final AddressSyncMsg msg) {
        return null;
    }
}
