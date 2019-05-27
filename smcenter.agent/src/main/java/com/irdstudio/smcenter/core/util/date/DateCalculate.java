package com.irdstudio.smcenter.core.util.date;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期计算
 * @author guangming.li
 * @version 1.0
 * @date 2013-10-28
 */
public class DateCalculate {
	/**
	 * 获取某月的最后一天
	 * 
	 * @param date
	 *            输入的日期
	 * @return 该日期对应月份的最后一天日期
	 */
	public static Date getLastDateOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	/**
	 * 获取两个日期之间的月份差值
	 * 
	 * @param date1
	 *            输入的日期1
	 * @param date2
	 *            输入的日期2
	 * @return (日期2-日期1)月份的差值
	 */
	public static int months(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance(), cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		return 12 * (cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR))
				+ cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
	}

	/**
	 * 获取两个日期之间的天数差值
	 * 
	 * @param date1
	 *            输入的日期1
	 * @param date2
	 *            输入的日期2
	 * @return (日期2-日期1)天数的差值
	 */

	public static int days(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance(), cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int days = (int) ((cal2.getTimeInMillis() - cal1.getTimeInMillis()) / 1000 / 60 / 60 / 24);
		if (days == 0 && date2.before(date1)) {
			days = -1;
		}
		return days;
	}

	/**
	 * 返回某天增加或删减天数后的日期
	 * 
	 * @param date
	 *            输入的日期
	 * @param days
	 *            需增加的天数
	 * @return 新日期
	 */
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

	/**
	 * 返回某天增加或删减月份后的日期
	 * 
	 * @param date
	 * @param months
	 * @return
	 */
	public static Date addMonths(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	/**
	 * 获取对应日期的一年的最后一天
	 * 
	 * @param date
	 *            输入的日期
	 * @return 该日期所在年份的最后一天
	 */
	public static Date getLastDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		return cal.getTime();
	}

	/**
	 * 获取下个月的第一天
	 * 
	 * @param date
	 *            输入的日期
	 * @return 该日期对应的下个月的第一天
	 */
	public static Date getFirstDateOfNextMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获取输入月的第一天
	 * 
	 * @param date
	 *            输入的日期
	 * @return 该日期对应的该月第一天
	 */
	public static Date getFirstDateOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获取输入月的上个月的第一天
	 * 
	 * @param month
	 * @return
	 */
	public static Date getFirstDateOfMonth(int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获取对应日期的明年的最后一天
	 * 
	 * @param date
	 *            输入的日期
	 * @return 该日期下一年的最后一天
	 */
	public static Date getLastDayOfNextYear(Date date) {
		Date _date = getLastDayOfYear(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(_date);
		cal.add(Calendar.YEAR, 1);
		return cal.getTime();
	}

	/**
	 * 获取当年的第一天
	 * 
	 * @return 当年的第一天日期
	 */
	public static Date getFirstDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return cal.getTime();
	}

	/**
	 * 返回后两个月的最后一天
	 * 
	 * @param date
	 *            操作的日期
	 * @return 该日期后两个月的最后一天日期
	 */
	public static Date getNextThreeMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.get(Calendar.MONTH) >= 8)
			cal.set(Calendar.MONTH, 11);
		else
			cal.add(Calendar.MONTH, 3);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	/**
	 * 获取对应月份的最后一天
	 * 
	 * @param month
	 *            月份(1-12)
	 * @return 对应月份的最后一天
	 */
	public static Date getLastDateOfMonth(int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return DateCalculate.getLastDateOfMonth(cal.getTime());
	}

	/**
	 * 获取对应时间的月份
	 * 
	 * @param date
	 *            输入时间
	 * @return 该时间的月份 1-12
	 */
	public static int getMonthOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取对应时间的日期
	 * 
	 * @param date
	 *            输入时间
	 * @return 日 1-(28~31)
	 */
	public static int getDayOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取对应时间的年份
	 * 
	 * @param date
	 *            输入时间
	 * @return 年份yyyy
	 */
	public static int getYearOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * 计算两个字符时间之间相隔的秒数
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public static BigDecimal computeIntervalMills(String startTime,String endTime,String format){
		Calendar cal = Calendar.getInstance();
		long startMills = 0, endMills = 0;
		try {
			cal.setTime(new SimpleDateFormat(format).parse(startTime));
			startMills = cal.getTimeInMillis();
			cal.setTime(new SimpleDateFormat(format).parse(endTime));
			endMills = cal.getTimeInMillis();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new BigDecimal(endMills - startMills).divide(new BigDecimal(
				1000.00));
	}

	/**
	 * 默认为计算常用的时间表示格式(YYYY-MM-DD HH:MM:SS)
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static BigDecimal computeIntervalMills(String startTime,String endTime){
		return computeIntervalMills(startTime,endTime,DateConst.FULL_FORMAT);
	}
	

	/**
	 * 获取两个日期间最大日期
	 * 
	 * @param date1
	 *            日期1
	 * @param date2
	 *            日期2
	 * @return 大的日期
	 */
	public static Date getMaxDate(Date date1, Date date2) {
		return date1.after(date2) ? date1 : date2;
	}

	/**
	 * 比较两个日期是否是同一天
	 * 
	 * @param date1
	 *            日期1
	 * @param date2
	 *            日期2
	 * @return
	 */
	public static boolean equals(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance(), cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
				&& (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH))
				&& (cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE));
	}
	
	public static void main(String[] args) {
		System.out.println(DateCalculate.addDays(new Date(), 1));
		System.out.println("日期+1:"
				+ DateConvert.toString(DateCalculate.addDays(
						DateConvert.toDateWithSeparate1("2013-02-28"), 1)));

		System.out.println("日期-1:"
				+ DateConvert.toString(DateCalculate.addDays(
						DateConvert.toDateWithSeparate1("2013-02-28"), -1)));

		System.out.println("日期-1:"
				+ DateConvert.toString(DateCalculate.addDays(
						DateConvert.toDateWithSeparate1("2013-03-01"), -1)));
		System.out.println("日期+1:"
				+ DateConvert.toString(DateCalculate.addDays(
						DateConvert.toDateWithSeparate1("2012-02-28"), 1)));
		System.out.println("日期+1:"
				+ DateConvert.toString(DateCalculate.addDays(
						DateConvert.toDateWithSeparate1("2012-02-29"), 1)));
		System.out.println("日期-1:"
				+ DateConvert.toString(DateCalculate.addDays(
						DateConvert.toDateWithSeparate1("2012-03-01"), -1)));
	}
}
