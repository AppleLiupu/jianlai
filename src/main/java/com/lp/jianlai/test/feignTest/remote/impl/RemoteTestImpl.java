package com.lp.jianlai.test.feignTest.remote.impl;

import com.lp.jianlai.test.feignTest.remote.RemoteTestApi;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: liupu
 * @Date: 2019/7/12
 */
@Component
public class RemoteTestImpl implements RemoteTestApi {
    @Override
    public String test1() {
        return null;
    }
}
