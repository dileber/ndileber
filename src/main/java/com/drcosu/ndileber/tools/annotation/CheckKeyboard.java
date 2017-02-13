package com.drcosu.ndileber.tools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 检查键盘是否打开
 * Created by shidawei on 2017/2/13.
 */
@Target(ElementType.TYPE)//表示用在类上
@Retention(RetentionPolicy.RUNTIME)//表示在生命周期是运行时
@Inherited //允许子类继承父类的注解。
public @interface CheckKeyboard {
    boolean value() default true;
}
