package com.zte.smartlink.addressbook.client;

import java.util.Timer;

public class AddressServiceSyncTimer
{
    public static void start()
    {
        Timer timer = new Timer("Timer-"+AddressServiceSyncTimer.class.getSimpleName());
        timer.schedule(new SendAddNodeTimerTask(), 100, 30000);
    }
}
