package com.course.design.proxy;

import java.util.Random;

/**
 * Created by fzh on 2017/7/28.
 */
public class ProxyCarTime implements Movable{

    Movable m ;

    public ProxyCarTime(Movable m) {
        this.m = m;
    }

    public void move() throws InterruptedException {

        System.out.println("汽车开始行驶！");
        long start = System.currentTimeMillis();
        m.move();
        Thread.sleep(new Random().nextInt(1000));
        long end = System.currentTimeMillis();
        System.out.println("行驶结束，行驶时间为："+(end-start));

    }
}
