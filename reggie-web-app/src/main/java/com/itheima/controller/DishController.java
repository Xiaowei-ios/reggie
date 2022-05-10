package com.itheima.controller;

import com.itheima.Service.DishService;
import com.itheima.common.R;
import com.itheima.dto.DishDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    DishService dishService;


    @GetMapping("list")
    public R<List<DishDto>> getDishList(@RequestParam long categoryId,@RequestParam int status) {
        List<DishDto> dishDtoList = dishService.getDishList(categoryId, status);
        return R.success(dishDtoList);
    }
}
