package com.lp.jianlai.test.feignTest.remote;

import com.lp.jianlai.test.feignTest.config.RemoteTestApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description:
 * @Author: liupu
 * @Date: 2019/7/12
 */
@FeignClient(name = "test", url = "http://localhost:8988/api/test",
        fallback = RemoteTestApiFallback.class,
        decode404 = true)
public interface RemoteTestApi {
        @RequestMapping(value = "/test1", method = RequestMethod.GET,
                consumes = "application/x-www-form-urlencoded;charset=UTF-8")
        String test1();
}
