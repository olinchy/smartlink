/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.zte.mw.components.tools.infrastructure.leaking_bucket;

/**
 * Created by olinchy on 16-11-30.
 */
public class FusingDateWrap<T>
{
    public FusingDateWrap(T t)
    {
        this.t = t;
    }

    private final long date = System.currentTimeMillis();

    public T getT()
    {
        return t;
    }

    public long getDate()
    {
        return date;
    }

    private final T t;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final FusingDateWrap<?> that = (FusingDateWrap<?>) o;

        return t != null ? t.equals(that.t) : that.t == null;
    }

    @Override
    public int hashCode() {
        return t != null ? t.hashCode() : 0;
    }
}
