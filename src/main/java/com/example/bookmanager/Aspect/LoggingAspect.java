package com.example.bookmanager.Aspect;

import com.example.bookmanager.Utils.ThreadLocalUtil;
import com.example.bookmanager.Utils.UserClaims;
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
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        UserClaims claims = ThreadLocalUtil.get();

        if (claims == null) {
            logger.info("{}: Entering method: {} with arguments: {}", className, methodName, args);
        } else logger.info("{}: Entering method: {} with arguments: {} (operated by user: {})", className, methodName, args, claims.getUsername());
        try {
            Object result = joinPoint.proceed();
            if (result != null && !methodName.equals("login"))
                logger.info("{}: Exiting method: {} with result: {}", className, methodName, result);
            return result;
        } catch (Exception e) {
            logger.error("{}: Exception in method: {} with cause: {}", className, methodName, e.getMessage());
            throw e;
        }
    }
}
