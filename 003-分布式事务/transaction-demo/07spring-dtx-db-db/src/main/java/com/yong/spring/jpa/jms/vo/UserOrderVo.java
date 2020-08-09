package com.yong.spring.jpa.jms.vo;

import com.yong.spring.jpa.jms.domain.User;
import com.yong.spring.jpa.jms.domain.UserOrder;

import java.util.List;

public class UserOrderVo {
    private User user;
    private List<UserOrder> orderList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UserOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<UserOrder> orderList) {
        this.orderList = orderList;
    }
}
