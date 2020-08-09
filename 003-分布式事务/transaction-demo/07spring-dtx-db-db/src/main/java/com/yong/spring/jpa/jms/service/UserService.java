package com.yong.spring.jpa.jms.service;

import com.yong.spring.jpa.jms.commons.BusinessException;
import com.yong.spring.jpa.jms.domain.User;
import com.yong.spring.jpa.jms.domain.UserOrder;
import com.yong.spring.jpa.jms.vo.UserOrderVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private static final String SQL_UPDATE_DEPOSIT = "UPDATE user set deposit = deposit - ? where user_id = ?";
    private static final String SQL_CREATE_ORDER = "insert into user_order (user_id, title, amount) value (?, ?, ?)";

    private final JdbcTemplate userJdbcTemplate;
    private final JdbcTemplate orderJdbcTemplate;

    public UserService(JdbcTemplate userJdbcTemplate, JdbcTemplate orderJdbcTemplate) {
        this.userJdbcTemplate = userJdbcTemplate;
        this.orderJdbcTemplate = orderJdbcTemplate;
    }

    @Transactional
    public void createOrder(UserOrder order) {
        userJdbcTemplate.update(SQL_UPDATE_DEPOSIT, order.getAmount(), order.getUserId());
        if(order.getTitle().equals("error1")) {
            throw new BusinessException("更新余额后报错");
        }
        orderJdbcTemplate.update(SQL_CREATE_ORDER, order.getUserId(), order.getTitle(), order.getAmount());
        if(order.getTitle().equals("error2")) {
            throw new BusinessException("提交订单后报错");
        }
    }

    public UserOrderVo get(Long userId) {
//        userJdbcTemplate.query
        return null;
    }

    @Transactional
    public void createUser(User user) {
        userJdbcTemplate.update("insert into user (username, deposit) value (?, ?)", user.getUsername(), user.getDeposit());
    }
}
