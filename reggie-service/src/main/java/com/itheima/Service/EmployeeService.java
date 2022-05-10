package com.itheima.Service;


import com.github.pagehelper.PageInfo;
import com.itheima.domain.Employee;

import java.util.List;

public interface EmployeeService {

    Employee login(String username);

    Employee getByPhone(String phone);

    Employee getByIdNumber(String idNumber);

    void save(Employee employee);

    PageInfo<Employee> pageFindAll(int page, int pageSize, String name);

    Employee findById(Long id);

    void update(Employee employee);
}
