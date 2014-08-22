package com.smarts.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类
 * 
 * @author 杨雪军(Yang xue jun)<br>
 *         2014年8月21日 下午1:15:05<br>
 */
public final class DateUtils {

	/**
	 * 将字符串日期转成Date
	 * 
	 * @param date日期
	 * @param format
	 *            日期格式
	 * @return
	 * @throws ParseException
	 */
	public static Date string2Date(String date, String format)
			throws ParseException {
		return org.apache.commons.lang3.time.DateUtils.parseDate(date, format);
	}

	/**
	 * 将字符串日期转成Date，format默认为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            日期
	 * @return
	 * @throws ParseException
	 */
	public static Date string2Date(String date) throws ParseException {
		return string2Date(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 日期转成成字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            输出日期格式
	 * @return
	 */
	public static String date2String(Date date, String format) {
		return DateFormatUtils.format(date, format);
	}

	/**
	 * 日期转成成字符串，默认输出日期格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String date2String(Date date) {
		return date2String(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将字符串日期，格式化为时间戳的形式
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static long string2UnixTime(String date, String format)
			throws ParseException {
		Date d = string2Date(date, format);
		return d.getTime();
	}

	/**
	 * 将字符串日期，格式化为时间戳的形式,（到秒）
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static long string2UnixTimeSecond(String date, String format)
			throws ParseException {
		return string2UnixTime(date, format) / 1000;
	}

	/**
	 * 将时间戳的毫秒数转成日期
	 * 
	 * @param timestamp
	 * @return
	 */
	public static Date unixTime2Date(long timestamp) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp*1000);
		return cal.getTime();
	}

	/**
	 * 将时间戳的毫秒数转成字符日期
	 * @param timestamp
	 * @param format
	 * @return
	 */
	public static String unixTime2String(long timestamp, String format) {
		return date2String(unixTime2Date(timestamp), format);
	}

	/**
	 * 将时间戳的毫秒数转成字符日期，format默认为yyyy-MM-dd HH:mm:ss
	 * @param timestamp
	 * @return
	 */
	public static String unixTime2String(long timestamp) {
		return unixTime2String(timestamp, "yyyy-MM-dd HH:mm:ss");
	}
}
