package com.drcosu.ndileber.tools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 页面实现样式 noactionbar就可以了,在后期的版本迭代中,会去掉这个类
 * Created by shidawei on 16/1/17.
 */
@Target(ElementType.TYPE)//表示用在类上
@Retention(RetentionPolicy.RUNTIME)//表示在生命周期是运行时
@Inherited //允许子类继承父类的注解。
@Deprecated
public @interface CloseTitle {

    boolean value() default true;

}
