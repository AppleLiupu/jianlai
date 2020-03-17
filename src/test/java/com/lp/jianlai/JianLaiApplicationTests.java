package com.lp.jianlai;

import com.lp.jianlai.test.annotationTest.AnnoTest;
import com.lp.jianlai.test.feignTest.remote.RemoteTestApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.lp.jianlai.mapper")
@EnableFeignClients
public class JianLaiApplicationTests {

    @Autowired
    private RemoteTestApi remoteTestApi;

    @Autowired
    private AnnoTest annoTest;

    @Test
    public void contextLoads() {
        String str =remoteTestApi.test1();
        System.out.println(str);
    }

    @Test
    public void test1() throws Exception {
        annoTest.test1();
    }

}
