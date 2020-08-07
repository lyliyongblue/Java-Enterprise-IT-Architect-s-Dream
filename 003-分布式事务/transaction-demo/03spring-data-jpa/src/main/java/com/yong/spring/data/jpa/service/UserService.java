package com.yong.spring.data.jpa.service;

import com.yong.spring.data.jpa.domain.User;
import com.yong.spring.data.jpa.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /** 根据方法名解析查询条件 */
    public List<User> queryByCreatedTime(LocalDateTime start, LocalDateTime end, int pageNo, int pageSize) {
        pageNo--;
        pageNo = Math.max(0, pageNo);
        pageSize = Math.max(5, pageSize);
        pageSize = Math.min(pageSize, 200);
        return userRepository.queryByCreatedTimeBetween(start, end, PageRequest.of(pageNo, pageSize));
    }

    public Optional<User> get(Long userId) {
        return userRepository.findById(userId);
    }

    /** 派生计数查询 */
    public long count(Integer startAge, Integer endAge, String firstName) {
        return userRepository.countByAgeBetweenAndFirstName(startAge, endAge, firstName);
    }

    /** 派生的删除查询 */
    public long remove(String firstName) {
        return userRepository.removeByFirstName(firstName);
    }

    @Transactional
    public void add(Iterable<User> users) {
        userRepository.saveAll(users);
    }
}
