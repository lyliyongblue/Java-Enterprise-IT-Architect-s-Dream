package com.yong.mybatis.basic.entity;

import java.time.LocalDateTime;

public class UserInfo {
	private Long userId;
	private String username;
	private String password;
	private Integer age;
	private Short status;
	private Long createdUserId;
	private LocalDateTime createdDate;
	private Long lastUpdateUserId;
	private LocalDateTime CREATED_DATE;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
}
