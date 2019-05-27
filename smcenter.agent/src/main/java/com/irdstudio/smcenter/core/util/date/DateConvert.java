package com.irdstudio.smcenter.core.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换
 * 各种转换,字符串到日期，日期到字符串
 * @author guangming.li
 * @version 1.0
 * @date 2013-10-28
 */
public class DateConvert {

	/**
	 * 将带-分隔符的字符串转换为日期
	 * @param strDate
	 * @return
	 */
	public static Date toDateWithSeparate1(String strDate){
		return toDate(strDate, "yyyy-MM-dd");
	}
	
	/**
	 * 将带-分隔符的字符串转换为日期
	 * @param strDate
	 * @return
	 */
	public static Date toDateWithSeparate2(String strDate) {
		return toDate(strDate, "yyyy/MM/dd");
	}
	
	/**
	 * 将字符串转换为Java中的日期
	 * @param strDate
	 * @param formatStr
	 * @return
	 */
	public static Date toDate(String strDate,String formatStr){
		DateFormat sdf = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;		
	}
	
	 /** 
     * 转换日期格式为字符串 
     *  
     * @param date 
     *            待转换日期 
     * @return 格式为'yyyy-MM-dd'的字符串 
     */ 
    public static String toString(Date date) { 
        if (date == null) 
            return new String(); 
        return new SimpleDateFormat("yyyy-MM-dd").format(date); 
    } 

	/**
	 * 转化日期为YYYYMMDD
	 * 
	 * @param date
	 * @return
	 */
	public static String toStringNoSign(Date date) {
		if (date == null)
			return new String();
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}


}
