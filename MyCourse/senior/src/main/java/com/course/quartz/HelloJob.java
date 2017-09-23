package com.course.quartz;


import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by fengzhenghua on
 * 2017-08-12 22:09.
 */
/*
 * 实现Job接口，然后传入JobDetail
 */
public class HelloJob implements Job {

    private int count = 0;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //与Timer不一样，并没有实现count累加。而是每次实例化一个JobDetail对象
        System.out.println("任务开始执行,count=" + count);
        count++;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = dateFormat.format(Calendar.getInstance().getTime());
        System.out.println("dateStr = " + dateStr);
        //获取JobDetail 中 JobKey
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        System.out.println("Job name and group are:" + jobKey.getName() + " " + jobKey.getGroup());
        //获取triggerKey
        TriggerKey tk = jobExecutionContext.getTrigger().getKey();
        System.out.println("Trigger name and group are:" + tk.getName() + " " + tk.getGroup());
        //获取JobDataMap
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        //获取TriggerDataMap
        JobDataMap tDataMap = jobExecutionContext.getTrigger().getJobDataMap();

        String jobMessage = jobDataMap.getString("JobDetailMessage");
        float jobFloatValue = jobDataMap.getFloatValue("JobFloatValue");

        String triggerMessage = tDataMap.getString("TriggerMessage");
        double jobDoubleValue = tDataMap.getDoubleValue("TriggerDoubleVlue");

        System.out.println("JobDataMap=========>" + "jobMessage:" + jobMessage +  " jobFloatValue:" + jobFloatValue);
        System.out.println("TriDataMap=========>" + "triggerMessage:" + triggerMessage + " jobDoubleValue:" + jobDoubleValue);

    }
}
