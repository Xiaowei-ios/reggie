package com.itheima.Service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.Service.OrdersService;
import com.itheima.common.exception.BusinessException;
import com.itheima.common.util.ThreadLocalUtil;
import com.itheima.converter.OrdersConverter;
import com.itheima.domain.AddressBook;
import com.itheima.domain.OrderDetail;
import com.itheima.domain.Orders;
import com.itheima.domain.ShoppingCart;
import com.itheima.dto.OrdersDto;
import com.itheima.mapper.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {


    @Autowired
    OrdersMapper ordersMapper;

    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    @Autowired
    AddressBookMapper addressBookMapper;

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public PageInfo<Orders> page(int page, int pageSize, String number) {
        //1. 开启分页查询
        PageHelper.startPage(page,pageSize);
        //2. 执行查询
        if(StrUtil.isNotBlank(number)){
            number = "%"+number+"%";
        }
        ArrayList<Orders> orders = ordersMapper.findByNumber(number);
        //3. 返回结果
        return new PageInfo<>(orders);
    }

    @Override
    @Transactional
    public void submitOrder(Orders orders) {
        //            1. 获取用户id
        Long uid = (Long) ThreadLocalUtil.get();
//    2. 查询收货地址
        AddressBook addressBook = addressBookMapper.getById(orders.getAddressBookId());
        if(addressBook==null){
            throw new BusinessException(500,"地址不能为空");
        }
//    3. 查询购物车列表
        List<ShoppingCart> shoppingCart = shoppingCartMapper.findByUid(uid);
//    4. 生成订单id
        long orderId = IdUtil.getSnowflakeNextId();
//    5. 保存订单明细
        BigDecimal amountSum=new BigDecimal("0");
        for (ShoppingCart cart : shoppingCart) {
            //       - 遍历购物车
            OrderDetail orderDetail=new OrderDetail();
            BeanUtils.copyProperties(cart,orderDetail);
//        - 创建订单明细
//        - 补全数据
            orderDetail.setOrderId(orderId);
            orderDetail.setId(IdUtil.getSnowflakeNextId());
//        - 保存订单明细
            orderDetailMapper.insert(orderDetail);
//        - 累加订单金额
            BigDecimal number=new BigDecimal(orderDetail.getNumber());
            BigDecimal amount=new BigDecimal(orderDetail.getAmount()+"");
            amountSum=amountSum.add(number.multiply(amount));
        }

//    6. 保存订单
        BeanUtils.copyProperties(addressBook,orders);
        orders.setStatus(2);
        orders.setUserId(uid);
        orders.setId(orderId);
        orders.setNumber(orderId+"");
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setAmount(amountSum);
        orders.setAddress(addressBook.getDetail());
//        - 补全数据
//        - 设置总金额
//        - 保存订单
        ordersMapper.insert(orders);
//    7. 清空购物车
        shoppingCartMapper.clean(uid);

    }

    @Override
    public PageInfo<OrdersDto> findAll(int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        Long uid = (Long) ThreadLocalUtil.get();
        List<Orders> orders=ordersMapper.findByUid(uid);
        PageInfo pageInfo=new PageInfo(orders);
        List<OrdersDto> ordersDtos = OrdersConverter.INSTANCE.toDtos(orders);
        for (OrdersDto ordersDto : ordersDtos) {
            Long OrderId = ordersDto.getId();
            List<OrderDetail> orderDetails = orderDetailMapper.getByOredesId(OrderId);
            ordersDto.setOrderDetails(orderDetails);
        }
        pageInfo.setList(ordersDtos);

        return pageInfo;
    }
}

