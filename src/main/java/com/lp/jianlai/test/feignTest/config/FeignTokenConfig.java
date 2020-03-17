package com.lp.jianlai.test.feignTest.config;

import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @Description:
 * @Author: liupu
 * @Date: 2019/7/12
 */
@Configuration
public class FeignTokenConfig {
    @Bean
    public RequestInterceptor getRequestInterceptor() {
        return requestTemplate -> {
            /** 设置请求头信息 **/
            String uuid = UUID.randomUUID().toString();
            System.out.println(uuid);
            requestTemplate.header("Authorization", "Bearer " + uuid);
        };
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Retryer feignRetryer() {
        //return new Retryer.Default(100L, TimeUnit.SECONDS.toMillis(1L), 10);
        return Retryer.NEVER_RETRY;
    }

    @Bean
    public Request.Options feignOptions(){
        return new Request.Options(6 * 1000, 12 * 1000);
    }
}
