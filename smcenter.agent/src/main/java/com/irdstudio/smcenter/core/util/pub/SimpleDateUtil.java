package com.irdstudio.smcenter.core.util.pub;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * <p>
 * Title:简单日期处理类
 * </p>
 * <p>
 * Description:简单日期处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: easycon
 * </p>
 * 
 * @author cky
 * @version 1.0
 * modify:
 * 	李广明,增加两个格式化日期的函数,2006-11-20
 */
public class SimpleDateUtil {
	private SimpleDateUtil() {
	}

	public static final int SHANG_XUN = 1;

	public static final int ZHONG_XUN = 2;

	public static final int XIA_XUN = 3;

	private static String datePattern = "yyyy-MM-dd";// default value

	private static int year;

	private static int month;

	private static int day;

	private static int season;

	private static int xun;

	public static int getYear() {
		return year;
	}

	public static int getMonth() {
		return month;
	}

	public static int getDay() {
		return day;
	}

	public static int getSeason() {
		return season;
	}

	public static int getXun() {
		return xun;
	}

	public static String getDatePattern() {
		return datePattern;
	}

	public static void setDatePattern(String newDatePattern) {
		datePattern = newDatePattern;
	}

	/**
	 * 根据月份返回当前季度
	 * 
	 * @param strMonth
	 *            int
	 * @return int
	 */

	private static int getSeason(int month) throws Exception {
		int itMonth = month;
		if (itMonth < 1 || itMonth > 12) {
			throw new Exception("getSeasonByMonth传入的月份无效:" + itMonth);
		}
		if (1 <= itMonth && itMonth <= 3) {
			return 1;
		} else if (4 <= itMonth && itMonth <= 6) {
			return 2;
		} else if (7 <= itMonth && itMonth <= 9) {
			return 3;
		} else {
			return 4;
		}

	}

	/**
	 * 根据一个月中的第几天返回旬
	 * 
	 * @param day_of_month
	 *            int 一个月中的第几天
	 * @return int 这个是一个月中的什么旬
	 */
	private static int getXun(int day_of_month) throws Exception {
		if (day_of_month < 1 || day_of_month > 31) {
			throw new Exception("输入的天数有误" + day_of_month);
		} else {
			if (day_of_month >= 1 && day_of_month <= 10) {
				return SHANG_XUN;
			}
			if (day_of_month > 10 && day_of_month <= 20) {
				return ZHONG_XUN;
			}
			if (day_of_month > 20) {
				return XIA_XUN;
			}
		}
		return 0;
	}

	public static Calendar parse2Calendar(String str_date) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat(datePattern);
		sf.setLenient(false);
		Date date = null;
		try {
			date = sf.parse(str_date);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			throw new Exception("传入的日期有误，或者没有正确设置format格式", e);
		}
		Calendar tmpd = Calendar.getInstance();
		tmpd.setTime(date);
		return tmpd;
	}

	/**
	 * 从参数中返回日期串的各个部分
	 * 
	 * @param str_date
	 *            String
	 * @param year
	 *            Integer
	 * @param month
	 *            Integer
	 * @param day
	 *            Integer
	 * @throws Exception
	 */
	public static void getDateElements(String str_date) throws Exception {
		if (str_date == null || str_date.equals("")) {
			throw new Exception("查询日期不能为空");
		}
		Calendar d = null;
		try {
			d = parse2Calendar(str_date);
		} catch (Exception e) {
			throw new Exception("日期解析出错! 日期:" + str_date + " 日期格式:"
					+ datePattern);
		}
		day = d.get(Calendar.DAY_OF_MONTH);
		month = d.get(Calendar.MONTH) + 1;// 修正
		year = d.get(Calendar.YEAR);
		season = getSeason(month);
		xun = getXun(day);
	}

	/**
	 * 重载,加了模式串选项
	 * 
	 * @param str_date
	 *            String
	 * @param date_pattern
	 *            String
	 * @param year
	 *            Integer
	 * @param month
	 *            Integer
	 * @param day
	 *            Integer
	 * @throws Exception
	 */
	public static void getDateElements(String str_date, String date_pattern)
			throws Exception {
		if (date_pattern == null || date_pattern.trim().equals("")) {
			throw new Exception("模式字符串不能为空");
		}
		setDatePattern(date_pattern);
		getDateElements(str_date);
	}

	/**
	 * 返回日期加减后的新日期
	 * 
	 * @param str_date
	 *            String
	 * @param amount
	 *            int
	 * @throws Exception
	 * @return String
	 */
	public static String getModifyDate(String str_date, int amount)
			throws Exception {
		Calendar d = parse2Calendar(str_date);
		d.add(Calendar.DAY_OF_MONTH, amount);
		SimpleDateFormat sf = new SimpleDateFormat(datePattern);
		sf.setLenient(false);
		return sf.format(d.getTime());

	}

	/**
	 * 返回两个日期的相差天数,使用自定义格式
	 * date_end-date_begin
	 * 
	 * @param date1
	 *            String
	 * @param date2
	 *        	  String
	 * @return int
	 */
	public static long diffDate(String date_begin, String date_end,
			String date_format) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat(date_format);
		sf.setLenient(false);
		Date d1 = sf.parse(date_begin);
		Date d2 = sf.parse(date_end);
		long beginTime = d1.getTime();
		long endTime = d2.getTime();
		long betweenDays = (long) ((endTime - beginTime) / (1000 * 60 * 60 * 24));
		return betweenDays;
	}
	/**
	 * 针对格式不同的日期差
	 * @param date_begin
	 * @param date_end
	 * @param date_begin_format
	 * @param date_end_format
	 * @return
	 * @throws Exception
	 */
	public static long diffDate(String date_begin, String date_end,
			String date_begin_format,String date_end_format) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat(date_begin_format);
		SimpleDateFormat sf2=new SimpleDateFormat(date_end_format);
		sf.setLenient(false);
		sf2.setLenient(false);
		Date d1 = sf.parse(date_begin);
		Date d2 = sf2.parse(date_end);
		long beginTime = d1.getTime();
		long endTime = d2.getTime();
		long betweenDays = (long) ((endTime - beginTime) / (1000 * 60 * 60 * 24));
		return betweenDays;
	}
	
	/**
	 * 返回两个日期的相差天数,默认格式yyyy-MM-dd
	 *  date_end-date_begin
	 * @param date_begin
	 *            String
	 * @param date_end
	 *            String
	 * @throws Exception
	 * @return long
	 */
	public static long diffDate(String date_begin, String date_end)
			throws Exception {
		return diffDate(date_begin, date_end, "yyyy-MM-dd");
	}
	
	/**
	 * 检查两个日期之间的月份相差数
	 * @param dateBegin
	 * @param dateEnd
	 * @param dateFormat
	 * @return
	 */
	public static long diffMonth(String dateBegin, String dateEnd,
			String dateFormat) {
		if (dateBegin == null || "".equals(dateBegin) || dateEnd == null
				|| "".equals(dateEnd)) {
			return -1;
		}
		
		Calendar begin = null;
		Calendar end = null;
		
		try {
			begin = parse2Calendar(dateBegin);
			end	  = parse2Calendar(dateEnd);
		} catch (Exception e) {
			return -1;
		}		
		
		int beginMonth = begin.get(Calendar.MONTH) + 1;
		int endMonth   = end.get(Calendar.MONTH) + 1;
		
		int year = Math.abs(begin.get(Calendar.YEAR) - end.get(Calendar.YEAR));
		
		return Math.abs((year * 12) + (beginMonth - endMonth));
	}
	
	/**
	 * 检查两个日期之间的月份相差数
	 * @param dateBegin
	 * @param dateEnd
	 * @return
	 */
	public static long diffMonth(String dateBegin,String dateEnd){
		return diffMonth(dateBegin,dateEnd,datePattern);
	}
	
	 /**
	 * 格式化指定格式的日期
	 * @param strDate String
	 * @param strFormat String
	 * @return String
	 */
	public static String formatDate(String date,String strFormat){
	  String strRet = null;
	   SimpleDateFormat sdf=new SimpleDateFormat(strFormat,Locale.ENGLISH);
	   try{
	     strRet = sdf.format(sdf.parse(date));
	     return strRet;
	   }catch(ParseException e){
	     e.printStackTrace();
	     return date;
	   }
	}

	 /**
	 * 格式化指定格式的日期
	 * @param strDate String
	 * @param strFormat String
	 * @return String
	 */
	public static String formatDate(Date date,String strFormat){
	   SimpleDateFormat sdf=new SimpleDateFormat(strFormat,Locale.ENGLISH);
	   return sdf.format(date);
	}
	
	/**
	 * 返回某年某月的最后一天是几号
	 * @param nf
	 * @param yf
	 * @return
	 */
	public static int getMonthLastDay(int nf,int yf) {
		
		StringBuffer theDate = new StringBuffer();
		theDate.append(nf);
		theDate.append("-");
		theDate.append(yf);
		theDate.append("-");
		theDate.append("01");
		
		SimpleDateFormat sf = new SimpleDateFormat(datePattern);
		Date date = null;
		try {
			date = sf.parse(theDate.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH,1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	
	/**
	 * 返回某年某月的最后一天的日期
	 * @param nf
	 * @param yf
	 * @return
	 */
	public static String getMonthLastDayStr(int nf,int yf) {
		
		StringBuffer theDate = new StringBuffer();
		theDate.append(nf);
		theDate.append("-");
		theDate.append(yf);
		theDate.append("-");
		theDate.append("01");
		
		SimpleDateFormat sf = new SimpleDateFormat(datePattern);
		Date date = null;
		try {
			date = sf.parse(theDate.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH,1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return formatDate(calendar.getTime(),"yyyy-MM-dd");
	}
	
	/**
	 * 日期加上年月日.
	 * @param date 原始日期
	 * @param nyr  年月日(六位的数字yymmdd)
	 * @return 加上年月日后的日期.
	 */
	@SuppressWarnings("deprecation")
	public static String addYearMonthDay(String date,String nyr){
		Date date1=null;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			date1=sf.parse(date);
		} catch (Exception e) {
			throw new UnsupportedOperationException("解析日期["+date+"]出错");
		}
		
		int year, month, day;
		try {
			year = Integer.parseInt(nyr.substring(0, 2));
			month = Integer.parseInt(nyr.substring(2, 4));
			day = Integer.parseInt(nyr.substring(4, 6));
		} catch (RuntimeException e) {
			System.err.println("计算年月日错误:"+nyr);
			throw e;
		}			
		int y=date1.getYear();
		int m=date1.getMonth();
		int d=date1.getDate();
		return sf.format(new Date(y+year,m+month,d+day));
	}
	
	public static void main(String args[]) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
		String xtrq = sdf.format(new Date());
		
		System.out.println(xtrq);
		System.out.println(addYearMonthDay(xtrq,"010101"));
		
		SimpleDateUtil.getDateElements("2003-01-12");
		System.out.println("年份：" + SimpleDateUtil.getYear());
		System.out.println("月份：" + SimpleDateUtil.getMonth());
		System.out.println("日期：" + SimpleDateUtil.getDay());
		System.out.println("季度：" + SimpleDateUtil.getSeason());
		System.out.println("旬:" + SimpleDateUtil.getXun());
		System.out.println("获得相对日期："
				+ SimpleDateUtil.getModifyDate("2010-12-1", -12));
		
		System.out.println(SimpleDateUtil.diffMonth("2003-05-12","2005-05-25"));
		System.out.println(SimpleDateUtil.diffMonth("2006-05-12","2006-06-01"));
		System.out.println(SimpleDateUtil.diffMonth("2006-05-12","2006-04-01"));
		System.out.println(SimpleDateUtil.diffMonth("2005-05-12","2004-08-01"));
	}
}
