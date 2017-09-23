package com.course.concurrent.sync;

/**
 * Created by fzh on 2017/8/4.
 */
public class VolatileThread extends Thread {

    private  volatile boolean isRunning = true;

    @Override
    public void run() {
        System.out.println("开始");

        while (isRunning){
            System.out.println("进入run");//当此处没有代码块时，会造成死循环，而加了vlatile则不会
        }

        System.out.println("结束");
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public static void main(String[] args) throws InterruptedException {

        VolatileThread volatileThread = new VolatileThread();//创建一个线程分支，开辟一块，线程执行引擎执行线程内存空间，而不是从主内存中拿数据。

        volatileThread.start();

        Thread.sleep(3000);
        volatileThread.setRunning(false);
        Thread.sleep(100);
        System.out.println(volatileThread.isRunning);

    }
}
