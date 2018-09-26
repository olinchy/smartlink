package com.zte.smartlink.addressbook.client;

import java.util.TimerTask;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

public class SendAddNodeTimerTask extends TimerTask
{
    public SendAddNodeTimerTask()
    {
    }

    @Override
    public void run()
    {
        send();
    }

    public void send()
    {

        try
        {
            getInstance(LocalAddressBook.class).syncWith(
                    getInstance(AddressServiceStore.class).getService());
        }
        catch (Exception e)
        {
            logger(this.getClass()).info("address server is not available now");
        }
    }
}
