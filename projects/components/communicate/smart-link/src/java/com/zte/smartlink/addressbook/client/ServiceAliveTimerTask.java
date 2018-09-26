package com.zte.smartlink.addressbook.client;
import static com.zte.mos.util.basic.Logger.logger;

import com.zte.smartlink.addressbook.service.AddressService;

import java.rmi.Naming;
import java.util.TimerTask;

import static com.zte.mos.util.Singleton.getInstance;

public class ServiceAliveTimerTask extends TimerTask
{
    public ServiceAliveTimerTask()
    {
    }

    @Override
    public void run()
    {
        try
        {
            getInstance(AddressServiceStore.class).setService(
                    (AddressService) Naming.lookup(AddressClientProcess.serverUrl));
        }
        catch (Exception e)
        {
            try
            {
                getInstance(AddressServiceStore.class).setService(null);
            }
            catch (Exception e1)
            {
                logger(ServiceAliveTimerTask.class).error("",  e1);
            }
        }
    }
}
