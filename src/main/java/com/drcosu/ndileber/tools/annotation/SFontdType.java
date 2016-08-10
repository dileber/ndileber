package com.drcosu.ndileber.tools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shidawei on 16/2/3.
 */
@Target(ElementType.TYPE)//表示用在类上
@Retention(RetentionPolicy.RUNTIME)//表示在生命周期是运行时
@Inherited //允许子类继承父类的注解。
public @interface SFontdType {

    String value() default "icomoon.ttf";

}
