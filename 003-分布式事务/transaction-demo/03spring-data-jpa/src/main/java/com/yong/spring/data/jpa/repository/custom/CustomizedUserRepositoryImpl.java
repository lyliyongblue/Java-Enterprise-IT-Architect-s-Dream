package com.yong.spring.data.jpa.repository.custom;

import com.yong.spring.data.jpa.domain.User;

/** 类名必须是CustomizedUserRepository + Impl */
public class CustomizedUserRepositoryImpl implements CustomizedUserRepository {
    @Override
    public void printUser(User user) {
        System.out.println("user: " + user);
    }
}
