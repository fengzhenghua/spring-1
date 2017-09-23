package com.course.design.proxy;

/**
 * Created by fzh on 2017/7/28.
 */
public class Car implements Movable{

    @Override
    public void move() throws InterruptedException {

        System.out.println("汽车正在行驶！");
    }
}
