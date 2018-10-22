/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.tools.environment;

import com.zte.mw.components.tools.logger.Logger;
import com.zte.mw.components.tools.logger.LoggingService;

public final class TestBuilder {
    public static void setUp() throws Exception {
        ServiceLocator.register(LoggingService.class, clazz -> new Logger() {
            @Override
            public void info(final String msg) {
                System.out.println(clazz.getSimpleName() + ":" + msg);
            }

            @Override
            public void info(final String msg, final Throwable e) {
                System.out.println(clazz.getSimpleName() + ":" + msg);
                e.printStackTrace();
            }

            @Override
            public void info(final Object message) {
                System.out.println(clazz.getSimpleName() + ":" + message);
            }

            @Override
            public void error(final Object msg, final Throwable e) {
                System.out.println(clazz.getSimpleName() + ":" + msg);
                e.printStackTrace();
            }

            @Override
            public void error(final String msg) {
                System.out.println(clazz.getSimpleName() + ":" + msg);
            }

            @Override
            public void error(final String msg, final Throwable e) {
                System.out.println(clazz.getSimpleName() + ":" + msg);
                e.printStackTrace();
            }

            @Override
            public void warn(final String msg) {
                System.out.println(clazz.getSimpleName() + ":" + msg);
            }

            @Override
            public void warn(final String msg, final Throwable e) {
                System.out.println(clazz.getSimpleName() + ":" + msg);
                e.printStackTrace();
            }

            @Override
            public void debug(final String msg, final Throwable e) {
                System.out.println(clazz.getSimpleName() + ":" + msg);
                e.printStackTrace();
            }

            @Override
            public void debug(final String msg) {
                System.out.println(clazz.getSimpleName() + ":" + msg);
            }
        });
        ServiceLocator.register(ResourceProvider.class, new TestResourceProvider());
    }
}
