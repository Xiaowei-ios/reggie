package com.itheima.Service.impl;

import cn.hutool.core.util.IdUtil;
import com.itheima.Service.ShoppingCartService;
import com.itheima.common.util.ThreadLocalUtil;
import com.itheima.domain.ShoppingCart;
import com.itheima.mapper.ShoppingCartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {


    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCart addShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.setUserId((Long) ThreadLocalUtil.get());
        //根据 uid+dish_id 查询之前是否添加过
        ShoppingCart cart = shoppingCartMapper.findByUidAndDid(shoppingCart);
        //不为空，说明添加过，把数量+1，更新回数据库
        if (cart != null) {
           cart.setNumber(cart.getNumber()+1);
           shoppingCartMapper.updateByPrimaryKey(cart);
           return cart;
        }
        //为空，说明没有添加过，把数据添加到数据库
        shoppingCart.setId(IdUtil.getSnowflakeNextId());
        shoppingCart.setNumber(1);
        shoppingCartMapper.insert(shoppingCart);
        return shoppingCart;
    }

    @Override
    public List<ShoppingCart> list() {
       return shoppingCartMapper.findByUid((Long) ThreadLocalUtil.get());
    }

    @Override
    public ShoppingCart sub(ShoppingCart shoppingCart) {
        shoppingCart.setUserId((Long) ThreadLocalUtil.get());
        ShoppingCart cart = shoppingCartMapper.findByUidAndDid(shoppingCart);
        if (cart != null) {
            cart.setNumber(cart.getNumber()-1);
            if (cart.getNumber() == 0) {
                shoppingCartMapper.deleteByPrimaryKey(cart.getId());
            }
            shoppingCartMapper.updateByPrimaryKey(cart);
            return cart;
        }
        return null;
    }

    @Override
    public void clean() {
        shoppingCartMapper.clean((Long) ThreadLocalUtil.get());
    }
}
