/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful.ut;

import org.junit.BeforeClass;
import org.junit.Test;

import com.zte.mw.components.tools.environment.TestBuilder;
import com.zte.mw.components.tools.infrastructure.Prop;

public class TestStartRestfulAddress {
    @BeforeClass
    public static void setUp() throws Exception {
        TestBuilder.setUp();
        Prop.setProperty("httpport", "54321");
    }

    @Test
    public void name() {
    }
}
