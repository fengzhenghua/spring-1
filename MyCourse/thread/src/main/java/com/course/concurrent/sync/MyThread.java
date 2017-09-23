package com.course.concurrent.sync;

/**
 * Created by fengzhenghua on
 * 2017-08-11 11:46.
 */
public class MyThread {

    public static void main(String[] args) {

        new TestMyThread().start();
        new TestMyThread().start();
        new TestMyThread().start();
    }

}

    class TestMyThread extends Thread{

        private int ticket = 5;

        @Override
        public void run() {

            while (ticket>0){
                System.out.println("ticket:" + ticket);
                ticket--;
            }
    }
}
