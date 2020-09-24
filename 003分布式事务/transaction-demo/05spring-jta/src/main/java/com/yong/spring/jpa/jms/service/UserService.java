package com.yong.spring.jpa.jms.service;

import com.yong.spring.jpa.jms.domain.User;
import com.yong.spring.jpa.jms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void add(User user) {
        userRepository.save(user);
    }

    public Optional<User> get(Long userId) {
        return userRepository.findById(userId);
    }
}
