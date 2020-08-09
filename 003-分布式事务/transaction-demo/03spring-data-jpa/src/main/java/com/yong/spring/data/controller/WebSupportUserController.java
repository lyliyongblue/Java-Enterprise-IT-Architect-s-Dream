package com.yong.spring.data.controller;

import com.yong.spring.data.commons.ResultHandler;
import com.yong.spring.data.domain.User;
import com.yong.spring.data.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/web/user")
public class WebSupportUserController {
    private final UserService userService;

    public WebSupportUserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * jpa对web框架进行了增强，通过实体主键，直接转换成对应的数据库实例，
     * 如果id对应的实体不存在，则
     */
    @GetMapping("/{id}")
    public ResultHandler<User> user(@PathVariable("id") User user, @PathVariable("id") Long userId) {
        if (user == null) {
            return ResultHandler.error(String.format("user: %d not existed", userId), null);
        }
        return ResultHandler.success(user);
    }

    /**
     * 基于JPA的Web增强，自动将入参中的page/size/sort的3个字段封装称了Pageable实例
     * page 0索引，默认值为0。
     * size 默认值为20
     * sort format property,property(,ASC|DESC)  for example, ?sort=firstname&sort=lastname,asc.
     * @param pageable 分页
     */
    @GetMapping("/get_by_created_time")
    public ResultHandler<PagedModel<EntityModel<User>>> queryList(String start, String end, Pageable pageable, PagedResourcesAssembler<User> assembler) {
        LocalDateTime startTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        Page<User> page = userService.queryByCreatedTime(startTime, endTime, pageable);
        return ResultHandler.success(assembler.toModel(page));
    }
}
