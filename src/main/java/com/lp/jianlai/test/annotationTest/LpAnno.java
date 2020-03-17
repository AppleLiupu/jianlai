package com.lp.jianlai.test.annotationTest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:
 * @Author: liupu
 * @Date: 2019/7/15
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface LpAnno {
    String[] value() default "default";
    Class<?> cclass() default void.class;
}
