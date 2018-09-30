/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.tools.infrastructure;

import com.zte.mw.components.tools.logger.Logger;

import static com.zte.mw.components.tools.infrastructure.LoggerLocator.logger;

public class Instance {
    private static Logger log = logger(Instance.class);

    public static <T> T instance(final Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("instance " + clazz.getName() + " caught exception", e);
        }

        return null;
    }
}
