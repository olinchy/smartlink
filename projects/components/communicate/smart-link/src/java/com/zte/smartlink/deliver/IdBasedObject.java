/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.zte.smartlink.deliver;

import java.io.Serializable;

/**
 * Created by olinchy on 17-1-9.
 */
public interface IdBasedObject extends Serializable
{
    Long id();
}
