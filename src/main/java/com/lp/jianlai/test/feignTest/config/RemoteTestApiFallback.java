package com.lp.jianlai.test.feignTest.config;

import com.lp.jianlai.test.feignTest.remote.RemoteTestApi;
import org.springframework.stereotype.Component;

/**
 * @Description: 调用feign失败时走的方法
 * @Author: liupu
 * @Date: 2019/7/12
 */
@Component
public class RemoteTestApiFallback implements RemoteTestApi {


    @Override
    public String test1() {
        System.out.println("---------------------");
        return "test1 fail";
    }
}
