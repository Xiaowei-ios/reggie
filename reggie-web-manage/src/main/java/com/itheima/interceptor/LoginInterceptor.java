package com.itheima.interceptor;


import  com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.constant.EmployeeConstant;
import com.itheima.domain.Employee;
import com.itheima.common.R;
import com.itheima.common.util.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    //在访问controller之前拦截，判断用户有没有登录
    //登录了才能放行
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //1. 从session中获取用户数据
        Employee emp = (Employee) request.getSession().getAttribute(EmployeeConstant.SESSION);

        log.info(" --> 当前请求：{}",request.getRequestURI());
        //如果有就是登录了
        if(emp != null){
            ThreadLocalUtil.set(emp);
            return true;
        }
        //否则就是没有登录：返回提示
        log.error("非法访问，没有登录 {}",request.getRequestURI());
        R<Object> error = R.error("NOTLOGIN");
        //把对象转json
        String result = new ObjectMapper().writeValueAsString(error);
        response.getWriter().write(result);

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        ThreadLocalUtil.remove();
    }
}
