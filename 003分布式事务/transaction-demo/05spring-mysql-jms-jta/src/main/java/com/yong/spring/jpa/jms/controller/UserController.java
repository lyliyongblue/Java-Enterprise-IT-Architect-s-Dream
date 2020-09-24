package com.yong.spring.jpa.jms.controller;

import com.yong.spring.jpa.jms.commons.ResultHandler;
import com.yong.spring.jpa.jms.domain.User;
import com.yong.spring.jpa.jms.service.UserService;
import com.yong.spring.jpa.jms.vo.AddUser;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResultHandler<Boolean> add(@Valid @RequestBody AddUser user) {
        User entity = new User();
        BeanUtils.copyProperties(user, entity);
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUpdatedTime(LocalDateTime.now());
        userService.add(entity);
        return ResultHandler.success(Boolean.TRUE);
    }

    @GetMapping("/{id}")
    public ResultHandler<User> user(@PathVariable("id") Long userId) {
        Optional<User> optional = userService.get(userId);
        return optional.map(ResultHandler::success)
                .orElseGet(() -> ResultHandler.error(String.format("user: %d not existed", userId), null));
    }
}
