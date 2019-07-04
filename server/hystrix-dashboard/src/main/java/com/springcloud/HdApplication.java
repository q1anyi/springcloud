package com.springcloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @Author: caicy
 * @Date: Created In 2019-07-01 22:30
 * @Description:eureka服务发现启动类
 */
@EnableHystrixDashboard
@SpringBootApplication
public class HdApplication {

    public static void main(String[] args) {
        SpringApplication.run(HdApplication.class, args);
    }
}
