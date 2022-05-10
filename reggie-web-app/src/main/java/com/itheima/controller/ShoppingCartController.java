package com.itheima.controller;


import com.itheima.Service.ShoppingCartService;
import com.itheima.common.R;
import com.itheima.domain.ShoppingCart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> addShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        log.info("添加购物车");
        return R.success(shoppingCartService.addShoppingCart(shoppingCart));
    }

    @GetMapping("list")
    public R<List<ShoppingCart>> list(){
        return R.success(shoppingCartService.list());
    }

    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart) {
        log.info("减少购物车");
        return R.success(shoppingCartService.sub(shoppingCart));
    }

    @DeleteMapping("/clean")
    public R<String> clean(){
        log.info("清空购物车");
        shoppingCartService.clean();
        return R.success("清空成功");
    }
}
