package com.lp.jianlai.test.feignTest;

import com.lp.jianlai.test.feignTest.remote.RemoteTestApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: liupu
 * @Date: 2019/7/12
 */
@RequestMapping("/api/test")
@RestController
public class TestController {

    @Autowired
    private RemoteTestApi remoteTestApi;

    @GetMapping("/test1")
    @ResponseBody
    public String test1(HttpServletRequest request) {
        System.out.println(request.getHeader("Authorization"));
        System.out.println("-------------------");
        return "test1 success";
    }
}
