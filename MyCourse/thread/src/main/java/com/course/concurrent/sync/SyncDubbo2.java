package com.course.concurrent.sync;

/**
 * Created by fzh on 2017/8/4.
 */
public class SyncDubbo2 {

    static class Main{
        public int i = 10;
        public synchronized void operatinSup(){

            try{
                i--;
                System.out.println("Main print i="+i);
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    static class Sub extends Main{
        public synchronized void operatinSub(){

            try{
                while (i>0){
                    i--;
                    System.out.println("Sub print i="+i);
                    Thread.sleep(100);
                    this.operatinSup();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {

        Sub sub = new Sub();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                 sub.operatinSub();
            }
        });

        t1.start();
    }
}
