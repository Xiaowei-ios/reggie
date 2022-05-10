package com.itheima.controller;


import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.itheima.Service.CategoryService;
import com.itheima.constant.EmployeeConstant;
import com.itheima.domain.Category;
import com.itheima.domain.Employee;
import com.itheima.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("page")
    public R<PageInfo<Category>>  page(int page,int pageSize){
        log.info("page:{},pageSize:{}",page,pageSize);
        return R.success(categoryService.page(page,pageSize));
    }

    @PostMapping
    public R<String> save(@RequestBody Category category, HttpSession session){
        log.info("------> 拦截器，当前线程：{}",Thread.currentThread().getId());
        category.setId(IdUtil.getSnowflakeNextId());
        Employee emp = (Employee) session.getAttribute(EmployeeConstant.SESSION);
        category.setCreateUser(emp.getId());
        category.setUpdateUser(emp.getId());
        categoryService.save(category);
        return R.success("添加成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("------> 拦截器，当前线程：{}",Thread.currentThread().getId());
        log.info("修改的数据：{}",category);
        categoryService.update(category);
        return R.success("修改成功");
    }
    @DeleteMapping
    public R<String> del(long id){
        log.info("------> 拦截器，当前线程：{}",Thread.currentThread().getId());
        log.info("删除的id：{}",id);
        categoryService.del(id);
        return R.success("删除成功");
    }

    @GetMapping("list")
    public R<List<Category>> getBtType(long type){
        return R.success(categoryService.getBtType(type));
    }
}
