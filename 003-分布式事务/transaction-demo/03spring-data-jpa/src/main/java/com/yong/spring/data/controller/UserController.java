package com.yong.spring.data.controller;

import com.yong.spring.data.commons.ResultHandler;
import com.yong.spring.data.domain.User;
import com.yong.spring.data.service.UserService;
import com.yong.spring.data.vo.AddUser;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add_list")
    public ResultHandler<Boolean> addList(@Valid @RequestBody List<AddUser> users) {
        List<User> entityList = users.stream().map(user -> {
            User entity = new User();
            BeanUtils.copyProperties(user, entity);
            entity.setCreatedTime(LocalDateTime.now());
            entity.setUpdatedTime(LocalDateTime.now());
            return entity;
        }).collect(Collectors.toList());
        userService.add(entityList);
        return ResultHandler.success(Boolean.TRUE);
    }

    @GetMapping("/get_by_created_time")
    public ResultHandler<List<User>> queryList(String start, String end, Integer pageNo, Integer pageSize) {
        LocalDateTime startTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        List<User> users = userService.queryByCreatedTime(startTime, endTime, pageNo, pageSize);
        return ResultHandler.success(users);
    }

    @GetMapping("/{id}")
    public ResultHandler<User> user(@PathVariable("id") Long userId) {
        Optional<User> optional = userService.get(userId);
        return optional.map(ResultHandler::success)
                .orElseGet(() -> ResultHandler.error(String.format("user: %d not existed", userId), null));
    }


    @GetMapping("/count/star/{startAge}/end/{endAge}/first_name/{firstName}")
    public ResultHandler<Long> count(@PathVariable Integer startAge, @PathVariable Integer endAge, @PathVariable String firstName) {
        long count = userService.count(startAge, endAge, firstName);
        return ResultHandler.success(count);
    }

    @DeleteMapping("/remove/first_name/{firstName}")
    public ResultHandler<Boolean> remove(@PathVariable String firstName) {
        long removed = userService.remove(firstName);
        return ResultHandler.success(removed > 0);
    }
}
