package com.zte.mw.components.communicate.smartlink;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;
import com.zte.mw.components.communicate.smartlink.model.Suite;
import com.zte.mw.components.communicate.smartlink.model.message.RegisterMsg;
import com.zte.mw.components.tools.environment.ResourceProvider;
import com.zte.mw.components.tools.environment.ServiceLocator;
import com.zte.mw.components.tools.infrastructure.Instance;
import com.zte.mw.components.tools.infrastructure.structure.DependableSorter;

import static com.zte.mw.components.tools.infrastructure.structure.Pair.pair;

public class SmartLinkClient implements SmartLinkNode {
    public SmartLinkClient(final Suite suite) {this.suite = suite;}

    private static Timer timer = Objects.requireNonNull(
            ServiceLocator.find(ResourceProvider.class)).get(
            "smart-link timer", Timer.class, () -> new Timer("smart-link timer", true));
    private AddressBook addressBook = new AddressBook();
    private Suite suite;
    private ConcurrentHashMap<String, SmartLinkNode> map = new ConcurrentHashMap<>();

    @Override
    public void start() {
        final Address address = suite.startClient(() -> DependableSorter.sort(
                Objects.requireNonNull(
                        scan(SmartLinkNode.class)).stream().map(Instance::instance)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toCollection(ArrayList::new))).stream()
                .map(node -> pair(node.name(), suite.start(this.map.computeIfAbsent(node.name(), name -> node))))
                .forEach(pair -> addressBook.add(pair.first(), pair.second())));
        LinkRepository.init(addressBook);
        map.forEach((key, value) -> value.post());

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Deliver(SmartLinkClient.this.name()).send(new RegisterMsg(addressBook, address));
            }
        }, 20000, 5000);
    }

    @Override
    public void post() {

    }

    private List<Class<SmartLinkNode>> scan(final Class<SmartLinkNode> smartLinkNodeClass) {
        return null;
    }

    @Override
    public String depends() {
        return "";
    }

    @Override
    public String name() {
        return "smart-link client";
    }
}
