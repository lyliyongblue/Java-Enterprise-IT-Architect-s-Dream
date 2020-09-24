package com.yong.spring.data.repository.custom;

import com.yong.spring.data.domain.User;

/** 类名必须是CustomizedUserRepository + Impl */
public class CustomizedUserRepositoryImpl implements CustomizedUserRepository {
    @Override
    public void printUser(User user) {
        System.out.println("user: " + user);
    }
}
