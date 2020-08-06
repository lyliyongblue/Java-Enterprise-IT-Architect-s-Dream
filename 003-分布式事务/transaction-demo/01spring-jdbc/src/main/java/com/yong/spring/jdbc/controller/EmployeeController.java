package com.yong.spring.jdbc.controller;

import com.yong.spring.jdbc.commons.ResultHandler;
import com.yong.spring.jdbc.dao.EmployeeDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

    private final EmployeeDao employeeDao;

    public EmployeeController(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @GetMapping("/count")
    public ResultHandler<Integer> count() {
        Integer count = employeeDao.getCountOfEmployees();
        return ResultHandler.success(count);
    }
}
