package com.zte.smartlink.addressbook.service;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by olinchy on 16-1-8.
 */
public class AddressKeyComparator implements Comparator<byte[]>, Serializable
{
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(byte[] o1, byte[] o2)
    {
        return new String(o1).compareTo(new String(o2));
    }
}
