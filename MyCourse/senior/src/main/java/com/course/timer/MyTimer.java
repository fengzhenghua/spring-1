package com.course.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fengzhenghua on
 * 2017-08-12 11:23.
 */

/**
 * TimerTask中cancel与timer中比较：TimerTask只能终止当前任务，而Timer可以终止timer对象的所有正在执行的定时任务
 */
public class MyTimer {

    public static void main(String[] args) {

        Timer timer1 = new Timer("t1");
        Timer timer2 = new Timer("t2");
        //定时任务1
        MyTimerTask myTimerTask = new MyTimerTask();
        //定时任务2

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {

                String tName = Thread.currentThread().getName();
                System.out.println("线程" + tName + "任务开始执行=====================" + tName);
                Calendar calendar = Calendar.getInstance();
                System.out.println("执行时间为：" + sdf.format(calendar.getTime()));
            }


        };

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,11);
        calendar.set(Calendar.MINUTE,49);
        calendar.set(Calendar.SECOND,00);
        Date date = calendar.getTime();
//        Calendar calendar1 = Calendar.getInstance();
//        //设置3秒后执行，只执行一次
//        calendar1.add(Calendar.SECOND,2);
//        timer.schedule(myTimerTask ,calendar1.getTime());
        //设置1秒后开始执行，间隔时间为1秒
        //timer.schedule(myTimerTask,1L,1000L);
        //设置定时执行任务，间隔1秒
        timer1.schedule(myTimerTask,date,1000L);
        System.out.println("the last time schedule : " + sdf.format(myTimerTask.scheduledExecutionTime()) );
        //timer2.schedule(timerTask,date,1500L);

    }
}
