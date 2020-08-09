package com.yong.spring.jpa.jms.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddUser {
    @NotBlank(message = "FirstName 不能为空")
    private String firstName;
    @NotBlank(message = "LastName 不能为空")
    private String lastName;
    @NotNull(message = "年龄不能为空")
    private Integer age;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
