package com.itheima.Service;

import com.itheima.domain.AddressBook;

import java.util.List;

public interface AddressBookService {
    //地址列表查询
    List<AddressBook> list();
    //添加收货地址
    void add(AddressBook addressBook);
    //设置默认收货地址
    void setDefaultAddress(AddressBook addressBook);
    //获取默认收货地址
    AddressBook getDefaultAddress();
    //根据ID查询收货地址
    AddressBook getById(Long id);
    //修改收货地址
    void updateById(AddressBook addressBook);
}