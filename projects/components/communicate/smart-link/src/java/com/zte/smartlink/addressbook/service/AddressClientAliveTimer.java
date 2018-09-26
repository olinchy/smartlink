package com.zte.smartlink.addressbook.service;
import static com.zte.mos.util.basic.Logger.logger;

import com.zte.mos.exception.MOSException;
import com.zte.mos.util.basic.Logger;
import com.zte.smartlink.Address;
import com.zte.smartlink.addressbook.NodeAddress;

import java.util.Timer;
import java.util.TimerTask;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

public class AddressClientAliveTimer
{
    public static void start()
    {
        Timer timer = new Timer("Timer-"+AddressClientAliveTimer.class.getSimpleName());
        timer.schedule(new ClientAliveTimerTask(), 0, 5000);
        timer.schedule(new ClientAddressTimerTask(), 0, 600000);
        try
        {
            getInstance(ServiceAddressBook.class);
            logger(AddressClientAliveTimer.class).info("init cache of addresses successfully!");
        }
        catch (MOSException e)
        {
            logger(AddressClientAliveTimer.class).error(e.getMessage(), e);
        }
    }

    private static class ClientAddressTimerTask extends TimerTask
    {
        static Logger log = logger(ClientAddressTimerTask.class);

        @Override public void run()
        {
            try
            {
                Address[] clients = getInstance(ServiceAddressBook.class).getClient();
                for (Address client : clients)
                {
                    log.debug("client : " + client.toString());
                    NodeAddress[] nodes =
                            getInstance(ServiceAddressBook.class).getClientNodeAddress(client);
                    if(null!=nodes){
                        for (NodeAddress node : nodes)
                        {
                            log.debug("node : " + node.toString());
                        }
                    }
                }
            }
            catch (Exception e)
            {
                logger(AddressClientAliveTimer.class).error(e.getMessage(), e);
            }
        }
    }
}
