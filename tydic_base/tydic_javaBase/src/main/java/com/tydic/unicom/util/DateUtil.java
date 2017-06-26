package com.tydic.unicom.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具
 * */
public class DateUtil {

	/**
	 * 日期格式：yyyy-MM-dd HH:mm:ss
	 * */
	public static String dateFormatYyyy_MM_dd_HH_mm_ss= "yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期格式：yyyy-MM-dd HH:mm:ss.SSSSSS
	 * */
	public static String dateFormatYyyy_MM_dd_HH_mm_ss_SSSSSS= "yyyy-MM-dd HH:mm:ss.SSSSSS";

	/**
	 * 日期格式：yyyy-MM-dd E (E为星期几)
	 * */
	public static String dateFormatYyyy_MM_dd_E = "yyyy-MM-dd E";

	/**
	 * 日期格式：yyyy-MM-dd
	 */
	public static String dateFormatYyyy_MM_dd = "yyyy-MM-dd";

	/**
	 * 获取指定日期格式的系统时间字符串
	 * @param format 日期格式：取值从本类中日期格式静态变量中选取
	 * @return 指定日期格式的系统时间字符串
	 * */
	public static String getSysDateString(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(new Date());
	}

	/**
	 * 将日期转成指定格式的字符串
	 * @param date 日期
	 * @param format 日期格式：取值从本类中日期格式静态变量中选取
	 * @return 指定日期格式的时间字符串
	 * */
	public static String getDateString(Long date, String format) {
		DateFormat dateFormat= new SimpleDateFormat(format);
		return dateFormat.format(new Date(date));
	}

	/**
	 * 将日期转成中文格式（年月日时分秒）的字符串
	 * @param date Long 日期
	 * @return 指定日期格式的时间字符串
	 * */
	public static String getDateStringZh(Long date) {
		DateFormat dateFormat= new SimpleDateFormat(dateFormatYyyy_MM_dd_HH_mm_ss);
		String formatString= dateFormat.format(new Date(date));
		return formatString.substring(0, 4)+ "年"+ formatString.substring(5, 7)+ "月"+ formatString.substring(8, 10)+ "日"+ formatString.substring(11, 13)+ "点"+ formatString.substring(14, 16)+ "分"+ formatString.substring(17, 19)+ "秒";
	}

	/**
	 * 将日期转成中文格式（年-月-日）的字符串
	 * @param date Long 日期
	 * @return 指定日期格式的时间字符串
	 * */
	public static String getCurrentDate() {
		DateFormat dateFormat= new SimpleDateFormat(dateFormatYyyy_MM_dd);
		String formatString= dateFormat.format(new Date());
		return formatString;
	}

	/**
	 * 将日期转成中文格式（年月日 星期几）的字符串
	 *
	 * @return 指定日期格式的时间字符串
	 * */
	public static String getDateStringWithE() {
		DateFormat dateFormat= new SimpleDateFormat(dateFormatYyyy_MM_dd_E);
		String formatString = dateFormat.format(new Date());
		return formatString.substring(0, 4)+ "年"+ formatString.substring(5, 7)+ "月"+ formatString.substring(8, 10)+ "日   "+ formatString.substring(11, 14);
	}

	/**
	 * 将日期转成指定格式的字符串
	 * @param date 日期
	 * @param format 日期格式：取值从本类中日期格式静态变量中选取
	 * @return 指定日期格式的时间字符串
	 * */
	public static String chngDateString(Date date, String format) {
		DateFormat dateFormat= new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/**
	 *
	 * @author yangwen 2014-8-13 上午9:26:36
	 * @Method: getNextMonthFirst
	 * @Description: TODO 获得下个月第一天的日期
	 * @return 下个月第一天的日期字符串
	 */
	public static String getNextMonthFirst(){
		String nextMonDay = "";
		DateFormat dateFormat= new SimpleDateFormat(dateFormatYyyy_MM_dd);
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1); //加一个月
		lastDate.set(Calendar.DATE, 1);  //把日期设置为当月第一天
		nextMonDay = dateFormat.format(lastDate.getTime());
		return nextMonDay;
	}


	/**
	 *
	 * @author yangwen 2014-8-13 上午9:26:36
	 * @Method: getNextMonthFirst
	 * @Description: TODO 获得下个月第一天的日期
	 * @return 下个月第一天的日期字符串
	 */
	public static String getLastWeekDay(){
		String nextMonDay = "";
		DateFormat dateFormat= new SimpleDateFormat(dateFormatYyyy_MM_dd);
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, -7);  //把日期设置为当月第一天
		nextMonDay = dateFormat.format(lastDate.getTime());
		return nextMonDay;
	}


	/**
	 *
	 * @author yangwen 2014-8-13 上午9:26:36
	 * @Method: strToDate
	 * @Description: TODO 将指定的时间格式字符串转换为时间
	 * @param strDate 指定日期字符串
	 * @param format 日期格式
	 * @return 返回Date类型的日期
	 */
	public static Date strToDate(String strDate,String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		ParsePosition pos = new ParsePosition(0);
		Date strToDate = formatter.parse(strDate, pos);
		return strToDate;
	}

	/**
	 *
     * @author yangwen 2014-8-13 上午9:26:36
	 * @Method: getAfterMonth
	 * @Description: TODO 得到指定月后（前）的日期 参数传负数即可
	 * @param date 指定日期
	 * @param month 月份偏移量
	 * @return 返回指定月后（前）的日期字符串
	*/
	public static String  getAfterMonth(Date date,int month) {
	        Calendar c = Calendar.getInstance();//获得一个日历的实例
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        c.setTime(date);//设置日历时间
	        c.add(Calendar.MONTH,month);//在日历的月份上增加6个月
	        String strDate = sdf.format(c.getTime());//得到你想要的6个月后的日期
	        return strDate;
	 }

	public static String getBeforeDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	/**
	 * 获取所传日期当天的开始时间0点
	 * @param date
	 * @return
	 */
	public static String dayStartDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return chngDateString(c.getTime(), dateFormatYyyy_MM_dd_HH_mm_ss);
    }

	/**
	 * 获取所传日期当天的结束时间24点
	 * @param date
	 * @return
	 */
	public static String dayEndDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return chngDateString(c.getTime(), dateFormatYyyy_MM_dd_HH_mm_ss);
    }

	/**
	 * 判断选择的日期是否是同月
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isInSameMonth(Date startDate,Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String startMonth = sdf.format(startDate);
        String endMonth = sdf.format(endDate);
        if(startMonth.equals(endMonth)){
          return true;
        }
        return false;
    }

	/**
	* 两个时间相差距离多少天多少小时多少分多少秒
	*
	* @param str1
	*            时间参数 1 格式：1990-01-01 12:00:00
	* @param str2
	*            时间参数 2 格式：2009-01-01 12:00:00
	* @return long 返回值为：{天, 时, 分, 秒}
	* @throws java.text.ParseException
	*/
	public static long getDistanceTimes(String str1, String str2) throws java.text.ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		long diff = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diff;
	}

}
