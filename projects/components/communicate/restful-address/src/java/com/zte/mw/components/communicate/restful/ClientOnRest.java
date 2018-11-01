/*
 * Copyright © 2016 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful;

import com.zte.mw.components.communicate.smartlink.model.Request;
import com.zte.mw.components.communicate.smartlink.model.Response;
import com.zte.mw.components.communicate.smartlink.model.message.DelNodeMsg;

public interface ClientOnRest {
    void delNode(DelNodeMsg msg);

    <T> Response<T> onMsg(Request<Response<T>> request);
}
