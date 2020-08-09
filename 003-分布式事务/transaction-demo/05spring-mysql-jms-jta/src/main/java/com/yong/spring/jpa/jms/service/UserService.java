package com.yong.spring.jpa.jms.service;

import com.yong.spring.jpa.jms.commons.BusinessException;
import com.yong.spring.jpa.jms.domain.User;
import com.yong.spring.jpa.jms.repository.UserRepository;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final JmsTemplate jmsTemplate;
    private final UserRepository userRepository;

    public UserService(JmsTemplate jmsTemplate, UserRepository userRepository) {
        this.jmsTemplate = jmsTemplate;
        this.userRepository = userRepository;
    }

    @Transactional
    public void add(User user) {
        userRepository.save(user);
        sendMsg(user.getFirstName());
    }

    public Optional<User> get(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public void sendMsg(String msg) {
        jmsTemplate.convertAndSend("simple-queue", msg);
        if(msg.equals("error")) {
            throw new BusinessException("不支持的名称");
        }
    }
}
