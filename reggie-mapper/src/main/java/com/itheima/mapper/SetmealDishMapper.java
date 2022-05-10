package com.itheima.mapper;

import com.itheima.domain.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SetmealDishMapper {

    void save(SetmealDish dishDto);

    void deleteBySetmealIds(Long[] ids);


    @Update("update setmeal_dish set is_deleted = 1 where setmeal_id = #{id}")
    void deleteBySetmealId(Long id);
}
