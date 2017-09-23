package com.course.design.proxy;

/**
 * Created by fzh on 2017/7/29.
 */
public class CglibTest {

    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        Train t = (Train) cglibProxy.getProxy(Train.class);
        t.move();
    }
}
