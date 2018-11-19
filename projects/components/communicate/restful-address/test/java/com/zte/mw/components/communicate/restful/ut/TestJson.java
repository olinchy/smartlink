/*
 * Copyright © 2016 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful.ut;

import org.junit.BeforeClass;
import org.junit.Test;

import com.zte.mw.components.communicate.restful.Hello;
import com.zte.mw.components.communicate.restful.JsonUtil;
import com.zte.mw.components.tools.environment.TestBuilder;

public class TestJson {
    @BeforeClass
    public static void setUp() throws Exception {
        TestBuilder.setUp();
    }

    @Test
    public void name() throws Exception {
        System.out.println(JsonUtil.toString(new Hello("test")));
    }
}
