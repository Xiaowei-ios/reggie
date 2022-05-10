package com.itheima.controller;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.itheima.Service.UserService;
import com.itheima.common.R;
import com.itheima.common.exception.BusinessException;
import com.itheima.common.util.SmsTemplate;
import com.itheima.constant.UserConstant;
import com.itheima.domain.User;
import com.itheima.domain.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    SmsTemplate smsTemplate;

    @Autowired
    UserService userService;


    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user,  HttpSession session) {
        log.info("user:{}", user);
        String phone=user.getPhone();
        if (StrUtil.isBlank(phone)){
            throw new BusinessException(500,"手机号不能为空");
        }
        String code = RandomUtil.randomNumbers(6);
        log.info("验证码：{}",code);
        String result="200";
        if (result.equals("200")||result.equals("OK")){
            session.setAttribute(phone+":code",code);
            //smsTemplate.sendSms(phone,code);
            return R.success("发送成功");
        }
        return R.error("发送失败");
    }

    @PostMapping("login")
    public R<String> login(@RequestBody UserVo userVo, HttpSession session) {
        log.info("userVo:{}", userVo);
        String phone=userVo.getPhone();
        String code=userVo.getCode();
        if (StrUtil.isBlank(phone)){
            throw new BusinessException(500,"手机号不能为空");
        }
        if(session.getAttribute(phone+":code")==null||!session.getAttribute(phone+":code").equals(code)){
            throw new BusinessException(500,"验证码错误");
        }
        //验证码正确，则查询用户是否存在
        User user=userService.findUserByPhone(phone);
        session.setAttribute(UserConstant.SESSION,user.getId());
        return R.success("登录成功");
    }
    @PostMapping("loginout")
    public R<String> loginout(HttpSession session) {
        session.invalidate();
        return R.success("退出成功");
    }
}
