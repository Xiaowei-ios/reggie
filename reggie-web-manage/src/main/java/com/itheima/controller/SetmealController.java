package com.itheima.controller;


import com.github.pagehelper.PageInfo;
import com.itheima.Service.SetmealService;
import com.itheima.domain.Setmeal;
import com.itheima.common.R;
import com.itheima.dto.SetmealDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {


    @Autowired
    SetmealService setmealService;

    @GetMapping("page")
    public R<PageInfo<Setmeal>> page(int page, int pageSize,String name){
        PageInfo<Setmeal> pageInfo = setmealService.findPage(page, pageSize, name);
        return R.success(pageInfo);
    }


    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
          setmealService.save(setmealDto);
          return R.success("添加成功");
    }

    @DeleteMapping
    public R<String> delete(Long[] ids){
        setmealService.delete(ids);
        return R.success("删除成功");
    }

    @GetMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable Long id){
         return R.success(setmealService.getById(id));
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        setmealService.update(setmealDto);
        return R.success("更新成功");
    }
    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status,Long[] ids){
        setmealService.updateStatus(status,ids);
        return R.success("更新状态成功");
    }
}
