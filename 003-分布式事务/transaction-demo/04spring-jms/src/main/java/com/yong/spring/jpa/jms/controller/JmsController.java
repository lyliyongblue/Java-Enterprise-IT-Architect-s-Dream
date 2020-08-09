package com.yong.spring.jpa.jms.controller;

import com.yong.spring.jpa.jms.commons.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JmsController {
    private final Logger logger = LoggerFactory.getLogger(JmsController.class);
    private final JmsTemplate jmsTemplate;

    public JmsController(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostMapping("/simple/send/{msg}")
    public ResultHandler<Boolean> sendMsg(@PathVariable String msg) {
        jmsTemplate.convertAndSend("simple-queue", msg);
        return ResultHandler.success(Boolean.TRUE);
    }

    @Transactional
    @JmsListener(destination = "simple-queue:reply")
    public void handler(String msg) {
        logger.info("Get reply msg: {}", msg);
    }
}
