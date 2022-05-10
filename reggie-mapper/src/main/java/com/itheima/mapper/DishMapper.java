package com.itheima.mapper;

import com.itheima.domain.Dish;
import com.itheima.domain.SetmealDish;
import com.itheima.dto.DishDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DishMapper {

    @Select("select * from dish order by sort ASC")
    List<Dish> page();

    List<Dish> selectByName(String name);

    void save(Dish dish);
    //根据id查询
    @Select("select * from dish where id = #{id} and is_deleted = 0")
    DishDto getById(Long id);

    void update(DishDto dishDto);

    @Select("select * from dish where category_id = #{categoryId} and status = 1 and is_deleted = 0")
    List<Dish> findByCid(long categoryId);

    int countStatusByIds(Long[] ids, int status);

    void deleteByIds(Long[] ids);


    @Select("select * from setmeal_dish where setmeal_id = #{id} and is_deleted = 0")
    List<SetmealDish> listByDishId(Long id);

    @Update("update dish set status = #{status} where id = #{ids}")
    void updateStatus(Integer status, Long ids);

    List<Dish> getDishList(long categoryId, int status);
}
