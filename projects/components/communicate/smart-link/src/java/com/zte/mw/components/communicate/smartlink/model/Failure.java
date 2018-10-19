/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.model;

import java.util.List;

public class Failure<T> implements Response<T> {
    public Failure(final Exception ex) {
        this.ex = ex;
    }

    private final Exception ex;

    @Override
    public List<T> getContent() {
        return null;
    }

    @Override
    public Boolean isSuccess() {
        return false;
    }

    @Override
    public Exception ex() {
        return ex;
    }
}
