package com.q1anyi.springcloud.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: caicy
 * @Date: Created In 2019-07-01 22:42
 * @Description:
 */

@RestController
public class TestController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/info")
    public String info() {
        log.info(discoveryClient.toString());
        return discoveryClient.toString();
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
