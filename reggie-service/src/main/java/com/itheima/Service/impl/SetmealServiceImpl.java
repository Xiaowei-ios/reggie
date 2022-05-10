package com.itheima.Service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.Service.CategoryService;
import com.itheima.Service.SetmealService;
import com.itheima.common.exception.BusinessException;
import com.itheima.common.util.ThreadLocalUtil;
import com.itheima.converter.SetmealConverter;
import com.itheima.domain.*;
import com.itheima.dto.DishDto;
import com.itheima.dto.SetmealDto;
import com.itheima.mapper.CategoryMapper;
import com.itheima.mapper.DishMapper;
import com.itheima.mapper.SetmealDishMapper;
import com.itheima.mapper.SetmealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    SetmealMapper setmealMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    SetmealDishMapper setmealDishMapper;

    @Autowired
    DishMapper dishMapper;
    @Override
    public PageInfo<Setmeal> findPage(int page, int pageSize,String name) {
        PageHelper.startPage(page,pageSize);
        if (StrUtil.isNotBlank(name)){
            name="%"+name+"%";
        }
        List<Setmeal> setmealList= setmealMapper.selectByName(name);
        PageInfo pageInfo=new PageInfo(setmealList);
        ArrayList<SetmealDto> setmealDtos = new ArrayList<>();
        for (Setmeal setmeal : setmealList) {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtil.copyProperties(setmeal,setmealDto);
            Category category =categoryMapper.getById(setmeal.getCategoryId());
            setmealDto.setCategoryName(category.getName());
            setmealDtos.add(setmealDto);
        }
        pageInfo.setList(setmealDtos);
        return pageInfo;
    }

    @Override
    @Transactional
    public void save(SetmealDto setmealDto) {
        Setmeal setmeal = SetmealConverter.INSTANCE.toPO(setmealDto);
        long setmealId = IdUtil.getSnowflakeNextId();
        setmeal.setId(setmealId);
        Long uid = ((Employee) ThreadLocalUtil.get()).getId();
        setmeal.setCreateUser(uid);
        setmeal.setUpdateUser(uid);
        setmealMapper.save(setmeal);
        setmealDto.getSetmealDishes().forEach(dishDto -> {
            dishDto.setId(IdUtil.getSnowflakeNextId());
            dishDto.setSetmealId(setmealId);
            dishDto.setCreateUser(uid);
            dishDto.setUpdateUser(uid);
            setmealDishMapper.save(dishDto);
       });
    }

    @Override
    public void delete(Long[] ids) {
        if (ids==null||ids.length==0){
            throw new BusinessException(500,"请选择要删除的套餐");
        }
        int count= setmealMapper.countStatusByIds(ids,1);
        if (count>0){
            throw new BusinessException(500,"该套餐已经被预约，不能删除");
        }
        setmealMapper.deleteByIds(ids);
        setmealDishMapper.deleteBySetmealIds(ids);
    }

    @Override
    public SetmealDto getById(Long id) {
         SetmealDto setmealDto=setmealMapper.getById(id);
         List<SetmealDish> dish=dishMapper.listByDishId(id);
         setmealDto.setSetmealDishes(dish);
         return setmealDto;
    }

    @Override
    @Transactional
    public void update(SetmealDto setmealDto) {
        setmealDto.setUpdateTime(LocalDateTime.now());
        Long userId = ((Employee) ThreadLocalUtil.get()).getId();
        setmealDto.setUpdateUser(userId);
        setmealMapper.update(setmealDto);
        setmealDishMapper.deleteBySetmealId(setmealDto.getId());
        setmealDto.getSetmealDishes().forEach(dishDto -> {
            dishDto.setId(IdUtil.getSnowflakeNextId());
            dishDto.setSetmealId(setmealDto.getId());
            dishDto.setCreateUser(userId);
            dishDto.setUpdateUser(userId);
            setmealDishMapper.save(dishDto);
        });
    }

    @Override
    @Transactional
    public void updateStatus(Integer status, Long[] ids) {
        if (ids==null||ids.length==0){
            throw new BusinessException(500,"请选择要修改的套餐");
        }
        for (Long id : ids) {
            setmealMapper.updateStatus(status,id);
        }
    }

    @Override
    public List<Setmeal> getByCidAndStatus(long categoryId, int status) {
        return setmealMapper.getByCidAndStatus(categoryId,status);
    }

    @Override
    public List<Setmeal> getByCid(long categoryId) {
        return setmealMapper.getByCid(categoryId);
    }
}
