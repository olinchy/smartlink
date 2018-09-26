/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.zte.smartlink;

import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;

/**
 * Created by olinchy on 17-1-9.
 */
public class StubMsg implements MoMsg
{
    public StubMsg(int i, int j)
    {
        this.i = i;
        this.j = j;
    }

    private final int i;
    private final int j;

    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoCreateInd;
    }

    @Override
    public Maybe<Integer> getTransactionID()
    {
        return new Maybe<Integer>(null);
    }

    @Override
    public void setTransId(Maybe<Integer> transId)
    {

    }

    @Override
    public String dn()
    {
        return "/Ems/1/Ne/1";
    }

    @Override
    public String[] dns()
    {
        return new String[0];
    }

    @Override
    public void setDN(String dn)
    {

    }

    @Override
    public String toString()
    {
        return String.format("msg{group: %d, msg: %d}", i, j);
    }
}
