package com.demo.datasource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wanghj
 * @createTime 2019、6、27
 * @description 配置多数据源
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {

    /**
     * 该方法所对应的数据库的数据源
     */
    String source() default "";


}
