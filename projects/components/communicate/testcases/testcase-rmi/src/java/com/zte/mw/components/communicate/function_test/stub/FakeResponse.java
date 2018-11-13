/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.function_test.stub;

import java.util.List;

import com.zte.mw.components.communicate.smartlink.model.Successful;

public class FakeResponse extends Successful<String> {
    @Override
    public List<String> getContent() {
        return null;
    }
}
