package com.yong.spring.data.repository.custom;

import com.yong.spring.data.domain.User;

/**
 * 扩展一个自定义的Repository
 */
public interface CustomizedUserRepository {
    void printUser(User user);
}
