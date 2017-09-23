package com.course.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by fzh on 2017/7/28.
 */
public class ProxyTest {

    public static void main(String[] args) throws InterruptedException {
        //Movable proxyCar = new ProxyCar();//继承方式代理
        //proxyCar.move();
       // Movable proxyCar1 = new JuHeCar(new Car());//聚合方式代理
        //proxyCar1.move();
        //ProxyCarTime pct = new ProxyCarTime(new Car());
        //ProxyCarLog pcl = new ProxyCarLog(pct);
        //pcl.move();
//        ProxyCarLog pcl = new ProxyCarLog(new Car());
//        ProxyCarTime pct = new ProxyCarTime(pcl);
//        pct.move();
        /*
        JDK动态代理
         */
        InvocationHandler h = new TimeHandler(new Car());
        Class clazz = new Car().getClass();

        Movable m = (Movable)Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),h);
        m.move();
    }
}
