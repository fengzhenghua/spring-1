package com.course.design.proxy;

import java.util.Random;

/**
 * Created by fzh on 2017/7/28.
 */
public class ProxyCar extends Car{

    @Override
    public void move() throws InterruptedException {

        System.out.println("汽车开始行驶！");
        long start = System.currentTimeMillis();
        super.move();
        Thread.sleep(new Random().nextInt(1000));
        long end = System.currentTimeMillis();
        System.out.print("行驶结束，行驶时间为："+(end-start));
    }
}
