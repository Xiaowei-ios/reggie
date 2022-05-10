package com.itheima.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.Service.CategoryService;
import com.itheima.domain.Category;
import com.itheima.domain.Employee;

import com.itheima.mapper.CategoryMapper;
import com.itheima.common.exception.BusinessException;
import com.itheima.common.util.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author 29262
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public PageInfo<Category> page(int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        return new PageInfo<>(categoryMapper.page());
    }

    @Override
    public void save(Category category) {
        log.info("------> 拦截器，当前线程：{}",Thread.currentThread());
        categoryMapper.save(category);
    }

    @Override
    public void update(Category category) {
        category.setUpdateUser(((Employee) ThreadLocalUtil.get()).getId());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    @Override
    public void del(long id) {
       int num= categoryMapper.countDishByCid(id);
       if(num>0){
          throw new BusinessException(500,"该分类下有菜品，不能删除");
       }
        num = categoryMapper.countSetmealByCid(id);
        if(num>0){
            throw new BusinessException(500,"该分类下有套餐，不能删除");
        }
        categoryMapper.del(id);
    }

    @Override
    public List<Category> getBtType(long type) {
        return categoryMapper.getBtType(type);
    }

    @Override
    public List<Category> list() {
        return categoryMapper.page();
    }
}
