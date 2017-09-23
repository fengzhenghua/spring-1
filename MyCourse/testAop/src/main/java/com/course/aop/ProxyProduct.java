package com.course.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by fengzhenghua on
 * 2017-08-10 10:19.
 */
//@Aspect
//@Component
public class ProxyProduct {

    private Date date1;
    private Date date2;

  /*  @Before("execution(* com.course.aop.Product.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        date1 = new Date();
        System.out.println("准备生产...");
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("The method:"+methodName+" begin with"+args);
    }

    @After("execution(* com.course.aop.Product.*(..))")
    public void afterMethod(JoinPoint joinPoint){
        date2 = new Date();
        System.out.println("生产完成...");
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("The method:"+methodName+" end with"+args);
    }

    @AfterReturning(value="execution(* com.course.aop.Product.*(..))",returning = "resurt")
    public void afterReturning(JoinPoint joinPoint,Object resurt){

        System.out.println("生产结果..."+"生产时间"+ (date2.getTime() - date1.getTime()));
        String methodName = joinPoint.getSignature().getName();
        System.out.println("The method :"+ methodName + "return " + resurt);
    }*/

    //@Around(value = "execution(* com.course.aop.Product.*(..))")
    public void around(ProceedingJoinPoint pjp){

        Object resurt = null;
        Class<?> clazz = pjp.getTarget().getClass();
        String methodName = pjp.getSignature().getName();
        List<Object> args  = Arrays.asList(pjp.getArgs());
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Class<?>  returnClazz = methodSignature.getReturnType();
        System.out.println("生产前--->" + "ClassName:"+clazz.getName()+" MethodName:"+methodName + "args:" +args + "返回类型:"+ returnClazz.getName());
        try {
            resurt = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("生产后--->" + "resurt:" + resurt + " ClassName:"+clazz.getName()+" MethodName:"+methodName + "args:" +args + "返回类型:"+ returnClazz.getName());
    }
}
