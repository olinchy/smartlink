/*
 * Copyright © 2016 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.zte.mw.components.tools.environment.TestBuilder;

@EnableDiscoveryClient
@SpringBootApplication
public class RestfulSmartLinkClient {

    public static void main(String[] args) throws Exception {
        TestBuilder.setUp();
        SpringApplication.run(RestfulSmartLinkClient.class, args);
    }
}
