package com.itheima.mapper;

import com.itheima.domain.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 29262
 */
@Mapper
public interface CategoryMapper {

    @Select("select * from category order by sort ASC")
    List<Category> page();

    void save(Category category);

    void update(Category category);

    int countDishByCid(long id);

    int countSetmealByCid(long id);

    @Delete("delete from category where id = #{id}")
    void del(long id);

    @Select("select * from category where id = #{categoryId}")
    Category getById(Long categoryId);

    List<Category> getBtType(long type);

}
