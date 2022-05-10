package com.itheima.controller;

import com.itheima.Service.CategoryService;
import com.itheima.common.R;
import com.itheima.domain.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("list")
    public R<List<Category>> list(){
       return R.success(categoryService.list());
    }
}
