package com.zte.smartlink.addressbook.client;

import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NeedStopProcessException;
import com.zte.mos.jms.JMSRegister;
import com.zte.mos.util.Singleton;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.tools.Prop;
import com.zte.mos.util.tools.RefThread;
import com.zte.smartlink.Address;
import com.zte.smartlink.JMSAddress;
import com.zte.smartlink.SmartLinkNodeStarter;
import com.zte.smartlink.UrlAddress;
import com.zte.smartlink.addressbook.service.AddressService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import static com.zte.mos.util.basic.Logger.logger;

public class AddressClientProcess
{
    private static final int MAX_RETRY_TIMES = 10;
    protected static String serverUrl;
    private static final Logger logger = Logger.logger(AddressClientProcess.class);
    private static int count = 1;

    public static Address start()
            throws Exception
    {
        Address clientAddress;
        try
        {
            String url = getClientUrl();
            try
            {
                clientAddress = startViaRmi(url);
            }
            catch (Exception e)
            {
                logger.warn("starting client via rmi caught exception, start it via jms instead", e);
                clientAddress = startViaJms(url);
            }

            Singleton.getInstance(ClientAddr.class).setAddr(clientAddress);
            logger.info("Address Client started!");
            AddressServiceSyncTimer.start();
        }
        catch (Throwable e)

        {
            logger(AddressClientProcess.class).error(e.getMessage(),e);
            if (count <= MAX_RETRY_TIMES)
            {
                logger.warn("start address client failed, retrying the " + count + "st times");
                RefThread.sleep(50000);
                count++;
                clientAddress = start();
            }
            else
            {
                logger.warn("retrying to start address client achieve maximum");
                throw new NeedStopProcessException("start address client achieve maximum retry times");
            }
        }

        return clientAddress;
    }

    private static String getClientUrl() throws Exception
    {
        getClientIp();
        int clientPort = decidePort(Prop.get("ports"));

        return "//" + System.getProperty("java.rmi.server.hostname") + ":" + String.valueOf(clientPort) + "/client";
    }

    private static Address startViaJms(String url) throws RemoteException, MalformedURLException, MOSException
    {
        JMSRegister.register(url, new AddressClientServiceImpl());

        return new JMSAddress(url);
    }

    private static Address startViaRmi(String url) throws Exception
    {
        logger.info("binding Client on " + url);
        try
        {
            Naming.rebind(url, new AddressClientServiceImpl());
        }
        catch (Exception e)
        {
            logger.warn("cannot publish client on " + url + " using jms", e);
            throw e;
        }

        try
        {
            AddressService service = (AddressService) Naming.lookup(serverUrl);
            service.test(url);
        }
        catch (Exception e)
        {
            logger.warn("service looked up to client failed, using jms", e);
            Naming.unbind(url);
            throw e;
        }

        return new UrlAddress(url);
    }

    private static void getClientIp() throws Exception
    {
        initServiceUrl();

        AddressService service = (AddressService) Naming.lookup(serverUrl);
        String ip = service.getClientIp();
        logger(SmartLinkNodeStarter.class).info("client ip:"+ip);

        if (System.getProperty("java.rmi.server.hostname")==null ||(
                !System.getProperty("java.rmi.server.hostname").equalsIgnoreCase("ems-server") &&
                !System.getProperty("java.rmi.server.hostname").equalsIgnoreCase("mos-server")))
            System.setProperty("java.rmi.server.hostname", ip);
        System.setProperty("java.net.preferIPv4Stack", "true");

        logger(SmartLinkNodeStarter.class).info("serverUrl = " + serverUrl);
    }

    private static void initServiceUrl()
    {
        serverUrl = String.format("//%1$s:%2$s/addressService", Prop.get("serverIP"), Prop.get("addrport"));
    }

    protected static int decidePort(String ports) throws Exception
    {
        String[] portScope = ports.split("-");
        for (
                int port = Integer.parseInt(portScope[0]);
                port < Integer.parseInt(portScope[1]); port++)
        {
            try
            {
                LocateRegistry.createRegistry(port);
                return port;
            }
            catch (RemoteException e)
            {
                continue;
            }
        }
        throw new Exception("cannot find available port");
    }
}
