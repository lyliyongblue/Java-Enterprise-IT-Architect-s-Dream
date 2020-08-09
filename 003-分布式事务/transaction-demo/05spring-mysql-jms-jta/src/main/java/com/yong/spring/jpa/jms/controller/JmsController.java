package com.yong.spring.jpa.jms.controller;

import com.yong.spring.jpa.jms.commons.ResultHandler;
import com.yong.spring.jpa.jms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JmsController {
    private final Logger logger = LoggerFactory.getLogger(JmsController.class);
    private final UserService userService;

    public JmsController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/simple/send/{msg}")
    public ResultHandler<Boolean> sendMsg(@PathVariable String msg) {
        userService.sendMsg(msg);
        return ResultHandler.success(Boolean.TRUE);
    }

    @JmsListener(destination = "simple-queue:reply")
    public void handler(String msg) {
        logger.info("Get reply msg: {}", msg);
    }
}
