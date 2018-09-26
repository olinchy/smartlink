package com.zte.smartlink.addressbook.service;

import com.zte.mos.util.tools.RefThread;
import com.zte.smartlink.Address;

import java.util.Date;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

public class ClientAliveTimerTask extends TimerTask
{
    public ClientAliveTimerTask()
    {
    }

    private static final long TIMEOUT = 600000;
    public static final ConcurrentHashMap<Address, Date> map = new ConcurrentHashMap<Address, Date>();

    @Override
    public void run()
    {
        Date now = new Date();
        for (Map.Entry<Address, Date> entry : map.entrySet())
        {
            if (now.getTime() - entry.getValue().getTime() > TIMEOUT)
            {
                logger(this.getClass()).info("client " + entry.getKey() + " achieved timeout, clear it");
                clearClient(entry.getKey());
            }
        }
    }

    private void clearClient(Address client)
    {
        try
        {
            ServiceSendDelNodeThread endThread = new ServiceSendDelNodeThread(getInstance(
                    ServiceAddressBook.class).getClientNodeAddress(
                    client), client);
            Thread threadTemp = RefThread.newThread(endThread);
            RefThread.start(threadTemp);
            getInstance(ServiceAddressBook.class).delClientBook(
                    client);
            map.remove(client);
            client.terminate();
        }
        catch (Exception e1)
        {
            logger(getClass()).warn(e1.getMessage(), e1);
        }
    }
}
