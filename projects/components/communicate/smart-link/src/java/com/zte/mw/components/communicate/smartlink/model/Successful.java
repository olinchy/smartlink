/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.smartlink.model;

public abstract class Successful<T> implements Response<T> {
    @Override
    public Boolean isSuccess() {
        return true;
    }

    @Override
    public Exception ex() {
        return null;
    }
}
