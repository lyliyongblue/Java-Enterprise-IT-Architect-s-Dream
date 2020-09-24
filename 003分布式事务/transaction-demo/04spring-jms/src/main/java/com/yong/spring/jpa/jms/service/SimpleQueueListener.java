package com.yong.spring.jpa.jms.service;

import com.yong.spring.jpa.jms.commons.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用于验证Jms消费消息的事务性
 */
@Service
public class SimpleQueueListener {
    private final Logger logger = LoggerFactory.getLogger(SimpleQueueListener.class);

    private final JmsTemplate jmsTemplate;

    public SimpleQueueListener(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Transactional
    @JmsListener(destination = "simple-queue")
    public void handler(String msg) {
        logger.info("Get msg: {}", msg);
        String reply = "Reply-" + msg;
        jmsTemplate.convertAndSend("simple-queue:reply", reply);
        // 失败会重试7次
        if(msg.contains("error")) {
            throw new BusinessException("JMS事务回滚");
        }
    }

}
