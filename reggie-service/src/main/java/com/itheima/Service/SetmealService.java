package com.itheima.Service;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Setmeal;
import com.itheima.dto.SetmealDto;

import java.util.List;

public interface SetmealService {
    PageInfo<Setmeal> findPage(int page, int pageSize, String name);

    void save(SetmealDto setmealDto);

    void delete(Long[] ids);

    SetmealDto getById(Long id);

    void update(SetmealDto setmealDto);

    void updateStatus(Integer status, Long[] ids);

    List<Setmeal> getByCidAndStatus(long categoryId, int status);

    List<Setmeal> getByCid(long categoryId);
}
