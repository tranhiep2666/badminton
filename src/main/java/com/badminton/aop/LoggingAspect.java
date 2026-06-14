package com.badminton.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Around(
            "execution(* com.badminton.service.impl.*.*(..))"
    )
    public Object logExecutionTime(

            ProceedingJoinPoint joinPoint

    ) throws Throwable {

        long start =
                System.currentTimeMillis();

        Object result =
                joinPoint.proceed();

        long end =
                System.currentTimeMillis();

        log.info(

                "[EXECUTION_TIME] {} took {} ms",

                joinPoint
                        .getSignature()
                        .toShortString(),

                (end - start)
        );

        return result;
    }

    @AfterReturning(
            pointcut =
                    "execution(* com.badminton.service.impl.BookingServiceImpl.createBooking(..))",

            returning = "result"
    )
    public void bookingSuccess(

            JoinPoint joinPoint,

            Object result
    ) {

        log.info(

                "[AUDIT-SUCCESS] Booking created successfully"
        );
    }

    @AfterThrowing(

            pointcut =
                    "execution(* com.badminton.service.impl.BookingServiceImpl.createBooking(..))",

            throwing = "exception"
    )
    public void bookingFailed(

            JoinPoint joinPoint,

            Exception exception
    ) {

        log.error(

                "[AUDIT-FAILED] Booking failed: {}",

                exception.getMessage()
        );
    }

    @AfterThrowing(

            pointcut =
                    "execution(* com.badminton.service.impl.*.*(..))",

            throwing = "exception"
    )
    public void serviceException(

            JoinPoint joinPoint,

            Exception exception
    ) {

        log.error(

                "[SERVICE_ERROR] {} - {}",

                joinPoint
                        .getSignature()
                        .toShortString(),

                exception.getMessage()
        );
    }
}