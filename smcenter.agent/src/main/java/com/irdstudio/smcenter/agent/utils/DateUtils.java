package com.irdstudio.smcenter.agent.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author liuming
 *
 */
public class DateUtils {

	/**
	 * 獲取下一天
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static String getNextDay(String date,String format) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
		dateFormat.setLenient(false);
		Date timeDate = dateFormat.parse(date);

		Date next = org.apache.commons.lang3.time.DateUtils.addDays(timeDate, 1);
		return dateFormat.format(next);
	}
	
	/**
	 * 月份相加
	 * @param dateString
	 * @param monthAmount
	 * @return
	 */
	public static String getDateStringByMonth(String dateString, int monthAmount) {
		String[] sday = dateString.split("-");
		Calendar rsCalendar = Calendar.getInstance();
		rsCalendar.setTime(getDateFromDbString(dateString));
		rsCalendar.add(Calendar.MONTH, monthAmount);

		int maxDay = rsCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int dayCount = Integer.parseInt(sday[2]) > maxDay ? maxDay : Integer.parseInt(sday[2]);
		rsCalendar.set(Calendar.DAY_OF_MONTH, dayCount);

		return getDateStringToDb(rsCalendar.getTime());
	}
	/**
	 * 把数据库的字符串转化成Date型
	 * 
	 * @param dateString
	 *            字符串格式(yyyy-MM-dd)
	 * @return
	 */
	public static Date getDateFromDbString(String dateString) {
		try {
			return THREADLOCAL_YCLOANS_FORMAT.get().parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalArgumentException("string format error!!");
		}
	}
	/** 为保证线程安全重构为ThreadLocal,日期格式yyyy-MM-dd */
	private static ThreadLocal<DateFormat> THREADLOCAL_YCLOANS_FORMAT = new ThreadLocal<DateFormat>() {
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		}
	};
	/**
	 * 
	 * @param date
	 * @return 将日期格式转换为String类型，并填充。例如：2009-5-5 ---> 2009-05-05
	 */
	public static String getDateStringToDb(Date date) {
		return THREADLOCAL_YCLOANS_FORMAT.get().format(date);
	}
	/**
	 * 获取自然月首日
	 * @param openDay
	 * @return
	 */
	public static String getFirstDayofNextMonth(String openDay,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try{
			Date date = sdf.parse(openDay);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.MONTH, 1);
			return sdf.format(calendar.getTime());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 返回。输入日期+dayCount 输入负数，就是减少天数
	 * 
	 * @param inputDate
	 * @return
	 */
	public static String getDateIncDayCount(String inputDate, int dayCount) {
		Date d = getDateFromDbString(inputDate);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DAY_OF_YEAR, dayCount);
		return getDateStringToDb(c.getTime());
	}
	
	/**
	 * 计算两个任意时间中间的间隔天数 改进精确计算相隔天数的方法
	 * 
	 * @param d1
	 *            String型日期
	 * @param d2
	 *            String型日期
	 * @return int 天数
	 */
	public final static int getDaysBetween(String d1Str, String d2Str) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = new Date();
		Date d2 = new Date();
		try {
			d1 = df.parse(d1Str);
			d2 = df.parse(d2Str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getDaysBetween(d1,d2);
	}
	
	/**
	 * 计算两个任意时间中间的间隔天数 改进精确计算相隔天数的方法
	 * 
	 * @param d1
	 *            Date型日期
	 * @param d2
	 *            Date型日期
	 * @return int 天数
	 */
	public final static int getDaysBetween(Date d1, Date d2) {
		return getDaysBetween(date2Calendar(d1), date2Calendar(d2));
	}
	
	/**
	 * 计算两个任意时间中间的间隔天数 改进精确计算相隔天数的方法
	 * 
	 * @param d1
	 *            Calendar型日期
	 * @param d2
	 *            Calendar型日期
	 * @return int 天数
	 */
	public final static int getDaysBetween(Calendar d1, Calendar d2) {
		if (d1.after(d2)) {
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);// 得到当年的实际天数
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}
	
	/**
	 * Date转化为Calendar
	 * 
	 * @return Calendar型日期
	 */
	public final static Calendar date2Calendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
}
