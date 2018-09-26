package com.zte.smartlink.addressbook.client;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.util.tools.Prop;
import com.zte.smartlink.addressbook.NodeAddress;
import com.zte.smartlink.addressbook.service.AddNodeMsg;
import com.zte.smartlink.addressbook.service.DelNodeMsg;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

public class AddressClientServiceImpl extends UnicastRemoteObject implements
        AddressClientService
{
    private static final long serialVersionUID = 1L;

    protected AddressClientServiceImpl() throws RemoteException
    {
    }

//    private void delNode(NodeAddress[] nodeAddresses, String serverIP) throws Exception
//    {
//        String serverip;
//        if (Prop.get("serverIP").equals(serverip = getClient(serverIP)))
//            getInstance(LocalAddressBook.class).delOtherNode(nodeAddresses);
//        else
//            logger(getClass()).warn(
//                    "receive del addresses from wrong server " + serverip + ", the right one is " + Prop.get(
//                            "serverIP"));
//    }

    private String getClient(String serverIP)
    {
        try
        {
            return getClientHost();
        }
        catch (Exception e)
        {
            logger(getClass()).warn("get request source failed!", e);
            return serverIP;
        }
    }

    @Override
    public Result<?> onMsg(final MoMsg msg) throws RemoteException, MOSException
    {
        logger(getClass()).info("executing " + msg);
        try
        {
            if (msg instanceof DelNodeMsg)
                nodeChange(new AddressClientServiceImpl.NodeChange()
                {
                    @Override
                    public void doChange() throws MOSException
                    {
                        getInstance(LocalAddressBook.class).delOtherNode(((DelNodeMsg) msg).getNodes());
                    }
                }, ((DelNodeMsg) msg).getServerIP());
            else if (msg instanceof AddNodeMsg)
                nodeChange(new AddressClientServiceImpl.NodeChange()
                {
                    @Override
                    public void doChange() throws MOSException
                    {
                        getInstance(LocalAddressBook.class).addOtherNode(((AddNodeMsg) msg).getNodes());
                    }
                }, ((AddNodeMsg) msg).getServerIP());
        }
        catch (Exception e)
        {
            logger(getClass()).warn("executing add or del nodes caught exception!", e);
        }
        return new Successful();
    }

    private void nodeChange(AddressClientServiceImpl.NodeChange change, String serverIP) throws MOSException
    {
        String serverip;
        if (Prop.get("serverIP").equals(serverip = getClient(serverIP)))
            change.doChange();
        else
            logger(getClass()).warn(
                    "receive add addresses from wrong server " + serverip + ", the right one is " + Prop.get(
                            "serverIP"));
    }

    @Override
    public Result<?> onMsg(ArrayList<? extends MoMsg> msg) throws RemoteException, MOSException
    {
        return onMsg(msg.get(0));
    }

    private interface NodeChange
    {
        void doChange() throws MOSException;
    }
}
