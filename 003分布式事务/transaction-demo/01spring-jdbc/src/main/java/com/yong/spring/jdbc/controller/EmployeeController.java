package com.yong.spring.jdbc.controller;

import com.yong.spring.jdbc.commons.ResultHandler;
import com.yong.spring.jdbc.dao.EmployeeDao;
import com.yong.spring.jdbc.entity.Employee;
import com.yong.spring.jdbc.vo.EmployeeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/all")
    public ResultHandler<List<Employee>> employees() {
        List<Employee> employees = employeeDao.getAllEmployees();
        return ResultHandler.success(employees);
    }

    @PostMapping("/add")
    public ResultHandler<Boolean> add(@Valid @RequestBody EmployeeVo request) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(request, employee);
        int result = employeeDao.addEmployee(employee);
        return ResultHandler.success(result == 1);
    }

    @GetMapping("/get/{id}")
    public ResultHandler<Employee> get(@PathVariable("id") Long id) {
        Employee employee = employeeDao.getEmployee(id);
        if(employee == null) {
            return ResultHandler.error(String.format("id: %d not existed", id), null);
        }
        return ResultHandler.success(employee);
    }

    @PostMapping("/batch/add")
    public ResultHandler<Boolean> batchAddEmployee(@Valid @RequestBody List<EmployeeVo> employees) {
        List<Employee> collect = employees.stream().map(vo -> {
            Employee employee = new Employee();
            BeanUtils.copyProperties(vo, employee);
            return employee;
        }).collect(Collectors.toList());
        employeeDao.batchAddEmployee(collect);
        return ResultHandler.success(Boolean.TRUE);
    }
}
