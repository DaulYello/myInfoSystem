package com.personal.info.myInfoSystem.core.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAop {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAop.class);

    @Pointcut(value = "@annotation(com.personal.info.myInfoSystem.)")
    public void cutService(){

    }
}
