/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.zte.smartlink.addressbook.service;

import com.zte.smartlink.addressbook.NodeAddress;

/**
 * Created by olinchy on 17-3-7.
 */
public class DelNodeMsg extends AddNodeMsg
{
    public DelNodeMsg(NodeAddress[] nodes)
    {
        super(nodes);
    }
}
