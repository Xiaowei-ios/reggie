package com.itheima.Service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.Service.DishService;
import com.itheima.common.exception.BusinessException;
import com.itheima.converter.DishConverter;
import com.itheima.domain.*;
import com.itheima.dto.DishDto;
import com.itheima.mapper.CategoryMapper;
import com.itheima.mapper.DishMapper;
import com.itheima.mapper.DishFlavorMapper;
import com.itheima.common.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl  implements DishService {

    @Autowired
    DishMapper dishMapper;

    @Autowired
    DishFlavorMapper dishFlavorMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public PageInfo<Dish> page(int page, int pageSize, String name) {
        PageHelper.startPage(page, pageSize);
        if (StrUtil.isNotBlank(name)) {
            name = "%" + name + "%";
        }
        List<Dish> dishList = dishMapper.selectByName(name);
        PageInfo pageInfo = new PageInfo(dishList);
        ArrayList<DishDto> dishDtos = new ArrayList<>();
        for (Dish dish : dishList) {
            DishDto dishDto = new DishDto();
            BeanUtil.copyProperties(dish, dishDto);
            Category category = categoryMapper.getById(dish.getCategoryId());
            dishDto.setCategoryName(category.getName());
            dishDtos.add(dishDto);
        }
        pageInfo.setList(dishDtos);
        return pageInfo;
    }

    @Override
    @Transactional //开启事务
    public void saveFlavor(DishDto dishDto) {
        long dishId = IdUtil.getSnowflakeNextId();
        dishDto.setId(dishId);
        Long userId = ((Employee) ThreadLocalUtil.get()).getId();
        dishDto.setCreateUser(userId);
        dishDto.setUpdateUser(userId);
        dishMapper.save(dishDto);

        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setId(IdUtil.getSnowflakeNextId());
            flavor.setDishId(dishId);
            flavor.setCreateUser(userId);
            flavor.setUpdateUser(userId);
            dishFlavorMapper.saveflavor(flavor);
        }
    }

    @Override
    public DishDto getById(Long id) {
        //1. 查询菜品
        DishDto dishDto = dishMapper.getById(id);
        //2. 查询菜品口味数据
        List<DishFlavor> flavors = dishFlavorMapper.listByDishId(id);
        dishDto.setFlavors(flavors);
        //3. 返回结果
        return dishDto;
    }

    @Override
    public void update(DishDto dishDto) {
        dishDto.setUpdateTime(LocalDateTime.now());
        Long userId = ((Employee) ThreadLocalUtil.get()).getId();
        dishDto.setUpdateUser(userId);
        dishMapper.update(dishDto);
        dishFlavorMapper.deleteByDishId(dishDto.getId());
        dishDto.getFlavors().forEach(flavor -> {
            flavor.setId(IdUtil.getSnowflakeNextId());
            flavor.setDishId(dishDto.getId());
            flavor.setId(IdUtil.getSnowflakeNextId());
            //设置菜品id
            flavor.setDishId(dishDto.getId());
            //创建人 修改人
            flavor.setCreateUser(userId);
            flavor.setUpdateUser(userId);
            //保存口味
            dishFlavorMapper.saveflavor(flavor);
        });
    }

    @Override
    public List<Dish> findByCid(long categoryId) {
        return dishMapper.findByCid(categoryId);
    }

    @Override
    @Transactional
    public void delete(Long[] ids) {
        if (ids != null && ids.length > 0) {
            int count = dishMapper.countStatusByIds(ids, 1);
            if (count > 0) {
                throw new BusinessException(500, "该菜品已经上架，不能删除");
            }
            dishMapper.deleteByIds(ids);
            dishFlavorMapper.deleteByDishIds(ids);
        } else {
            throw new BusinessException(500, "请选择要删除的菜品");
        }
    }

    @Override
    @Transactional
    public void updateStatus(Integer status, Long[] ids) {
        if (ids != null && ids.length > 0) {
            int count = dishMapper.countStatusByIds(ids, 1);
            if (count > 0) {
                throw new BusinessException(500, "操作菜品中关联有在售套餐，请停售套餐后重试");
            }

            for (Long id : ids) {
                dishMapper.updateStatus(status, id);
            }
        }
    }

    @Override
    public List<DishDto> getDishList(long categoryId, int status) {
        List<DishDto> dishDtoList = DishConverter.INSTANCE.toDtos(dishMapper.getDishList(categoryId, status));
        dishDtoList.forEach(dishDto -> {
            dishDto.setFlavors(dishFlavorMapper.listByDishId(dishDto.getId()));
        });
        return dishDtoList;
    }
}
