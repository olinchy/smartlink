package com.zte.mw.components.communicate.smartlink;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;
import com.zte.mw.components.communicate.smartlink.model.message.RegisterMsg;
import com.zte.mw.components.tools.environment.ResourceProvider;
import com.zte.mw.components.tools.environment.ServiceLocator;

import static com.zte.mw.components.communicate.smartlink.addressBook.AddressBookHolder.addressBook;
import static com.zte.mw.components.tools.infrastructure.structure.DependableSorter.sort;
import static com.zte.mw.components.tools.infrastructure.structure.Pair.pair;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toCollection;

public class Client {
    public Client(Address address, List<SmartLinkNode> nodes) {
        sort(requireNonNull(nodes).stream().filter(Objects::nonNull).collect(toCollection(ArrayList::new))).stream()
                .map(node -> pair(node.name(), address.publish(this.map.computeIfAbsent(node.name(), name -> node))))
                .forEach(pair -> addressBook().add(pair.first(), pair.second()));
        map.forEach((key, value) -> value.post());

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                List<Response> list = new Deliver(Client.this.name()).send(new RegisterMsg(addressBook(), address));
                // TODO: 10/10/18 add into addressbook
            }
        }, 20000, 5000);
    }

    private String name() {
        return "smart-link client";
    }

    protected static Timer timer = requireNonNull(
            ServiceLocator.find(ResourceProvider.class)).get(
            "smart-link timer", Timer.class, () -> new Timer("smart-link timer", true));
    private ConcurrentHashMap<String, SmartLinkNode> map = new ConcurrentHashMap<>();
}
