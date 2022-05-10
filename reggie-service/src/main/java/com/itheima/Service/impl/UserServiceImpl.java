package com.itheima.Service.impl;

import cn.hutool.core.util.IdUtil;
import com.itheima.Service.UserService;
import com.itheima.common.exception.BusinessException;
import com.itheima.domain.User;
import com.itheima.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User findUserByPhone(String phone) {
        User user = userMapper.findUserByPhone(phone);
        if(user == null){
            //注册用户
            user = new User();
            user.setId(IdUtil.getSnowflakeNextId());
            user.setPhone(phone);
            userMapper.insert(user);
        }else {
            //说明已注册，检查是否禁用
            if(user.getStatus() == 0){
                throw new BusinessException(500,"账户已禁用，请联系管理员");
            }
        }
        return user;
    }
}
