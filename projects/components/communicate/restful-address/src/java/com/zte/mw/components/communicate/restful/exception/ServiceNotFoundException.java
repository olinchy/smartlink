/*
 * Copyright © 2016 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful.exception;

import com.zte.mw.components.communicate.smartlink.exception.SmartLinkException;

public class ServiceNotFoundException extends SmartLinkException {
    public ServiceNotFoundException(final String s) {
        super(s);
    }
}
