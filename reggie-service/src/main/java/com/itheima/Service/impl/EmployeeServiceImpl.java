package com.itheima.Service.impl;


import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.Service.EmployeeService;
import com.itheima.domain.Employee;
import com.itheima.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    @Override
    public Employee login(String username) {
        return employeeMapper.login(username);
    }

    @Override
    public Employee getByPhone(String phone) {
        return employeeMapper.getByPhone(phone);
    }

    @Override
    public Employee getByIdNumber(String idNumber) {
        return employeeMapper.getByIdNumber(idNumber);
    }

    @Override
    public void save(Employee employee) {
        employeeMapper.save(employee);
    }

    @Override
    public PageInfo<Employee> pageFindAll(int page, int pageSize, String name) {
        PageHelper.startPage(page,pageSize);
        if (StrUtil.isNotBlank(name)){
            name = "%"+name+"%";
        }
        List<Employee> list = employeeMapper.pageFindAll(name);
        //3. 封装分页结果
        PageInfo<Employee> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    @Override
    public Employee findById(Long id) {
        return employeeMapper.findById(id);
    }

    @Override
    public void update(Employee employee) {
        employeeMapper.update(employee);
    }
}
