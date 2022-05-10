package com.itheima.mapper;

import com.itheima.domain.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    void saveflavor(DishFlavor flavor);

    @Select("select * from dish_flavor where dish_id = #{id} and is_deleted = 0")
    List<DishFlavor> listByDishId(Long id);

    @Update("update dish_flavor set is_deleted = 1 where dish_id = #{id}")
    void deleteByDishId(Long id);

    void deleteByDishIds(Long[] ids);
}
