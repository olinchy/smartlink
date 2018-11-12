/*
 * Copyright © 2016 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful.client;

import java.io.Serializable;

import com.zte.mw.components.communicate.smartlink.model.Request;

public class RequestWrapper implements Serializable {
    public RequestWrapper(final Request content, final String name) {
        this.content = content;
        this.name = name;
    }

    private Request content;
    private String name;

    public String name() {
        return name;
    }

    public Request content() {
        return content;
    }
}
