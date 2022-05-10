package com.itheima.mapper;

import com.itheima.domain.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrdersMapper {


    ArrayList<Orders> findByNumber(String number);

    void insert(Orders orders);


    @Select("select * from orders where user_id = #{aLong}")
    List<Orders> findByUid(Long aLong);
}
