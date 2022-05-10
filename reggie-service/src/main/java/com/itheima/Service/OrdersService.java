package com.itheima.Service;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Orders;
import com.itheima.dto.OrdersDto;

import java.util.Date;

public interface OrdersService {
    PageInfo<Orders> page(int page, int pageSize, String number);

    void submitOrder(Orders orders);

    PageInfo<OrdersDto> findAll(int page, int pageSize);
}
