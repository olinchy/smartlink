/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.tools.infrastructure;

import java.util.Objects;

import com.zte.mw.components.tools.environment.ServiceLocator;
import com.zte.mw.components.tools.logger.Logger;
import com.zte.mw.components.tools.logger.LoggingService;

public class LoggerLocator {
    public static Logger logger(Class<?> clazz) {
        return Objects.requireNonNull(ServiceLocator.find(LoggingService.class)).logger(
                clazz);
    }
}
