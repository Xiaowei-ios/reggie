package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.Service.OrdersService;
import com.itheima.common.R;
import com.itheima.domain.Orders;
import com.itheima.dto.OrdersDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    OrdersService ordersService;

    @PostMapping("submit")
    public R<String> submitOrder(@RequestBody Orders orders) {
        log.info("接收到的订单信息：{}", orders);
        ordersService.submitOrder(orders);
        return R.success("下单成功");
    }

    @GetMapping("/userPage")
    public R<PageInfo<OrdersDto>> pageInfoR(@RequestParam int page, @RequestParam int pageSize) {
           return R.success(ordersService.findAll(page,pageSize));
    }
}
