package com.course.concurrent.sync;

/**
 * Created by fengzhenghua on
 * 2017-08-11 11:51.
 */
public class MyRunnable  {

    public static void main(String[] args) {

        TestMyRunnable my = new TestMyRunnable();
        new Thread(my).start();
        new Thread(my).start();
        new Thread(my).start();
    }
}
class TestMyRunnable implements Runnable{

    private int ticket = 5;

    @Override
    public void run() {

        while (ticket>0){
            ticket--;
            System.out.println(ticket);

        }
    }
}
