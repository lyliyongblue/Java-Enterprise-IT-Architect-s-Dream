package com.yong.spring.jpa.jms.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddUser {
    @NotBlank(message = "FirstName 不能为空")
    private String username;
    @NotNull(message = "deposit 不能为空")
    private Integer deposit;

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
