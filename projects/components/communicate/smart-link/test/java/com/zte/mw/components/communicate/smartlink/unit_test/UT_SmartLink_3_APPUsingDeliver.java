/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.unit_test;

import org.junit.Test;

import com.zte.mw.components.communicate.smartlink.Deliver;
import com.zte.mw.components.communicate.smartlink.model.MsgService;
import com.zte.mw.components.communicate.smartlink.model.Request;
import com.zte.mw.components.communicate.smartlink.model.SmartLinkNode;
import com.zte.mw.components.communicate.smartlink.stub.FakeRequest;
import com.zte.mw.components.communicate.smartlink.stub.FakeResponse;

public class UT_SmartLink_3_APPUsingDeliver {
    @Test
    public void name() {
        SmartLinkNode app = new SmartLinkNode() {
            @Override
            public void start() {

            }

            @Override
            public void post() {

            }

            @Override
            public MsgService service() {
                return (MsgService<FakeRequest, FakeResponse>) msg -> findDeliver().send(
                        (Request<FakeResponse>) new FakeRequest()).get(0);
            }

            private Deliver findDeliver() {
                return new Deliver(this);
            }

            @Override
            public String depends() {
                return null;
            }

            @Override
            public String name() {
                return null;
            }
        };
    }
}
