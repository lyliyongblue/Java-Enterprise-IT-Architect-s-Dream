package cn.itcast.ssh.service.impl;

import cn.itcast.ssh.dao.IEmployeeDao;
import cn.itcast.ssh.service.IEmployeeService;

public class EmployeeServiceImpl implements IEmployeeService {

	private IEmployeeDao employeeDao;

	public void setEmployeeDao(IEmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	
}
