package com.zte.mw.components.communicate.smartlink;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;
import com.zte.mw.components.communicate.smartlink.model.message.RegisterMsg;
import com.zte.mw.components.tools.environment.ResourceProvider;
import com.zte.mw.components.tools.environment.ServiceLocator;

import static com.zte.mw.components.tools.infrastructure.structure.DependableSorter.sort;
import static com.zte.mw.components.tools.infrastructure.structure.Pair.pair;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toCollection;

public class Client {
    public Client(Address address, List<SmartLinkNode> nodes) {
        sort(requireNonNull(nodes).stream().filter(Objects::nonNull).collect(toCollection(ArrayList::new))).stream()
                .map(node -> pair(node.name(), address.publish(this.map.computeIfAbsent(node.name(), name -> node))))
                .forEach(pair -> addressBook.add(pair.first(), pair.second()));
        LinkRepository.init(addressBook);
        map.forEach((key, value) -> value.post());

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Deliver(Client.this.name()).send(new RegisterMsg(addressBook, address));
            }
        }, 20000, 5000);
    }

    private String name() {
        return "smart-link client";
    }

    private static Timer timer = requireNonNull(
            ServiceLocator.find(ResourceProvider.class)).get(
            "smart-link timer", Timer.class, () -> new Timer("smart-link timer", true));
    private AddressBook addressBook = new AddressBook();
    private ConcurrentHashMap<String, SmartLinkNode> map = new ConcurrentHashMap<>();
}
