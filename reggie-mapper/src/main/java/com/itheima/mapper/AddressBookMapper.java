package com.itheima.mapper;

import com.itheima.domain.AddressBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AddressBookMapper {
    //查询当前登录用户收货地址列表
    @Select("select * from address_book where user_id = #{uid} and is_deleted = 0 order by update_time DESC")
    List<AddressBook> list(Long uid);

    //新增收货地址
    void insert(AddressBook addressBook);

    //设置为默认收货地址
    @Update("update address_book set is_default = 1 where id = #{id}")
    void updateDefault(Long id);

    //设置当前用户地址都是非默认
    @Update("update address_book set is_default = 0 where user_id = #{userId}")
    void allNotDefault(Long userId);

    @Select("select * from address_book where user_id = #{uid} and is_default = 1 and is_deleted = 0")
    AddressBook getDefaultAddress(Long uid);

    @Select("select * from address_book where id = #{addressBookId} and is_deleted = 0")
    AddressBook getById(Long addressBookId);

    void updateById(AddressBook addressBook);
}