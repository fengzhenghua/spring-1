package com.course.concurrent.sync;


/**
 * Created by fzh on 2017/8/4.
 */
public class StringLock {

    public void method(){

        synchronized ("锁"){

            try{

                System.out.println(Thread.currentThread().getName() + "开始");
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + "结束");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)  {

        StringLock stringLock = new StringLock();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                stringLock.method();
            }
        },"t1");


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                stringLock.method();
            }
        },"t2");

        t1.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
    }
}
