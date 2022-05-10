package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.Service.OrdersService;
import com.itheima.domain.Orders;
import com.itheima.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    OrdersService ordersService;


    @GetMapping("page")
    public R<PageInfo<Orders>> page(int page, int pageSize, String number) {
        return R.success(ordersService.page(page, pageSize,number));
    }

}
