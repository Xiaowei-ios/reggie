package com.itheima.converter;



import com.itheima.domain.Orders;
import com.itheima.dto.OrdersDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrdersConverter {

    OrdersConverter INSTANCE = Mappers.getMapper(OrdersConverter.class);

    List<OrdersDto> toDtos(List<Orders> list);
}
