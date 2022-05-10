package com.itheima.Service;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Dish;
import com.itheima.dto.DishDto;

import java.util.List;

public interface DishService {

    PageInfo<Dish> page(int page, int pageSize,String name);

    void saveFlavor(DishDto dishDto);

    DishDto getById(Long id);

    void update(DishDto dishDto);

    List<Dish>  findByCid(long categoryId);

    void delete(Long[] ids);

    void updateStatus(Integer status, Long[] ids);


    List<DishDto> getDishList(long categoryId, int status);
}
