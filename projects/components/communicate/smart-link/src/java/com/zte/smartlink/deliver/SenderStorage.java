package com.zte.smartlink.deliver;

import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.type.Pair;
import com.zte.smartlink.Address;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by olinchy on 5/27/14 for MO_JAVA.
 */
public class SenderStorage
{
    private ConcurrentHashMap<Pair<String, MoCmds>, List<Address>> map = new ConcurrentHashMap<Pair<String, MoCmds>, List<Address>>();

    private SenderStorage()
    {
    }

    public void put(MoMsg msg, Address[] addresses)
    {
        if (msg.dn() != null)
        {
            List<Address> list = getAddresses(msg);
            list.addAll(Arrays.asList(addresses));
        }
    }

    public void clear(MoMsg msg)
    {
        map.remove(new Pair<String, MoCmds>(msg.dn(), msg.getCmd()));
    }

    public Address[] sendersOf(MoMsg msg)
    {
        List<Address> addresses = this.getAddresses(msg);
        return addresses.toArray(new Address[addresses.size()]);
    }

    private List<Address> getAddresses(MoMsg msg)
    {
        Pair<String, MoCmds> key = new Pair<String, MoCmds>(msg.dn(), msg.getCmd());
        List<Address> list = map.get(key);
        if (list == null)
        {
            list = new ArrayList<Address>();
            map.put(key, list);
        }
        return list;
    }
}
