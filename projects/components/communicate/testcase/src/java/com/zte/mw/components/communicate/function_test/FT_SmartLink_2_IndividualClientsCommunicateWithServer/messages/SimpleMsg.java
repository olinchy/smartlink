/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.function_test.FT_SmartLink_2_IndividualClientsCommunicateWithServer.messages;

import com.zte.mw.components.communicate.smartlink.model.Request;
import com.zte.mw.components.communicate.smartlink.model.Response;

public class SimpleMsg implements Request<Response> {
    public SimpleMsg(final String text) {
        this.text = text;
    }
    private String text;

    @Override
    public String key() {
        return "show msg";
    }

    public String getText() {
        return text;
    }
}
