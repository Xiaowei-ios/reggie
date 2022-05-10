package com.itheima.controller;

import com.itheima.Service.AddressBookService;
import com.itheima.common.R;
import com.itheima.domain.AddressBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    AddressBookService addressBookService;

    /**
     * 查询登录人收货地址列表
     * @return
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list(){
        return R.success(addressBookService.list());
    }

    /**
     * 新增收货地址
     * @param addressBook
     * @return
     */
    @PostMapping
    public R add(@RequestBody AddressBook addressBook){
        addressBookService.add(addressBook);
        return R.success(null);
    }

    /**
     * 根据ID查询收货地址
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<AddressBook> edit(@PathVariable Long id){
        return R.success(addressBookService.getById(id));
    }

    /**
     * 修改收货地址
     * @param addressBook
     * @return
     */
    @PutMapping
    public R update(@RequestBody AddressBook addressBook){
        addressBookService.updateById(addressBook);
        return R.success("地址更新成功");
    }

    /**
     * 设置默认收货地址
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    public R setDefaultAddress(@RequestBody AddressBook addressBook){
        addressBookService.setDefaultAddress(addressBook);
        return R.success(null);
    }

    /**
     * 获取默认收货地址
     * @return
     */
    @GetMapping("/default")
    public R getDefaultAddress(){
        AddressBook defaultAddress = addressBookService.getDefaultAddress();
        if(addressBookService != null){
            return R.success(defaultAddress);
        }
        return R.error("当前没有默认收货地址，请添加");
    }
}