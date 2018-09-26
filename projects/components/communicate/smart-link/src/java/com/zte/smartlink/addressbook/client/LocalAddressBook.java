package com.zte.smartlink.addressbook.client;

import com.zte.mos.util.DistinguishedList;
import com.zte.mos.util.basic.Logger;
import com.zte.smartlink.Address;
import com.zte.smartlink.addressbook.AddressBook;
import com.zte.smartlink.addressbook.NodeAddress;
import com.zte.smartlink.addressbook.service.AddressService;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

public class LocalAddressBook
{
    private final AddressBook local = new AddressBook();
    private final AddressBook remote = new AddressBook();
    private final Timer timer = new Timer("Timer-" + LocalAddressBook.class.getSimpleName());

    private LocalAddressBook()
    {
    }

    public void addNode(String name, Address address) throws Exception
    {
        local.addAddress(name, address);

        timer.schedule(
                new SenderTask(new AddSender(
                        getInstance(
                                ClientAddr.class)
                                .getAddr(),
                        new NodeAddress(name, address))), 5);
    }

    public void delNode(String name, Address address) throws Exception
    {
        local.delAddress(name, address);

        timer.schedule(new SenderTask(new DelSender(
                getInstance(
                        ClientAddr.class)
                        .getAddr(),
                new NodeAddress(name, address))), 5);
    }

    public void reset()
    {
        local.reset();
        remote.reset();
    }

    public void addOtherNode(NodeAddress[] nodes)
    {
        if (nodes == null || nodes.length == 0)
            return;
        Logger.logger(this.getClass()).info("add remote nodes: " + Arrays.asList(nodes));
        remote.addAddress(nodes);
    }

    public void delOtherNode(NodeAddress[] nodes)
    {
        Logger.logger(this.getClass()).info("remove remote nodes: " + Arrays.asList(nodes));
        remote.delAddress(nodes);
    }

    public AddressBook getSelfBook()
    {
        return local;
    }

    public Address[] getAddress(String name)
    {
        DistinguishedList<Address> addresses = new DistinguishedList<Address>();
        Address[] localTemp = local.getAddress(name);
        Address[] remoteTemp = remote.getAddress(name);

        addresses.addAll(Arrays.asList(localTemp));
        addresses.addAll(Arrays.asList(remoteTemp));
        return addresses.toArray(new Address[addresses.size()]);
    }

    public void syncWith(AddressService service)
    {
        try
        {
            logger(this.getClass()).info(
                    "begin clientRegister " + getInstance(ClientAddr.class).getAddr() + ", " + local);

            NodeAddress[] addresses = service.clientRegister(getInstance(ClientAddr.class).getAddr(), local);
            this.remote.addAddress(addresses);
            logger(this.getClass()).info("end clientRegister");
        }
        catch (Exception e)
        {
            Logger.logger(this.getClass()).warn("", e);
        }
    }

    public boolean contains(Address address)
    {
        return local.contains(address) || remote.contains(address);
    }

    private class SenderTask extends TimerTask
    {
        private final CommandSender sender;

        public SenderTask(CommandSender sender)
        {
            this.sender = sender;
        }

        @Override
        public void run()
        {
            try
            {
                sender.send();
            }
            catch (Exception e)
            {
                Logger.logger(this.getClass()).warn("", e);
            }
        }
    }

    @Override
    public String toString()
    {
        return "LocalAddressBook{" +
                "local=" + local +
                ", remote=" + remote +
                '}';
    }
}
