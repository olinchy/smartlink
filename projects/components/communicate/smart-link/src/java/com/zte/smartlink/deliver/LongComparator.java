/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.zte.smartlink.deliver;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by olinchy on 17-1-9.
 */
public class LongComparator implements Comparator<byte[]>, Serializable
{
    @Override
    public int compare(byte[] o1, byte[] o2)
    {
        return Long.valueOf(new String(o1).trim()).compareTo(Long.valueOf(new String(o2).trim()));
    }
}
