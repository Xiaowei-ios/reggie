package com.itheima.mapper;

import com.itheima.domain.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


/**
 * @author 29262
 */
@Mapper
public interface EmployeeMapper {
    @Select("select * from employee where username = #{username}")
    Employee login(String username);

    @Select("select * from employee where phone = #{phone}")
    Employee getByPhone(String phone);

    /**
     */
    @Select("select * from employee where id_number = #{idNumber}")
    Employee getByIdNumber(String idNumber);

    void save(Employee employee);

    List<Employee> pageFindAll(String name);


    @Select("select * from employee where id = #{id}")
    Employee findById(Long id);

    void update(Employee employee);
}
