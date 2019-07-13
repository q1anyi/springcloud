package com.springcloud;

import filter.ErrorFilter;
import filter.PostFilter;
import filter.PreSendForwardFilter;
import filter.SecondFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: caicy
 * @Date: Created In 2019-07-01 22:30
 * @Description:zuul服务网关启动类
 */

@EnableZuulProxy
//@EnableDiscoveryClient
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

    // 需要特别注意的是这里一定要把配置好的过滤器注入进来
    @Bean
    public PreSendForwardFilter preSendForwardFilter() {
        return new PreSendForwardFilter();
    }

//    @Bean
//    public SecondFilter secondFilter() {
//        return new SecondFilter();
//    }
//
//    @Bean
//    public PostFilter postFilter() {
//        return new PostFilter();
//    }
//
//    @Bean
//    public ErrorFilter errorFilter() {
//        return new ErrorFilter();
//    }
}
