/*
 * Copyright © 2016 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zte.mw.components.communicate.restful.ShrankRestfulAddress;
import com.zte.mw.components.communicate.smartlink.Server;
import com.zte.mw.components.communicate.smartlink.model.MsgService;
import com.zte.mw.components.communicate.smartlink.model.message.RegisterMsg;
import com.zte.mw.components.communicate.smartlink.model.message.RegisterResponse;

@RestController
public class ServerController implements MsgService<RegisterMsg, RegisterResponse> {
    private Server server = new Server(new ShrankRestfulAddress() {
        {
            url = "";
        }
    });

    @RequestMapping("/on")
    @Override
    public RegisterResponse on(@RequestParam final RegisterMsg msg) {
        return server.on(msg);
    }
}
