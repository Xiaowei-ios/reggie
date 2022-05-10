package com.itheima.controller;


import com.itheima.Service.SetmealService;
import com.itheima.common.R;
import com.itheima.domain.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    SetmealService setmealService;

    @GetMapping("list")
    public R<List<Setmeal>> getByCidAndStatus(@RequestParam long categoryId, @RequestParam int status){
        return R.success(setmealService.getByCidAndStatus(categoryId,status));
    }

    @GetMapping("dish/{categoryId}")
    public R<List<Setmeal>> getByCidAndStatus(@PathVariable long categoryId){
        return R.success(setmealService.getByCid(categoryId));
    }
}
