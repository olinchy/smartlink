package com.zte.mos.smartlink.addressbook.service;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Daemon;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.tools.Prop;
import com.zte.smartlink.addressbook.service.AddressClientAliveTimer;
import com.zte.smartlink.addressbook.service.AddressServiceImpl;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import static com.zte.mos.util.basic.Logger.logger;

public class StartSmartlinkServer implements Daemon
{
    static Logger logger = logger(StartSmartlinkServer.class);
    private static AddressServiceImpl serviceHolderAgainstGC;

    public static void start(String port, String serurl)
            throws MOSException
    {
        logger.info("starting StartSmartlinkServer...");
        logger.info("binging port " + port + "...\n");
        long start = System.currentTimeMillis();
        try
        {
            LocateRegistry.createRegistry(Integer.parseInt(port));
            logger.info("binding service...");

            serviceHolderAgainstGC = new AddressServiceImpl();
            Naming.rebind(serurl, serviceHolderAgainstGC);
            logger.info("Address Service started on " + serurl + "!");

            //startBundles();

            AddressClientAliveTimer.start();
        }
        catch (RemoteException e)
        {
            logger.warn("start StartSmartlinkServer failed", e);
            throw new RuntimeException(e);
        }
        catch (Exception e)
        {
            logger.warn("start StartSmartlinkServer failed", e);
        }
        logger.info("app address server start cost " + (System.currentTimeMillis() - start) + " ms");
    }

    @Override
    public void start(String... args) throws MOSException
    {
        String port = Prop.get("addrport");
        String serurl = Prop.get("addressserver");
        start(port, serurl);
    }

    @Override
    public int compareTo(Daemon o)
    {
        return -1;
    }
}
