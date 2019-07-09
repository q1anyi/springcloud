package com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: caicy
 * @Date: Created In 2019-07-01 22:30
 * @Description:zuul服务网关启动类
 */

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class ZuulConsumerApplication {

    @GetMapping("/test/hello")
    public String hello() {
        return "hello zuul";
    }

    public static void main(String[] args) {
        SpringApplication.run(ZuulConsumerApplication.class, args);
    }

}
