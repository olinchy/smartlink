package com.zte.smartlink.addressbook.client;

import java.util.Timer;

public class AddressServiceAliveTimer
{
    public static void start()
    {
        Timer timer = new Timer("Timer-"+AddressServiceAliveTimer.class.getSimpleName());
        timer.schedule(new ServiceAliveTimerTask(), 10, 1000);
    }
}
