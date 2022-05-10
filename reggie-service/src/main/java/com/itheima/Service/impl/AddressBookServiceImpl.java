package com.itheima.Service.impl;

import cn.hutool.core.util.IdUtil;
import com.itheima.Service.AddressBookService;
import com.itheima.common.util.ThreadLocalUtil;
import com.itheima.domain.AddressBook;
import com.itheima.mapper.AddressBookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    AddressBookMapper addressBookMapper;

    /**
     * 查询当前用户的收获地址
     * @return
     */
    @Override
    public List<AddressBook> list() {
        //1. 获取当前用户id
        Long uid = (Long) ThreadLocalUtil.get();
        //2. 根据id查询收货地址
        return addressBookMapper.list(uid);
    }

    /**
     * 新增收货地址
     * @param addressBook
     */
    @Override
    public void add(AddressBook addressBook) {
        //1. 补全数据
        //id
        addressBook.setId(IdUtil.getSnowflakeNextId());
        //uid
        addressBook.setUserId((Long) ThreadLocalUtil.get());
        //create_user update_user
        addressBook.setCreateUser((Long) ThreadLocalUtil.get());
        addressBook.setUpdateUser((Long) ThreadLocalUtil.get());
        //保存数据
        addressBookMapper.insert(addressBook);
    }

    @Transactional
    @Override
    public void setDefaultAddress(AddressBook addressBook) {
        //1. 当前用户的收获地址都设置为非默认
        addressBookMapper.allNotDefault((Long) ThreadLocalUtil.get());
        //2. 更新当前id为默认
        addressBookMapper.updateDefault(addressBook.getId());
    }

    /**
     * 查询默认收货地址
     * @return
     */
    @Override
    public AddressBook getDefaultAddress() {
        return addressBookMapper.getDefaultAddress((Long) ThreadLocalUtil.get());
    }

    /**
     * 根据ID查询收货地址
     * @param id
     * @return
     */
    @Override
    public AddressBook getById(Long id) {
        return addressBookMapper.getById(id);
    }

    /**
     * 修改收货地址
     * @param addressBook
     */
    @Override
    public void updateById(AddressBook addressBook) {
        //更新数据
        addressBook.setUpdateUser((Long) ThreadLocalUtil.get());
        addressBook.setUpdateTime(LocalDateTime.now());

        addressBookMapper.updateById(addressBook);
    }
}