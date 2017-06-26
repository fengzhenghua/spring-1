package com.tydic.unicom.uoc.service.es.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EsDateUtil {

	public static final String STD_DATE = "yyyy-MM-dd HH:mm:ss";
	public static final String SHORT_DATE = "yyyy-MM-dd";
	public static final String FULL_DATE = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String ES_DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	private static final Pattern isLongDate = Pattern.compile("^\\d{13}$");
	private static final Pattern isShortDate = Pattern.compile("^2\\d{3}-\\d{1,2}-\\d{1,2}$");// yyyy-MM-dd
	private static final Pattern isStdDate = Pattern.compile("^2\\d{3}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$");// yyyy-MM-dd HH:mm:ss
	private static final Pattern isFullDate = Pattern.compile("^2\\d{3}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}$");// yyyy-MM-dd HH:mm:ss.SSS
	private static final Pattern isESDate = Pattern.compile("^2\\d{3}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}Z$");// yyyy-MM-dd HH:mm:ss.SSS
	private static final Pattern isESDateZone = Pattern.compile("(^2\\d{3}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3})[+-]?\\d+$");// yyyy-MM-ddTHH:mm:ss.SSS+0800

	/**
	 * 字符串转换为Date。
	 * 
	 * <pre>
	 * 支持格式:
	 * Long型、
	 * yyyy-MM-dd、
	 * yyyy-MM-dd HH:mm:ss、
	 * yyyy-MM-dd HH:mm:ss.SSS、
	 * yyyy-MM-ddTHH:mm:ss.SSSZ
	 * </pre>
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDate(String dateStr) throws ParseException {
		if (dateStr == null || "".equals(dateStr.trim())) {
			throw new ParseException("要转换的数据为空", 0);
		}
		if (isStdDate.matcher(dateStr).matches()) {
			return strToDate(dateStr, STD_DATE);
		} else if (isFullDate.matcher(dateStr).matches()) {
			return strToDate(dateStr, FULL_DATE);
		} else if (isShortDate.matcher(dateStr).matches()) {
			return strToDate(dateStr, SHORT_DATE);
		} else if (isLongDate.matcher(dateStr).matches()) {
			try {
				long l = Long.parseLong(dateStr);
				if (l >= 1451577600000L && l < 4607251200000L) {// 大于2016-01-01 00:00:00 小于:2116-01-01 00:00:00
					return new Date(l);
				} else {
					throw new ParseException(dateStr + "日期超出了系统可接受的范围", 0);
				}
			} catch (NumberFormatException e) {
				throw new ParseException(dateStr + "转换为日期时出错", 0);
			}
		} else if (isESDate.matcher(dateStr).matches()) {
			return strToDate(dateStr, ES_DATEFORMAT);
		} else {
			Matcher matcher = isESDateZone.matcher(dateStr);
			if (matcher.matches()) {
				return strToDate(matcher.group(1), "yyyy-MM-dd'T'HH:mm:ss.SSS");
			}
		}
		throw new ParseException(dateStr + "转换为日期时出错,未知的日期格式", 0);
	}

	public static Date strToDate(String dateStr, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false);
		return sdf.parse(dateStr);
	}

	public static String formatDate(String dateStr, String format) throws ParseException {
		return new SimpleDateFormat(format).format(strToDate(dateStr));
	}

	public static String formatDate(long longDate, String format) {
		return new SimpleDateFormat(format).format(new Date(longDate));
	}

	public static String formatDateToEs(String dateStr) throws ParseException {
		return formatDate(dateStr, ES_DATEFORMAT);
	}

	public static String formatDateToEs(long longDate) {
		return formatDate(longDate, ES_DATEFORMAT);
	}
	
	public static long getTimeStamp(String dateStr) throws Exception{
		if (dateStr == null || "".equals(dateStr.trim())) {
			throw new ParseException("要转换的数据为空", 0);
		}
		Date date = new Date();
		if (isStdDate.matcher(dateStr).matches()) {
			date = strToDate(dateStr, STD_DATE);
		} else if (isFullDate.matcher(dateStr).matches()) {
			date = strToDate(dateStr, FULL_DATE);
		} else if (isShortDate.matcher(dateStr).matches()) {
			date = strToDate(dateStr, SHORT_DATE);
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		long timestamp = cal.getTimeInMillis();
		return timestamp;
	}

}
