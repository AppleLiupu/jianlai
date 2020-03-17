package com.lp.jianlai.test.annotationTest;

import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: liupu
 * @Date: 2019/7/23
 */
@Component
public class AnnoTest {

    @LpAnno("aaaa")
    public void test1() throws Exception {
        System.out.println("ttttttttttt");
        if(Math.random() > 0.5){
            throw new Exception("出错了");
        }else{
            System.out.println("ssss");
        }
    }

    @LpAnno
    public String test1(int id){
        System.out.println(id+"ttttttttttt");
        return "success";
    }
}
