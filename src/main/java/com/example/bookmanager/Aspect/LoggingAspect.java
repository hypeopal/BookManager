package com.example.bookmanager.Aspect;

import com.example.bookmanager.Utils.ThreadLocalUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    @Around("@annotation(com.example.bookmanager.Annotation.LogRecord)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String username = ThreadLocalUtil.get().getUsername();

        logger.info("Entering method: {} with arguments: {} (operated by user: {})", methodName, args, username);
        try {
            Object result = joinPoint.proceed();
            logger.info("Exiting method: {} with result: {}", methodName, result);
            return result;
        } catch (Exception e) {
            logger.error("Exception in method: {} with cause: {}", methodName, e.getMessage());
            throw e;
        }
    }
}
