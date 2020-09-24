package com.yong.spring.jpa.jms.controller;

import com.yong.spring.jpa.jms.commons.ResultHandler;
import com.yong.spring.jpa.jms.domain.UserOrder;
import com.yong.spring.jpa.jms.domain.User;
import com.yong.spring.jpa.jms.service.UserService;
import com.yong.spring.jpa.jms.vo.AddOrder;
import com.yong.spring.jpa.jms.vo.AddUser;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/order")
    public ResultHandler<Boolean> createOrder(@Valid @RequestBody AddOrder order) {
        UserOrder userOrder = new UserOrder();
        BeanUtils.copyProperties(order, userOrder);
        userService.createOrder(userOrder);
        return ResultHandler.success(Boolean.TRUE);
    }

    @PostMapping("/add")
    public ResultHandler<Boolean> createUser(@Valid @RequestBody AddUser addUser) {
        User user = new User();
        BeanUtils.copyProperties(addUser, user);
        userService.createUser(user);
        return ResultHandler.success(Boolean.TRUE);
    }

    @GetMapping("/{id}")
    public ResultHandler<User> userInfo(@PathVariable("id") Long userId) {
       return null;
    }
}
