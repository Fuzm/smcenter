package com.irdstudio.ssm.framework.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**ss
 * 日期时间工具类
 * 
 * @author chenw
 * 
 */
public class DateTool {

	public static final String PATTERN_DEFAULT = "yyyy-MM-dd";
	public static final String PATTERN_DAYPATH = "yyyy\\MM\\dd\\";
	public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_DATETIME_COMPACT = "yyyyMMddHHmmss";
	public static final String PATTERN_DATETIME_COMPACT_SSS = "yyyyMMddHHmmssSSS";
	public static final String PATTERN_DATE_COMPACT = "yyyyMMdd";
	public static final String PATTERN_DATESHORT = "yyMMdd";
	public static final String PATTERN_YEARMONTH = "yyyyMM";

	private static final String MONTHS_STRING[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
			"Oct", "Nov", "Dec" };

	/**
	 * 获取年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取日份
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回小时
	 * 
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 返回分钟
	 * 
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 * 
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}

	/**
	 * 返回毫秒
	 * 
	 * @param date
	 * @return
	 */
	public static long getMillis(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDateWithDay(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDateWithMilliSecond(Date date, long milliSecond) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + milliSecond);
		return c.getTime();
	}

	/**
	 * 日期相减
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int diffDate(Date date1, Date date2) {
		return (int) ((getMillis(date1) - getMillis(date2)) / (24 * 3600 * 1000));
	}

	/**
	 * 日期相减
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long diffDateInMillis(Date date1, Date date2) {
		return (long) ((getMillis(date1) - getMillis(date2)));
	}

	/**
	 * 
	 * 格式化日期
	 * 
	 * 
	 * @param date
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 * 
	 */
	public static String getDateTimeStr(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化日期
	 * 
	 * 
	 * @param date
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getDateStr(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}

	/**
	 * 获取当前日期时间字符
	 * 
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 获取当前日期时间字符,到SSS
	 * 
	 * 
	 * @return yyyyMMddHHmmssSSS
	 */
	public static String getCurrentDateTimeSSS() {
		return formatDate(new Date(), PATTERN_DATETIME_COMPACT_SSS);
	}

	/**
	 * 获取当前日期时间字字符
	 * 
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getCurrentDate() {
		return formatDate(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 将字符型日期时间转换为Date
	 * 
	 * @param strDate
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date getDateFromLongStr(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将字符型日期转换为Date
	 * 
	 * @param strDate
	 *            yyyy-MM-dd
	 * 
	 * @return
	 */
	public static Date getDateFromStr(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 获取指定日期在年份一年的天数
	 * 
	 * @param date
	 *            日期
	 * 
	 * @return 返回 366或365
	 */
	public static int getYearDays(Date date) {
		GregorianCalendar c = (GregorianCalendar) GregorianCalendar.getInstance();
		c.setTime(date);
		return c.isLeapYear(c.get(1)) ? 366 : '\u016D';
	}

	/**
	 * 获取指定日期 在月份那月的天数
	 * 
	 * @param date
	 * @return 返回date 在月份的天数
	 */
	public static int getMonthDays(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(5);
	}

	/**
	 * 比较两个日期时间是否相等，相等返回true，不等返回false
	 * 
	 * @param date1
	 * 
	 * @param date2
	 * 
	 * @return
	 */
	public static boolean compareDateTime(Date date1, Date date2) {
		return !date1.before(date2) && !date2.before(date1);
	}

	/**
	 * 比较两个日期的年月日是否相等，相等返回true，不等返回false
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareDay(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6);
	}

	/**
	 * 将UTC格式的日期时间转换为Date类型
	 * 
	 * @param date UTC格式的日期
	 * @return
	 */
	public static Date parseDateUTC(String date) {
		date = date.substring(4);
		date = date.replace("UTC 0800 ", "");
		for (int i = 0; i < MONTHS_STRING.length; i++) {
			if (!date.startsWith(MONTHS_STRING[i]))
				continue;
			date = date.replace(MONTHS_STRING[i], String.valueOf(i + 1));
			break;
		}
		SimpleDateFormat df = new SimpleDateFormat("MM dd HH:mm:ss yyyy");
		try {
			return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 获取指定日期的最后时间
	 * 
	 * 
	 * @param date
	 * @return yyyy-MM-dd 23:59:59
	 */
	public static String getEndDay(Date date) {
		return (new StringBuilder(String.valueOf(getDateStr(date)))).append(" 23:59:59").toString();
	}

	/**
	 * 
	 * 格式化日期
	 * 
	 * 
	 * @param date
	 *            需要格式化的日期时间
	 * 
	 * @param pattern
	 *            格式化形式，参考上面
	 * 
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		String result = "";
		if (date != null) {
			java.text.DateFormat df = new SimpleDateFormat(pattern);
			result = df.format(date);
		}
		return result;
	}


	/**
	 * 获取今年最后一天，返回字符串，格式为：2011-12-31
	 * 
	 * @return
	 */
	public static String getEndOfYearStr() {
		return (new StringBuilder(String.valueOf(Calendar.getInstance().get(1)))).append("-12-31").toString();
	}

	/**
	 * 获取指定时间跟当前时间的间隔，返回中文描述
	 * 
	 * 
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static String getTimeDescription(java.util.Date time) throws Exception {
		long tt = System.currentTimeMillis() - time.getTime(); // 离现在的时间间隔
		long t = tt / (3600 * 1000);
		if (t > 24) {
			String timeS = getDateTimeStr(time);
			java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
			tt = df.parse(df.format(new java.util.Date())).getTime() - df.parse(timeS.substring(0, 10)).getTime();
			long d = tt / (24 * 3600 * 1000);
			if (d >= 3) {
				return timeS;
			} else if (d == 2) {
				return "前天";
			} else {
				return "昨天";
			}
		} else {
			if (t > 0) {
				return Long.toString(t) + "小时";
			} else {
				t = tt / (60 * 1000);
				if (t > 0) {
					return Long.toString(t) + "分钟";
				} else {
					return "刚刚";
				}
			}
		}
	}

	/**
	 * Get the date time in simple formatted string, such as '2012-09-14
	 * 11:12:00'.
	 * 
	 * @param date
	 * @return
	 */
	public static String getSimpleDateTime(Date date) {
		if (date == null) {
			return "";
		}

		return new SimpleDateFormat(PATTERN_DATETIME).format(date);
	}

	/**
	 * Parse the simple date time literal into Date object.
	 * 
	 * @param dateTime
	 * @return
	 */
	public static Date parseSimpleDateTime(String dateTime) {
		if (dateTime == null || "".equals(dateTime.trim())) {
			return null;
		}

		try {
			return new SimpleDateFormat(PATTERN_DATETIME).parse(dateTime);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Get the date several days ago, e.g. a month ago (30 days).
	 * 
	 * @param days
	 * @return
	 */
	public static Date getDateTimeDaysAgo(int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		cal.add(Calendar.DAY_OF_MONTH, 0 - days);

		return cal.getTime();
	}
	
	/**
	 * @职责 字条串转DATE
	 * 
	 * @author niezm;
	 * @since 2015-3-27 下午5:07:48
	 */
	public static Date parseDate(String date, String formate)
	{
		Date d = null;
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat(formate);
			d = formatter.parse(date);
		}
		catch (Exception e)
		{
			//e.printStackTrace();
		}
		return d;
	}
	
	/**
	 * @职责 DATE转字符串
	 * 
	 * @author niezm;
	 * @since 2015-3-27 下午5:07:50
	 */
	public static String formateDate(Date date, String formate)
	{
		String s = null;
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat(formate);
			s = formatter.format(date);
		}
		catch (Exception e)
		{
			//e.printStackTrace();
		}
		return s;
	}
	
	/**
     * @职责 DATE转字符串
     * 
     * @author niezm;
     * @since 2015-3-27 下午5:07:50
     */
    public static String formateDate(String formate){
        String s = null;
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(formate);
            s = formatter.format(new Date());
        }
        catch (Exception e)
        {
            //e.printStackTrace();
        }
        return s;
    }
    
    /**
	 * 将字符串(格式符合规范)转换成Date
	 * @param value 需要转换的字符串
	 * @param format 日期格式 
	 * @return Date
	 */
	public static Date string2Date(String value,String format){
		if(value == null || "".equals(value)){
			return null;
		}
		SimpleDateFormat sdf = getFormat(format);
		Date date = null;
		try {
			value = formatDate(value, format);
			date = sdf.parse(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 获取日期显示格式，为空默认为yyyy-mm-dd HH:mm:ss
	 * @author chenssy
	 * @date Dec 30, 2013
	 * @param format
	 * @return
	 * @return SimpleDateFormat
	 */
	protected static SimpleDateFormat getFormat(String format){
		if(format == null || "".equals(format)){
			format = PATTERN_DATETIME;
		}
		return new SimpleDateFormat(format);
	}
	/**
	 * 获取当前时间，以指定的格式进行返回
	 * @param format
	 * @return
	 */
	public static String getDate(String format) {
		Date date =new Date();
		return formateDate(date, format);
	}
	/**
	 * 
	 * @Description 日期格式转换
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(String date, String format) {
		if (date == null || "".equals(date)){
			return "";
		}
		Date dt = null;
		SimpleDateFormat inFmt = null;
		SimpleDateFormat outFmt = null;
		ParsePosition pos = new ParsePosition(0);
		date = date.replace("-", "").replace(":", "");
		if ((date == null) || ("".equals(date.trim())))
			return "";
		try {
			if (Long.parseLong(date) == 0L)
				return "";
		} catch (Exception nume) {
			return date;
		}
		try {
			switch (date.trim().length()) {
			case 14:
				inFmt = new SimpleDateFormat("yyyyMMddHHmmss");
				break;
			case 12:
				inFmt = new SimpleDateFormat("yyyyMMddHHmm");
				break;
			case 10:
				inFmt = new SimpleDateFormat("yyyyMMddHH");
				break;
			case 8:
				inFmt = new SimpleDateFormat("yyyyMMdd");
				break;
			case 6:
				inFmt = new SimpleDateFormat("yyyyMM");
				break;
			case 7:
			case 9:
			case 11:
			case 13:
			default:
				return date;
			}
			if ((dt = inFmt.parse(date, pos)) == null)
				return date;
			if ((format == null) || ("".equals(format.trim()))) {
				outFmt = new SimpleDateFormat("yyyy年MM月dd日");
			} else {
				outFmt = new SimpleDateFormat(format);
			}
			return outFmt.format(dt);
		} catch (Exception ex) {
		}
		return date;
	}

}
