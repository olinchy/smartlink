/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.zte.smartlink.deliver;

import com.zte.mos.inf.MoMsg;

import java.util.ArrayList;

/**
 * Created by olinchy on 17-1-9.
 */
public class MsgHolder implements IdBasedObject
{
    private static final long serialVersionUID = 1L;
    private ArrayList<? extends MoMsg> msg;

    public MsgHolder(int id, ArrayList<? extends MoMsg> msg)
    {
        this.msg = msg;
        this._id = id;
    }

    private int _id;

    @Override
    public Long id()
    {
        return (long) _id;
    }

    public ArrayList<? extends MoMsg> getMsg()
    {
        return msg;
    }

    @Override
    public String toString()
    {
        return "MsgHolder{" +
                "msg=" + msg +
                ", id=" + _id +
                '}';
    }
}
