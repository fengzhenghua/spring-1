package com.tydic.unicom.crm.web.uss.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tydic.unicom.service.cache.service.redis.interfaces.RedisInitServ;
import com.tydic.unicom.uoc.business.common.interfaces.ActivemqServDu;
import com.tydic.unicom.uoc.business.common.interfaces.TimingTaskServiceServDu;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.sale.interfaces.InfoSaleOrderBusinessServDu;
import com.tydic.unicom.webUtil.UocMessage;

public class LoadRedisListenter  implements ServletContextListener{

	private static Logger logger = Logger.getLogger(LoadRedisListenter.class);
	
	private RedisInitServ redisInitServ;
	
	private InfoSaleOrderBusinessServDu infoSaleOrderBusinessServDu;
	
	private ActivemqServDu activemqServDu;
	
	private TimingTaskServiceServDu timingTaskServiceServDu;
	
	private  static String redisInit ="";
	private  static int hour;
	private  static int minute;
	private  static int second;
	private  static int backupHour;
	private  static int backupMinute;
	private  static int backupSecond;
	private  static long backupInterval;
	private  static String backupTotalNum;
	private  static String backupRemainder;
	private  static int activemqHour;
	private  static int activemqMinute;
	private  static int activemqSecond;
	private  static String activemqTotalNum;
	private  static String activemqRemainder;
	private  static long activemqInterval;
			
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext());
		redisInitServ = (RedisInitServ) wac.getBean("RedisInitServ");
		infoSaleOrderBusinessServDu = (InfoSaleOrderBusinessServDu) wac.getBean("InfoSaleOrderBusinessServDu");
		activemqServDu = (ActivemqServDu) wac.getBean("ActivemqServDu");
		timingTaskServiceServDu = (TimingTaskServiceServDu) wac.getBean("TimingTaskServiceServDu");
		try {
//			//系统启动时需要执行的任务
			intiTask();
			//redis定时刷新
//			TimingRedisTask();
			//es定时服务
			TimingEsTask();
			//定时备份
			OrderBackUpTask();
			//发送消息队列
			SendActivemqTask();
			//定时赠款
			grantFeeTimer();
			//不激活25天自动撤单
			TimingCancleOrder();
			//机卡比对
			TimingCardCompare();
		} catch (Exception e) {
			logger.error("=================redisListener异常=================");
			e.printStackTrace();
		}
	}

	/**系统启动时需要执行的任务*/
	private void intiTask() throws Exception{
		String url = LoadRedisListenter.class.getResource("").toString();
		//String Fullpath = url.toString();
		
		int lastIndex = url.lastIndexOf("/WEB-INF");
		url = url.replaceAll("\\\\", "/");
		String subUrl = url.substring(0, lastIndex);
		String suburl1 = subUrl.substring(0, subUrl.lastIndexOf("/"));
		String suburl2 = suburl1.substring(0, suburl1.lastIndexOf("/"));
		String suburl3 = suburl2.substring(suburl2.indexOf("/"), suburl2.length());
		InputStream in = null;
		try {
			Properties props = new Properties();
			String filePath = suburl3+"/conf/UocProperties.properties";
			File file = new File(filePath);
			in = new FileInputStream(file);
			props.load(in);
			String host = props.getProperty("host");
			in.close();
			logger.info("================="+host);
		  
		 backupHour = Integer.parseInt(props.getProperty("backup.hour"));
		 backupMinute = Integer.parseInt(props.getProperty("backup.minute"));
		 backupSecond = Integer.parseInt(props.getProperty("backup.second"));
		 backupInterval =1*1000*Integer.parseInt(props.getProperty("backup.interval"));
		 backupTotalNum =props.getProperty("backup.total_num");
		 backupRemainder =props.getProperty("backup.remainder");
		 
		 activemqHour = Integer.parseInt(props.getProperty("activemq.hour"));
		 activemqMinute = Integer.parseInt(props.getProperty("activemq.minute"));
		 activemqSecond = Integer.parseInt(props.getProperty("activemq.second"));
		 activemqInterval =1*1000*Integer.parseInt(props.getProperty("activemq.interval"));
		 activemqTotalNum =props.getProperty("activemq.total_num");
		 activemqRemainder =props.getProperty("activemq.remainder");

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("============="+suburl2);
		logger.info("==========="+url);	
	}
	
	/**
	 * 机卡比对（定时任务）
	 * */
	private void TimingCardCompare() throws Exception{
		logger.info("=============机卡比对开始（定时任务）===========");
		TimerTask task = new TimerTask(){
			@Override
			public void run() {
				try {
					UocMessage result = timingTaskServiceServDu.syncCardCompare();
					logger.info("=================机卡比对开始（定时任务）完成， ErrorCode："+result.getRespCode()+" Content:"+result.getContent());
				} catch (Exception e) {
					logger.info("=============机卡比对开始（定时任务）异常=======");
					e.printStackTrace();
				}
			}			
		};
		//设置首次执行时间
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 30);
		calendar.set(Calendar.SECOND, 00);
		Date firstTime = calendar.getTime();
		Timer timer = new Timer();
		//设置执行间隔时间
		long period = 24*60*60*1000;
		//执行定时任务
		timer.schedule(task, firstTime, period);
	}
	
	/**
	 * 不激活25天自动撤单(定时任务)
	 * */
	private void TimingCancleOrder() throws Exception{
		logger.info("=============不激活25天自动撤单(定时任务)===========");
		TimerTask task = new TimerTask(){
			@Override
			public void run() {
				try {
					UocMessage result = timingTaskServiceServDu.cancleOrder();
					logger.info("=================不激活25天自动撤单(定时任务) ErrorCode："+result.getRespCode()+" ErrorContent:"+result.getContent());
				} catch (Exception e) {
					logger.info("=============不激活25天自动撤单(定时任务)异常=======");
					e.printStackTrace();
				}
			}			
		};
		
		//设置首次执行时间
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 50);
			calendar.set(Calendar.SECOND, 00);
			Date firstTime = calendar.getTime();
			Timer timer = new Timer();
			//设置执行间隔时间
			long period = 24*60*60*1000;
			//执行定时任务
			timer.schedule(task, firstTime, period);
	}
	
	/**es文件系统定时任务*/
	private void TimingEsTask() throws Exception{
		logger.debug("=============启动es定时执行任务===========");
		//要执行的定时任务
		TimerTask task = new TimerTask(){
			@Override
			public void run() {
				logger.debug("===============开始执行定时任务(es)=============");
				logger.debug("===============执行定时任务完成(es)=============");
			}			
		};
//		Properties props = new Properties();
//		InputStream in = LoadRedisListenter.class.getResourceAsStream("/redisListener.properties");
//		props.load(in);
//		
//		int hour = Integer.parseInt(props.getProperty("hour"));
//		int minute = Integer.parseInt(props.getProperty("minute"));
//		int second = Integer.parseInt(props.getProperty("second"));
		
		//设置首次执行时间
		Calendar calendar = Calendar.getInstance();
				
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		Date firstTime = calendar.getTime();
		Timer timer = new Timer();
		//设置执行间隔时间
		long period = 2*60*60*1000;
		//执行定时任务
		timer.schedule(task, firstTime, period);
//		in.close();
	}
	
	
	/**定时订单备份*/
	private void OrderBackUpTask() throws Exception{
		logger.debug("=============定时订单备份===========");
		//要执行的定时任务
		TimerTask task = new TimerTask(){
			@Override
			public void run() {
				logger.debug("===============开始执行定时订单备份=============");
				try {
					String total_num =backupTotalNum;
					String remainder =backupRemainder;
					infoSaleOrderBusinessServDu.createOrderBackUp(total_num,remainder);
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.debug("===============执行定时完成单备份=============");
			}			
		};
		//取配置文件参数
//		Properties props = new Properties();
//		InputStream in = LoadRedisListenter.class.getResourceAsStream("/redisListener.properties");
//		props.load(in);
		
//		int hour = Integer.parseInt(props.getProperty("backup.hour"));
//		int minute = Integer.parseInt(props.getProperty("backup.minute"));
//		int second = Integer.parseInt(props.getProperty("backup.second"));
		//设置首次执行时间
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, backupHour);
		calendar.set(Calendar.MINUTE, backupMinute);
		calendar.set(Calendar.SECOND, backupSecond);
		Date firstTime = calendar.getTime();
		
		Timer timer = new Timer();
		//设置执行间隔时间
//		long period = 1*1000*Integer.parseInt(props.getProperty("backup.interval"));
		//执行定时任务
		timer.schedule(task, firstTime, backupInterval); 		
//		in.close();
	}
	
	/**定时发送消息队列*/
	private void SendActivemqTask() throws Exception{
		logger.debug("=============定时发送消息队列===========");
		//要执行的定时任务
		TimerTask task = new TimerTask(){
			@Override
			public void run() {
				logger.debug("===============开始执行发送消息队列=============");
				try {
					ParaVo vo =new ParaVo();
					//取配置文件参数
//					Properties props = new Properties();
//					InputStream in = LoadRedisListenter.class.getResourceAsStream("/redisListener.properties");
//					props.load(in);
//					String total_num=props.getProperty("activemq.total_num");
					String total_num =activemqTotalNum;
					if(total_num !=null && !"".equals(total_num)){
						vo.setTotal_num(total_num);
					}
//					String remainder=props.getProperty("activemq.remainder");
					String remainder =activemqRemainder;
					if(remainder !=null && !"".equals(remainder)){
						vo.setRemainder(remainder);
					}
					activemqServDu.createSendActivemq(vo);
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.debug("===============执行定时完成发送消息队列=============");
			}			
		};
		//取配置文件参数
//		Properties props = new Properties();
//		InputStream in = LoadRedisListenter.class.getResourceAsStream("/redisListener.properties");
//		props.load(in);
		
//		int hour = Integer.parseInt(props.getProperty("backup.hour"));
//		int minute = Integer.parseInt(props.getProperty("backup.minute"));
//		int second = Integer.parseInt(props.getProperty("backup.second"));
		//设置首次执行时间
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, activemqHour);
		calendar.set(Calendar.MINUTE, activemqMinute);
		calendar.set(Calendar.SECOND, activemqSecond);
		Date firstTime = calendar.getTime();
		
		Timer timer = new Timer();
		//设置执行间隔时间
//		long period = 1*1000*Integer.parseInt(props.getProperty("activemq.interval"));  //间隔1秒
		//执行定时任务
		timer.schedule(task, firstTime, activemqInterval); 	
//		in.close();
	}
	
	
	/**定时AOP赠款*/
	private void grantFeeTimer() throws Exception{
		logger.debug("=============定时AOP赠款===========");
		//要执行的定时任务
		TimerTask task = new TimerTask(){
			@Override
			public void run() {
				logger.debug("===============开始执行定时AOP赠款=============");
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					//取系统当前时间
				    String now=sdf.format(new Date());
				    Calendar cal = Calendar.getInstance();
				    cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
				    String first = sdf.format(cal.getTime());
				    cal.set(Calendar.DAY_OF_MONTH,2);//设置为2号,当前日期既为本月第二天 
				    String second = sdf.format(cal.getTime());
				    //不是当月1、2号的不再继续往下执行
				    if(!now.equals(first)&&!now.equals(second)){
				    	return;
				    }
					infoSaleOrderBusinessServDu.createGrantFeeTimer();
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.debug("===============定时赠款结束=============");
			}			
		};
		long grantmqInterval=60000*60*24;
		Timer timer = new Timer();
		//执行定时任务
		timer.schedule(task, 0, grantmqInterval); 		
	}
	
}
