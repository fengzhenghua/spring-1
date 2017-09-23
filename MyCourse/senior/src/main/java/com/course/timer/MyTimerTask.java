package com.course.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

/**
 * Created by fengzhenghua on
 * 2017-08-12 11:17.
 */
//TimerTask抽象类，实现run方法
public class MyTimerTask extends TimerTask {

    private int num = 0;

    public void run() {
        if(num<3) {
            String tName = Thread.currentThread().getName();
            System.out.println("线程" + tName + "任务开始执行,num=" + num + "=====================" + tName);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("执行时间为：" + sdf.format(calendar.getTime()));
            num++;
        }else {
            cancel();
            System.out.println("task Cancel");
        }
    }

}
