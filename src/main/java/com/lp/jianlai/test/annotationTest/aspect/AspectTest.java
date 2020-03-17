package com.lp.jianlai.test.annotationTest.aspect;

import com.alibaba.fastjson.JSON;
import com.lp.jianlai.test.annotationTest.LpAnno;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: liupu
 * @Date: 2019/7/23
 */
@Aspect
@Component
public class AspectTest {
    //第一个*表示返回类型 ，..表示包与子包 *表示所有类 *(..)表示所有方法
    //@Pointcut("execution(public * com.lp.jianlai..*.*(..)) && args(id)")
    @Pointcut("@within(com.lp.jianlai.test.annotationTest.LpAnno) || @annotation(com.lp.jianlai.test.annotationTest.LpAnno)")
    public void aspectT(){

    }

    //    配置连接点 方法开始执行时通知
    @Before("aspectT()")
    public void beforeLog() {
        System.out.println("开始执行前置通知  日志记录:");
    }

    //    方法执行完后通知
    @After("aspectT()")
    public void afterLog() {
        System.out.println("开始执行后置通知 日志记录:");
    }

    //    执行成功后通知
    @AfterReturning("aspectT()")
    public void afterReturningLog() {
        System.out.println("方法成功执行后通知 日志记录:");
    }

    //    抛出异常后通知
    @AfterThrowing("aspectT()")
    public void afterThrowingLog() {
        System.out.println("方法抛出异常后执行通知 日志记录");
    }

    //    环绕通知
    @Around("aspectT()")
    public Object aroundLog(ProceedingJoinPoint joinpoint) throws Throwable {
        System.out.println("开始！！！！！！！");

        MethodSignature methodSignature = (MethodSignature) joinpoint.getSignature();
        LpAnno lpAnno = methodSignature.getMethod().getAnnotation(LpAnno.class);
        if (lpAnno == null) {
            lpAnno = joinpoint.getTarget().getClass().getAnnotation(LpAnno.class);
        }
        if (lpAnno == null) {
            System.out.println("未获取到注解");
        }
        System.out.println("获取到注解:"+ JSON.toJSON(lpAnno.value()));

        long start = System.currentTimeMillis();

        Object result =  joinpoint.proceed();

        long end = System.currentTimeMillis();
        System.out.println("总共执行时长" + (end - start) + " 毫秒");
        System.out.println("结束！！！！！！");
        return result;
    }
}
