/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.model;

public class Link {
    public Link(final String from, final String keyword, final String to) {
        this.from = from;
        this.to = to;
        this.keyword = keyword;
    }

    private String from;
    private String to;
    private String keyword;

    public String from() {
        return from;
    }

    public String to() {
        return to;
    }

    public String keyword() {
        return keyword;
    }
}
