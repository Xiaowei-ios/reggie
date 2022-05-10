package com.itheima.mapper;

import com.itheima.domain.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface  OrderDetailMapper {
    void insert(OrderDetail orderDetail);

    @Select("select * from order_detail where order_id = #{id}")
    List<OrderDetail> getByOredesId(Long id);
}
