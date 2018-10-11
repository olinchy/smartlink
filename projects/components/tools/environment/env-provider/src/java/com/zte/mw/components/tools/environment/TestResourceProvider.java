/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.tools.environment;

import java.util.concurrent.ConcurrentHashMap;

public class TestResourceProvider implements ResourceProvider {
    ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();

    @Override
    public <T> T get(final String name, final Class<T> tClass, final Creator<T> creator) {
        return (T) map.computeIfAbsent(name, aClass -> creator.create());
    }

    @Override
    public <T> T get(final String name, final Class<T> tClass) {
        return (T) map.get(name);
    }
}
