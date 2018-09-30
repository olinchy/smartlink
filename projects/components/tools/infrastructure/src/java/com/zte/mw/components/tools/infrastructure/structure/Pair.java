/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.tools.infrastructure.structure;

import java.io.Serializable;
import java.util.Objects;

import com.zte.mw.components.tools.environment.ServiceLocator;
import com.zte.mw.components.tools.logger.Logger;
import com.zte.mw.components.tools.logger.LoggingService;

public class Pair<FirstType, SecondType> implements Serializable {
    public Pair() {
    }

    public Pair(FirstType first, SecondType second) {
        this.firstValue = first;
        this.secondValue = second;
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static Logger log = Objects.requireNonNull(ServiceLocator.find(LoggingService.class)).logger(
            Pair.class);
    private FirstType firstValue;
    private SecondType secondValue;

    public static <T1, T2> Pair<T1, T2> pair(T1 t1, T2 t2) {
        return new Pair<T1, T2>(t1, t2);
    }

    public void setFirst(FirstType first) {
        this.firstValue = first;
    }

    public FirstType first() {
        return firstValue;
    }

    public SecondType second() {
        return secondValue;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        try {
            if (o instanceof Pair<?, ?>) {
                return (firstValue.equals(((Pair<?, ?>) o).firstValue) && secondValue.equals(
                        ((Pair<?, ?>) o).secondValue))
                        || (firstValue.equals(((Pair<?, ?>) o).secondValue) && secondValue.equals(
                        ((Pair<?, ?>) o).firstValue));
            }
        } catch (Exception e) {
            log.error("this is " + toString() + " object is " + o.toString());
        }

        return false;
    }

    @Override
    public String toString() {
        if (firstValue.equals(secondValue)) {
            return "[" + String.valueOf(firstValue) + "]";
        }
        return "[" + String.valueOf(firstValue) + "," + String.valueOf(secondValue) + "]";
    }

    public boolean match(Object o) {
        try {
            if (o instanceof Pair<?, ?>) {
                return (((Pair<?, ?>) o).firstValue.toString().matches(firstValue.toString())
                        && ((Pair<?, ?>) o).secondValue
                        .toString().matches(secondValue.toString()))
                        || (((Pair<?, ?>) o).firstValue.toString().matches(secondValue.toString())
                        && ((Pair<?, ?>) o).secondValue
                        .toString().matches(firstValue.toString()));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return false;
    }

    public void setSecond(SecondType t) {
        this.secondValue = t;
    }
}