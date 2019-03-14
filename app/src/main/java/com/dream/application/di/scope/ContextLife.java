package com.dream.application.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created on 2017/8/31.
 * author chen_ping
 * Email yy_cping@163.com
 * Edit androidStudio
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
/**
 * @interface @ 创建标识
 */
public @interface ContextLife {
    String value() default "Application";
}
