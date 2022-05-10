package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.Service.DishService;
import com.itheima.domain.Dish;
import com.itheima.dto.DishDto;
import com.itheima.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    DishService dishService;

    @GetMapping("page")
    public R<PageInfo<Dish>> page(int page, int pageSize,String name){
        log.info("page:{},pageSize:{}",page,pageSize,name);
        return R.success(dishService.page(page,pageSize,name));
    }

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.saveFlavor(dishDto);
        return R.success("添加成功");
    }
    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id){
        return R.success(dishService.getById(id));
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.update(dishDto);
        return R.success("更新成功");
    }

    @GetMapping("/list")
    public R<List<Dish>> findByCid(long categoryId){
        return R.success(dishService.findByCid(categoryId));
    }


    @DeleteMapping
    public R<String> delete(Long[] ids){
        dishService.delete(ids);
        return R.success("删除成功");
    }

    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status,Long[] ids){
        dishService.updateStatus(status,ids);
        return R.success("更新状态成功");
    }
}
