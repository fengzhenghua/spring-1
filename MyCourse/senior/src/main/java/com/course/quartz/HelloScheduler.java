package com.course.quartz;

import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by fengzhenghua on
 * 2017-08-12 22:12.
 */

/**
 *
 * 定时任务调度
 */
public class HelloScheduler  {
    
    public static void main(String[] args) throws SchedulerException {
        //创建一个JobDetail实例，与Job class进行绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob","group1")
                .usingJobData("JobDetailMessage","myJobData")
                .usingJobData("JobFloatValue",3.14F).build();

        System.out.println("jobDetail name = " + jobDetail.getKey().getName());
        System.out.println("jobDetail group = " + jobDetail.getKey().getGroup());
        System.out.println("jobDetail className = " + jobDetail.getClass().getName());
        System.out.println("jobDetail description = " + jobDetail.getDescription());
        //创建一个Trigger,定义该job立即执行，并且间隔每两秒一次，知道程序终止
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger","group1")
                .startNow()
                .usingJobData("TriggerMessage","myTrigerData")
                .usingJobData("TriggerDoubleVlue","10.0")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();
        //创建Schedule实例
        StdSchedulerFactory ssf = new StdSchedulerFactory();
        Scheduler scheduler = ssf.getScheduler();
        scheduler.start();
        //把JobDtail 与 Trgger绑定在一起
        scheduler.scheduleJob(jobDetail,trigger);
    }
}
