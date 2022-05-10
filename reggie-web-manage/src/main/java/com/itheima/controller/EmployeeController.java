package com.itheima.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageInfo;
import com.itheima.Service.EmployeeService;
import com.itheima.constant.EmployeeConstant;
import com.itheima.domain.Employee;
import com.itheima.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;


@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpSession session){
        log.info("登录请求参数：{}",employee);
        String username = employee.getUsername();
        String password = employee.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return R.error("用户名或密码不能为空");
        }
        Employee emp= employeeService.login(employee.getUsername());
        if (emp == null){
            return R.error("用户名不存在");
        }
        if(emp.getStatus() == 0){
            return R.error("用户已被禁用");
        }
        String pwd = SecureUtil.md5(password);
        if (!pwd.equals(emp.getPassword())){
            return R.error("密码错误");
        }
        session.setAttribute(EmployeeConstant.SESSION,emp);
        Employee employee1 = new Employee();
        employee1.setId(emp.getId());
        employee1.setUsername(emp.getUsername());
        return R.success(employee1);
    }
     @PostMapping("/logout")
      public R<String> logout(HttpSession session){
         log.info("退出登录");
         session.invalidate();
         return R.success("退出成功");
     }

    @PostMapping
    public R<String> save(@RequestBody  Employee employee, HttpSession session){
        log.info("保存员工信息：{}",employee);
        String phone = employee.getPhone();
        String idNumber = employee.getIdNumber();
        if (StrUtil.isBlank(employee.getUsername())||StrUtil.isBlank(employee.getPhone())||StrUtil.isBlank(employee.getIdNumber())){
            return R.error("信息不能为空，请重新输入");
        }
        Employee username = employeeService.login(employee.getUsername());
        if (username != null){
            return R.error("账号["+username.getUsername()+"]已注册");
        }
        //2.2. 校验手机号是否已注册
        if(StrUtil.isBlank(phone) ||
                employeeService.getByPhone(phone) != null){
            return R.error("手机号["+phone+"]已注册");
        }

        //2.3. 校验身份证号是否已注册
        if(StrUtil.isBlank(idNumber) ||
                employeeService.getByIdNumber(idNumber) != null){
            return R.error("身份证号["+idNumber+"]已注册");
        }

        employee.setId(IdUtil.getSnowflakeNextId());
        employee.setPassword(SecureUtil.md5("123456"));
        Employee attribute = (Employee) session.getAttribute(EmployeeConstant.SESSION);
        employee.setCreateUser(attribute.getId());
        employee.setUpdateUser(attribute.getId());
        employeeService.save(employee);
        return R.success("保存成功");
    }
    @GetMapping("page")
    public R<PageInfo<Employee>> pageFindAll(int page,int pageSize,String name){
        log.info("分页查询员工信息：{}",page);
        return R.success(employeeService.pageFindAll(page,pageSize,name));
    }
    @GetMapping("/{id}")
    public R<Employee> findById(@PathVariable("id") Long id){
        log.info("根据id查询员工信息：{}",id);
        return R.success(employeeService.findById(id));
    }

    @PutMapping
    public R<String> update(@RequestBody Employee employee,HttpSession session){
        log.info("修改员工信息：{}",employee);
        if (employee.getId() == null){
            return R.error("id不能为空");
        }
        employee.setUpdateTime(LocalDateTime.now());
        Employee emp = (Employee) session.getAttribute(EmployeeConstant.SESSION);
        employee.setUpdateUser(emp.getId());
        employeeService.update(employee);
        return R.success("修改成功");
    }
}

