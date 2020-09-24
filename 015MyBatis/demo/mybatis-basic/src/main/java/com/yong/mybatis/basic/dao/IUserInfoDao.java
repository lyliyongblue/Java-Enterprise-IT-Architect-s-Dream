package com.yong.mybatis.basic.dao;

import com.yong.mybatis.basic.entity.UserInfo;

import java.util.Optional;

public interface IUserInfoDao {
	Optional<UserInfo> selectUserInfo(Long userId);
	int insert(UserInfo userInfo);
}
