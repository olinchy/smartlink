package com.zte.smartlink.addressbook.service;

import com.zte.mos.inf.MoMsg;
import com.zte.smartlink.Address;
import com.zte.smartlink.UrlAddress;
import com.zte.smartlink.addressbook.NodeAddress;
import com.zte.smartlink.addressbook.SendNodeThread;

import java.util.Arrays;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

public class ServiceSendAddNodeThread extends SendNodeThread
{
    protected final Address exclude;

    public ServiceSendAddNodeThread(NodeAddress[] nodes, Address exclude)
    {
        super(nodes);
        this.exclude = exclude;
    }

    @Override
    public void send() throws Exception
    {
        Address[] clients = getInstance(ServiceAddressBook.class).getClient();

        for (int loop = 0; loop < clients.length; loop++)
        {

            if (clients[loop].equals(exclude))
                continue;

            try
            {
                clients[loop].on(createMsg());
                logger(getClass()).info(
                        "send address " + Arrays.asList(nodes) + " to " + clients[loop]);
            }
            catch (Exception e)
            {
                logger(getClass()).warn(String.format(
                        "can't get ClientService clients %s...",
                        ((UrlAddress) clients[loop]).getUrl()), e);
                continue;
            }
        }
    }

    protected MoMsg createMsg()
    {
        return new AddNodeMsg(nodes);
    }
}
