package com.zte.smartlink;

import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NeedStopProcessException;
import com.zte.mos.message.MsgService;
import com.zte.mos.util.Scan;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.scaner.PreLoadScanner;
import com.zte.smartlink.addressbook.client.AddressClientProcess;
import com.zte.smartlink.addressbook.client.LocalAddressBook;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 15-5-12.
 */
public class SmartLinkNodeStarter
{
    static Logger logger = logger(SmartLinkNodeStarter.class);
    static ArrayList<Remote> interfaces = new ArrayList<Remote>();
    static Map<String, Long> startCost = new HashMap<String, Long>();

    public static void start(
            String serverIP, String... args) throws MOSException
    {
        scanSmartLinks();
        try
        {
            startUnder(AddressClientProcess.start(), args);
        }
        catch (NeedStopProcessException e)
        {
            logger.warn("start nodes caught bindException, shutting down!", e);
            throw e;
        }
        catch (Throwable e)
        {
            logger.warn("starting nodes failed, caught ", e);
        }
        logger(SmartLinkNodeStarter.class).info("all nodes have been started! God bless you and have some fun :)");
    }

    private static void scanSmartLinks() throws MOSException
    {
        new PreLoadScanner().scan(Scan.getClasses("com.zte.app.smartlink"));
    }

    private static void startUnder(final Address clientAddress, final String... args) throws Exception
    {
        Set<Class<SmartLinkNode>> set = Scan.getClasses("com.zte.mos.node", SmartLinkNode.class);
        final LinkedList<SmartLinkNode> lstNode = new LinkedList<SmartLinkNode>();

        DependenceList.sort(lstNode, set);

        startNodes(clientAddress, lstNode, args);

        for (SmartLinkNode node : lstNode)
        {
            long start = System.currentTimeMillis();
            try
            {
                logger.info("starting to execute post() of " + node.getName());
                node.post();
                logger.info("finished to execute post() of " + node.getName());
            }
            catch (Throwable e)
            {
                logger.warn("", e);
            }
            updateCost(String.valueOf(node.getName()), System.currentTimeMillis() - start);
        }

        dumpStartCost();
    }

    private static void dumpStartCost()
    {
        Iterator iterator = startCost.entrySet().iterator();

        while (iterator.hasNext())
        {
            Map.Entry entry = (Map.Entry) iterator.next();
            logger.info("app " + entry.getKey() + " start cost " + entry.getValue() + " ms");
        }
    }

    private static void startNodes(
            Address clientAddress, List<SmartLinkNode> lstNode, String[] args) throws MOSException
    {
        for (SmartLinkNode node : lstNode)
        {
            long start = System.currentTimeMillis();
            try
            {
                startNode(clientAddress, args, node);
            }
            catch (RemoteException e)
            {
                logger.warn("starting nodes caught remote exception", e);
                throw new NeedStopProcessException(e);
            }
            catch (NeedStopProcessException e)
            {
                logger.warn("starting nodes caught remote exception", e);
                throw e;
            }
            catch (Throwable e)
            {
                logger.warn("start node " + node.getName() + " on " + clientAddress + " failed!", e);
            }
            updateCost(String.valueOf(node.getName()), System.currentTimeMillis() - start);
        }
    }

    private static void updateCost(String name, long cost)
    {
        Long time = startCost.get(name);

        if (time != null)
        {
            startCost.put(name, time + cost);
        }
        else
        {
            startCost.put(name, cost);
        }
    }

    private static void startNode(Address clientAddress, String[] args, SmartLinkNode node) throws Exception
    {
        ArrayList<String> strings = getParas((Class<SmartLinkNode>) node.getClass(), args);
        logger.info("starting node " + node.getName());

        Address nodeUrl = clientAddress.runWith(node.getName());
        strings.add(nodeUrl.toString());
        node.start(strings.toArray(new String[strings.size()]));

        Remote remote = node.getService();
        if (remote != null)
        {
            nodeUrl.publish((MsgService) remote);
            getInstance(LocalAddressBook.class).addNode(node.getName(), nodeUrl);
            // add static reference to every node in case of gc
            interfaces.add(remote);
            logger.info("bind node " + node.getName() + " on " + nodeUrl);
        }

        logger.info("node " + node.getName() + " started!");
    }

    private static ArrayList<String> getParas(Class<SmartLinkNode> clazz, String[] args)
    {
        ArrayList<String> strings = new ArrayList<String>();
        if (args != null && args.length != 0)
        {
            for (String arg : args)
            {
                String[] split = arg.split(":");
                String key = split[0];
                String value = split[1];
                if (key.equalsIgnoreCase(clazz.getSimpleName()))
                {
                    strings.add(value);
                }
            }
        }
        return strings;
    }
}
