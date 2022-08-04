package com.example.demo.logging.service;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Log4j2
@Component
public class ProductServiceAspect {
    @Pointcut(value = "execution(public * com.example.demo.service.ProductService" +
            ".*(..))")
    public void callAtProductService() {
    }

    @AfterThrowing(pointcut = "callAtProductService()", throwing = "ex")
    public void afterThrowingValidateProductAttributes(Exception ex) {
        log.error(ex.getMessage());
    }


}
