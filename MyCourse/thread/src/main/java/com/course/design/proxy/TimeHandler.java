package com.course.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Random;

/**
 * Created by fzh on 2017/7/28.
 */
public class TimeHandler implements InvocationHandler{

    private Object target;//代理目标对象

    public TimeHandler(Object target) {
        this.target = target;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("汽车开始行驶！");
        long start = System.currentTimeMillis();
        method.invoke(target);//通过反射调用move方法
        Thread.sleep(new Random().nextInt(1000));
        long end = System.currentTimeMillis();
        System.out.println("行驶结束，行驶时间为："+(end-start));
        return null;
    }
}
