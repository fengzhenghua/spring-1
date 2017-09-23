package com.course.design.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by fzh on 2017/7/28.
 */
public class CglibProxy implements MethodInterceptor{

    private Enhancer enhancer = new Enhancer();//Enhancer:加强

    public Object getProxy(Class clazz){
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);//实现回调
        return enhancer.create();
    }

    /*
     *拦截所有目标类方法的调用，
     * o:目标类的实例
     * method:目标方法的反射对象
     * objects:方法参数
     *methodProxy:代理类的实例
     */

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("日志开始");
        methodProxy.invokeSuper(o,objects);
        System.out.println("日志结束");
        return null;
    }
}
