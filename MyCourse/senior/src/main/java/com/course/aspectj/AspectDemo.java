package com.course.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;

/**
 * Created by fengzhenghua on
 * 2017-08-09 22:12.
 */
public class AspectDemo {

    @Before("execution(* com.course.aspectj.*.*(..))")
    public void onActivityMethodBefore(JoinPoint joinPoint) throws Throwable {
        System.out.println("before");
    }
}
