package com.yong.spring.data.jpa.repository.custom;

import com.yong.spring.data.jpa.domain.User;

/**
 * 扩展一个自定义的Repository
 */
public interface CustomizedUserRepository {
    void printUser(User user);
}
