package com.lp.jianlai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@MapperScan("com.lp.jianlai.mapper")
@EnableAspectJAutoProxy(exposeProxy=true)
public class JianLaiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JianLaiApplication.class, args);
    }

}
