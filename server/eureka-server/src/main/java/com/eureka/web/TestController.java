package com.eureka.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: caicy
 * @Date: Created In 2019-07-01 22:42
 * @Description:
 */

@RestController
public class TestController {

    @GetMapping(value = "/index")
    public String index() {
        return "hello springcloud";
    }
}
