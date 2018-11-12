/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.tools.infrastructure.exception;

public class BaseException extends Exception {
    public BaseException(final String s) {
        super(s);
    }

    public BaseException(final String s, final Throwable throwable) {
        super(s, throwable);
    }

    public BaseException(final Throwable throwable) {
        super(throwable);
    }

    protected BaseException(final String s, final Throwable throwable, final boolean b, final boolean b1) {
        super(s, throwable, b, b1);
    }
}
