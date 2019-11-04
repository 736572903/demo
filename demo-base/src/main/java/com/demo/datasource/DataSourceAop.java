package com.demo.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import com.demo.datasource.DataSourceType.DataBaseType;


@Aspect
@Component
public class DataSourceAop {

    //用路径

    //用注解
    @Pointcut("execution(* com.demo.service*.impl..*.*(..))")
    public void switchDataSources() {
    }

    @Around("switchDataSources()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        //类上的数据源注解
        Class<?> targetClass = pjp.getTarget().getClass();
        DataSource dataSource = targetClass.getAnnotation(DataSource.class);
        String authentic = "";
        if (dataSource != null) {
            authentic = dataSource.source();
        }
        if (authentic != null && authentic.equals("test02")) {
            DataSourceType.setDataBaseType(DataBaseType.TEST02);
        } else {
            DataSourceType.setDataBaseType(DataBaseType.TEST01);
        }

        Object o = pjp.proceed();
        DataSourceType.clearDataBaseType();
        return o;
    }


}
