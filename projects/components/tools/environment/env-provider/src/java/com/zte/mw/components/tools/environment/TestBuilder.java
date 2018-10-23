/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.tools.environment;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.zte.mw.components.tools.logger.Logger;
import com.zte.mw.components.tools.logger.LoggingService;

public final class TestBuilder {
    public static void setUp() throws Exception {
        init();
        ServiceLocator.register(LoggingService.class, clazz -> new Logger() {
            private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(clazz);

            @Override
            public void info(final String msg) {
                logger.info(msg);
            }

            @Override
            public void info(final String msg, final Throwable e) {
                logger.warn(msg, e);
            }

            @Override
            public void info(final Object message) {
                logger.info(message);
            }

            @Override
            public void error(final Object msg, final Throwable e) {
                logger.error(msg, e);
            }

            @Override
            public void error(final String msg) {
                logger.error(msg);
            }

            @Override
            public void error(final String msg, final Throwable e) {
                logger.error(msg, e);
            }

            @Override
            public void warn(final String msg) {
                logger.warn(msg);
            }

            @Override
            public void warn(final String msg, final Throwable e) {
                logger.warn(msg, e);
            }

            @Override
            public void debug(final String msg, final Throwable e) {
                logger.debug(msg, e);
            }

            @Override
            public void debug(final String msg) {
                logger.debug(msg);
            }
        });
        ServiceLocator.register(ResourceProvider.class, new TestResourceProvider());
    }

    private static Properties init() {
        Properties prop = new Properties();
        prop.put("log4j.rootCategory", "DEBUG, FILE, CONSOLE");
        prop.put("log4j.appender.CONSOLE", "org.apache.log4j.ConsoleAppender");
        prop.put("log4j.appender.CONSOLE.Threshold", "debug");
        prop.put("log4j.appender.CONSOLE.Target", "System.out");
        prop.put("log4j.appender.CONSOLE.layout", "org.apache.log4j.PatternLayout");
        prop.put("log4j.appender.CONSOLE.layout.ConversionPattern", "%d{yyyy-MM-dd HH:mm:ss.SSS}[thr:%t] %c{1}: %m%n");

        prop.put("log4j.appender.FILE", "com.zte.mw.components.tools.environment.ExtendedRollingFileAppender");
        prop.put("log4j.appender.FILE.Threshold", "DEBUG");
        prop.put("log4j.appender.FILE.MaxFileSize", "4MB");
        prop.put("log4j.appender.FILE.MaxBackupIndex", "10");
        prop.put("log4j.appender.FILE.File", "./log/log.log");
        prop.put("log4j.appender.FILE.layout", "org.apache.log4j.PatternLayout");
        prop.put(
                "log4j.appender.FILE.layout.ConversionPattern", "%d{yyyy-MM-dd HH:mm:ss.SSS}[thr:%t] %c{1}: [%p] %m%n");
        prop.put("log4j.appender.FILE.Append", "true");

        PropertyConfigurator.configure(prop);

        return prop;
    }
}
