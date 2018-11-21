/*
 * Copyright © 2016 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.testcase.eureka.injection;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class TestInjectionApplication {
    public TestInjectionApplication(final DiscoveryClient discoveryClient) {this.discoveryClient = discoveryClient;}

    private DiscoveryClient discoveryClient;

    public static void main(String[] args) {
        SpringApplication.run(TestInjectionApplication.class, args);
    }

    @RequestMapping("/getAll")
    public List<String> getAll() {
        return discoveryClient.getServices();
    }

    @RequestMapping("/get")
    public List<ServiceInstance> get(@RequestParam("appID") String appID) {
        return discoveryClient.getInstances(appID);
    }
}
