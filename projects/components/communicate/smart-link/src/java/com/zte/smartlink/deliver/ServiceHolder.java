package com.zte.smartlink.deliver;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.MsgService;

import java.rmi.RemoteException;
import java.util.*;

/**
 * Created by olinchy on 6/22/14 for MO_JAVA.
 */
public class ServiceHolder
{

    private List<MsgService> lst = new ArrayList<MsgService>();

    public void add(MsgService service)
    {
        lst.add(service);
    }

    public void ack(MoMsg msg) throws MOSException, RemoteException
    {
        for (MsgService service : lst)
        {
            service.onMsg(msg);
        }
    }
}
