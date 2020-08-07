package com.yong.spring.jdbc.dao;

import com.yong.spring.jdbc.commons.BusinessException;
import com.yong.spring.jdbc.entity.Employee;
import com.yong.spring.jdbc.entity.mapper.EmployeeMapper;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeDao {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer getCountOfEmployees() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM EMPLOYEE", Integer.class);
    }

    public List<Employee> getAllEmployees() {
        return jdbcTemplate.query("SELECT * FROM EMPLOYEE", new EmployeeMapper());
    }

    public boolean exist(Long id) {
        Long result = jdbcTemplate.queryForObject("select count(ID) from EMPLOYEE where ID = ?;", new Object[]{id}, Long.class);
        return result != null && result > 0;
    }

    @Transactional
    public int addEmployee(Employee emp) {
        boolean exist = exist(emp.getId());
        if(exist) {
            throw new BusinessException(String.format("id: %d, existed", emp.getId()));
        }
        String sql = "insert into EMPLOYEE (ID, FIRST_NAME, LAST_NAME, ADDRESS) values (?, ?, ?, ?);";
        return jdbcTemplate.update(sql, emp.getId(), emp.getFirstName(), emp.getLastName(), emp.getAddress());
    }

    public Employee getEmployee(long id) {
        String sql = "select * from EMPLOYEE where ID = ?";
        List<Employee> result = jdbcTemplate.query(sql, new Object[]{id}, new EmployeeMapper());
        if(CollectionUtils.isEmpty(result)) {
            return null;
        }
        return result.get(0);
    }

    @Transactional
    public void batchAddEmployee(List<Employee> employees) {
        String sql = "insert into EMPLOYEE (ID, FIRST_NAME, LAST_NAME, ADDRESS) values (?, ?, ?, ?);";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Employee employee = employees.get(i);
                ps.setLong(1, employee.getId());
                ps.setString(2, employee.getFirstName());
                ps.setString(3, employee.getLastName());
                ps.setString(4, employee.getAddress());
            }

            @Override
            public int getBatchSize() {
                return employees.size();
            }
        });
    }
}
