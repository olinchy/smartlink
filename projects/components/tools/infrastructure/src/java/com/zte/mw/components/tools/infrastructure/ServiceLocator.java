/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.tools.infrastructure;

import java.util.concurrent.ConcurrentHashMap;

public class ServiceLocator {
    private static ConcurrentHashMap<Class<?>, Object> map = new ConcurrentHashMap<>();

    public static <T> T find(final Class<T> serviceClass) {
        return map.get(serviceClass) != null ? (T) map.get(serviceClass) : null;
    }

    public static <T> void register(Class<T> serviceClass, T service) {
        map.put(serviceClass, service);
    }
}
