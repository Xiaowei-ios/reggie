package com.itheima;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
/**
 * 开启事务管理
 */
@EnableTransactionManagement
@Slf4j
public class ReggieManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieManageApplication.class,args);
        log.info(" ---> 项目启动成功");
    }

}
