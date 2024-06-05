package kr.kro.calcking.calckingwebbe.aspects;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LogAspect {
    @Pointcut("within(kr.kro.calcking.calckingwebbe.controllers..*)") 
    public void controller() {}

    @Before("controller()")
    public void beforeRequest(JoinPoint joinPoint) {
        log.info("###Start request {}", joinPoint.getSignature().toShortString());
        Arrays.stream(joinPoint.getArgs())
                .map(Object::toString)
                .map(str -> "\t" + str)
                .forEach(log::info);
    }

    @AfterReturning(pointcut = "controller()", returning = "returnValue")
    public void afterReturningLogging(JoinPoint joinPoint, Object returnValue) {
        log.info("###End request {}", joinPoint.getSignature().toShortString());

        if (returnValue == null) return;

        log.info("\t{}", returnValue.toString());
    }

    @AfterThrowing(pointcut = "controller()", throwing = "e")
    public void afterThrowingLogging(JoinPoint joinPoint, Exception e) {
        log.error("###Occured error in request {}", joinPoint.getSignature().toShortString());
        log.error("\t{}", e.getMessage());
    }
}
