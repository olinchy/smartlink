/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.zte.smartlink;

import com.zte.mos.message.Mo;
import com.zte.mos.util.msg.IndicateMsg;
import com.zte.mos.util.msg.MoCreateIndicationMsg;
import com.zte.mos.util.tools.IDAllocator;
import com.zte.mos.util.tools.Prop;
import com.zte.smartlink.deliver.IdBasedObjectHolder;
import com.zte.smartlink.deliver.MsgHolder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by olinchy on 17-1-6.
 */
public class TestMsgHolder
{
    @BeforeClass
    public static void setUp() throws Exception
    {
        Properties prop;
        Prop.set(prop = new Properties());

        prop.put("storage_path", "./msgdb/");
        prop.put("cache_size", "15");
        holder = new IdBasedObjectHolder<MsgHolder>(
                "127_5000_ems", MsgHolder.class);
    }

    private static IdBasedObjectHolder<MsgHolder> holder;

    @AfterClass
    public static void teardown()
    {
        holder.clear();
    }

    @Test
    public void test_msg_order() throws Exception
    {
        IDAllocator allocator = new IDAllocator(1, 2000);

        long[] expects = new long[10];

        for (int k = 0; k < 10; k++)
        {
            for (int i = 0; i < expects.length; i++)
            {
                ArrayList<IndicateMsg> list = new ArrayList<IndicateMsg>();
                for (int j = 0; j < 10; j++)
                {
                    list.add(new MoCreateIndicationMsg("/Ems/1/Ne/" + j, new Mo("Ne")));
                }
                MsgHolder msg = new MsgHolder(allocator.allocate(), list);
                holder.put(msg);
                expects[i] = msg.id();
                System.out.println("putting task at " + expects[i]);
            }

            for (int i = 0; i < 10; i++)
            {
                MsgHolder msg = holder.poll(expects[i]);
                System.out.println("getting msg " + i + " " + msg.id());
                assertThat(msg.id(), is(expects[i]));
            }
        }
    }
}
