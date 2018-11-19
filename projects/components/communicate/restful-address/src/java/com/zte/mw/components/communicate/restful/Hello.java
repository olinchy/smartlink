/*
 * Copyright © 2016 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Hello implements Serializable {
    public Hello(final String content) {
        this.content = content;
    }

    public Hello() {

    }

    @JsonProperty
    private String content;

    @Override
    public String toString() {
        return content;
    }
}
