package com.yong.spring.jdbc.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EmployeeVo {
    @NotNull(message = "ID Not null")
    private Long id;
    @NotBlank(message = "First Name not be null")
    private String firstName;
    @NotBlank(message = "Last Name not be null")
    private String lastName;
    @NotBlank(message = "Address not be null")
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
