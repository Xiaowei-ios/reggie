package com.itheima.Service;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Category;

import java.util.List;

/**
 * @author 29262
 */
public interface CategoryService {

    PageInfo<Category> page(int page, int pageSize);

    void save(Category category);

    void update(Category category);

    void del(long id);

    List<Category> getBtType(long type);

    List<Category> list();
}
