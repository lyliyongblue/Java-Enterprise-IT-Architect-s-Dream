package com.yong.spring.jpa.jms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue
    private Long userId;
    private String username;
    private Integer deposit;

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

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }
}
