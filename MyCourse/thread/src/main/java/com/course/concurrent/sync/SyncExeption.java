package com.course.concurrent.sync;

/**
 * Created by fzh on 2017/8/4.
 */
public class SyncExeption {

    private int i = 0;

    public synchronized void operation(){

        while (true){
            try {
                i++;
                //100秒输出一次i
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + ",i=" + i);

                if(i == 10){
                    //抛出异常
                    Integer.parseInt("a");
                    //throw new InterruptedException();
                }
            }catch (InterruptedException e){//中断异常，如果异常为Exeption,线程会继续走下去

                e.printStackTrace();
                System.out.println("Log i=" + i);
                continue;
            }
        }
    }

    public static void main(String[] args) {

        SyncExeption syncExeption = new SyncExeption();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                syncExeption.operation();
            };
        },"t1");

        t1.start();
    }
}
