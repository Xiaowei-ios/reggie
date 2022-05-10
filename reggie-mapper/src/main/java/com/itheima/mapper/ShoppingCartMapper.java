package com.itheima.mapper;

import com.itheima.domain.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    ShoppingCart findByUidAndDid(ShoppingCart shoppingCart);

    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateByPrimaryKey(ShoppingCart cart);

    void insert(ShoppingCart shoppingCart);

    @Select("select * from shopping_cart where user_id = #{uid}")
    List<ShoppingCart> findByUid(Long aLong);

    @Delete("delete from shopping_cart where id = #{id}")
    void deleteByPrimaryKey(Long id);

    @Delete("delete from shopping_cart where user_id = #{uid}")
    void clean(Long uid);
}
