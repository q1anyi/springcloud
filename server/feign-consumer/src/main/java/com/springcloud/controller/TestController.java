package com.springcloud.controller;

import com.springcloud.entity.User;
import com.springcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: caicy
 * @Date: Created In 2019-07-09 6:32
 * @Description:
 */
@RestController
public class TestController {
    @Autowired
    private UserService userService;

    @GetMapping("user/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.get(id);
    }

    @GetMapping("user")
    public List<User> getUsers() {
        return userService.get();
    }

    @PostMapping("user")
    public void addUser() {
        User user = new User(1L, "caicy", "111");
        userService.add(user);
    }

    @PutMapping("user")
    public void updateUser() {
        User user = new User(1L, "caicy", "111");
        userService.update(user);
    }

    @DeleteMapping("user/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
