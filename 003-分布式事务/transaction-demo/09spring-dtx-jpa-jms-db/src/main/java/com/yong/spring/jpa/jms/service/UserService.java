package com.yong.spring.jpa.jms.service;

import com.yong.spring.jpa.jms.commons.BusinessException;
import com.yong.spring.jpa.jms.commons.JacksonUtil;
import com.yong.spring.jpa.jms.domain.User;
import com.yong.spring.jpa.jms.repository.UserRepository;
import com.yong.spring.jpa.jms.vo.AddUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Objects;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final static String QUEUE = "09:new:user";
    private final UserRepository userRepository;
    private final JmsTemplate jmsTemplate;

    public UserService(UserRepository userRepository, JmsTemplate jmsTemplate) {
        this.userRepository = userRepository;
        this.jmsTemplate = jmsTemplate;
    }

    @Transactional
    public void sendCreateUserMsg(AddUser addUser) {
        String msg = Objects.requireNonNull(JacksonUtil.objectToJson(addUser));
        jmsTemplate.convertAndSend(QUEUE, msg);
        logger.info(QUEUE + " send success -> " + msg);
    }

    @Transactional
    @JmsListener(destination = QUEUE)
    public void createUser(String userJson) throws IOException {
        logger.info(QUEUE + " receive success -> " + userJson);
        AddUser addUser = JacksonUtil.jsonToObject(userJson, AddUser.class);
        User user = new User();
        BeanUtils.copyProperties(addUser, user);
        userRepository.save(user);
        if(addUser.getUsername().equals("error")) {
            throw new BusinessException("非法的用户名");
        }
        jmsTemplate.convertAndSend(QUEUE + ":reply", "add user success, user_id: " + user.getUserId());
        if(addUser.getUsername().equals("error2")) {
            throw new BusinessException("发送完应答消息后，无法正常介绍业务");
        }
    }
}
