package com.course.design.proxy;

/**
 * Created by fzh on 2017/7/28.
 */
public class ProxyCarLog implements Movable{

    Movable m ;

    public ProxyCarLog(Movable m) {
        this.m = m;
    }


    @Override
    public void move() throws InterruptedException {

        System.out.println("日志记录开始");

        m.move();

        System.out.println("日志记录结束");
    }
}
