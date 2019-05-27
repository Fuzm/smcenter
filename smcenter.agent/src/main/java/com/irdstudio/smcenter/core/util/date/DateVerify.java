package com.irdstudio.smcenter.core.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 公共类,用于各种日期的校验(收集整理)
 * @author public
 * @version 1.0
 * @date 2014-04-22
 */
public class DateVerify {
	
	/**
	 * 判断传入的YYYY-MM-DD格式的日期
	 * 是否为末尾(年末、季末、月末、周末)
	 * @param sdate YYYY-MM-DD格式的日期
	 * @param cycleOfDate 日期周期(见常量定义)
	 * @return
	 */
	public static boolean isDateTail(String sdate, int cycleOfDate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = df.parse(sdate);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String month = sdate.substring(5, 7);

		// 判断是否年末
		if (DateConst.YEAR == cycleOfDate && month.equals("12")) {
			int lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
			int nowDay = cal.get(Calendar.DAY_OF_YEAR);
			if (nowDay == lastDayOfMonth) {
				return true;
			}
			return true;
		} else if (DateConst.SEASON == cycleOfDate
				&& (month.equals("3") || month.equals("6") || month.equals("9") || month
						.equals("12"))) {
			// 判断是否为季末
			int lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			int nowDay = cal.get(Calendar.DAY_OF_MONTH);
			if (nowDay == lastDayOfMonth) {
				return true;
			}
		} else if (DateConst.MONTH == cycleOfDate) {
			// 判断是否为月末
			int lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			int nowDay = cal.get(Calendar.DAY_OF_MONTH);
			if (nowDay == lastDayOfMonth) {
				return true;
			}
		} else if (DateConst.WEEK == cycleOfDate) {
			// 判断是否为周末
			int lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_WEEK);
			int nowDay = cal.get(Calendar.DAY_OF_WEEK);
			if (nowDay == lastDayOfMonth) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 本函数用于校验4位数的字符是否为合法的年份
	 * @param year
	 * @return
	 */
	public static boolean isYear(String year){
		int j=0;
		if(year.length() != 4){
			return false;
		}
		
		for(int i=0; i<year.length(); i++){
			if(!(year.charAt(i)>=48 && year.charAt(i)<=57)){
				return false;
			}
		}
		
		j = Integer.parseInt(year);
		
		if( j>0 && j<=9999){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 本函用于校验是否为合法的月描述,2位数
	 * @param str
	 * @return
	 */
	public static boolean isMonth(String month){	
		int j=0;
		if(month.length() != 2){
			return false;
		}
		
		for(int i=0; i<month.length(); i++){
			if(!(month.charAt(i)>=48 && month.charAt(i)<=57)){
				return false;
			}
		}
		
		j = Integer.parseInt(month);
		
		if( j>=1 && j<=12){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 本函数校验是否为合法的日 2位
	 * @param day
	 * @return
	 */
	public static boolean isDayOfMonth(String day){
		int j=0;
		if(day.length() != 2){
			return false;
		}
		
		for(int i=0; i<day.length(); i++){
			if(!(day.charAt(i)>=48 && day.charAt(i)<=57)){
				return false;
			}
		}
		
		j = Integer.parseInt(day);
		
		if( j>=1 && j<=31){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 本函用于校验是否为合法的月描述,2位数
	 * @param str
	 * @return
	 */
	public static boolean isYearMonthday(String ymd){	
		int nf = 0;
		int yf = 0;
		int rq = 0;
		int j = 0;
		
		switch(ymd.length()){
		case 4:
		case 6:
		case 8:
			break;
		default:
			return false;
		}
		
		for(int i=0; i<ymd.length(); i++){
			if(!(ymd.charAt(i)>=48 && ymd.charAt(i)<=57)){
				return false;
			}
		}
		
		j = Integer.parseInt(ymd);
		
		switch(ymd.length()){
		case 8:
			rq = j % 100;
		case 6:
			yf = (j/100) % 100;
		case 4:
			nf = j/10000;
			break;
		default:
			return false;			
		}
		//System.out.println("NF :" + nf + ",YF :" + yf + ",RQ :" + rq);
		if( nf<=0 || nf>9999)
			return false;
					
		switch(ymd.length()){
		case 6:
			if( yf<1 || yf>12){
				return false;
			}
			break;
		case 8:
			if( yf<1 || yf>12){
				return false;
			}
			switch(yf){
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				if(rq<1 || rq>31)
					return false;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				if(rq<1 || rq >30)
					return false;
				break;
			case 2:
				if((nf%4)==0 && ((nf%400)==0 || (nf%100)!=0)){
					if(rq<1 || rq>29)
						return false;
				}else{
					if(rq<1 || rq>28)
						return false;
				}
				break;			
			}
		}
		
		return true;
	}
	
	/**
	 * 本函数用于校验传进来的字符串是否为合法的日期 
	 * 首先需要校验其位数是否正确
	 * 4位是年  6位是年月  8位是年月日
	 * @param day
	 * @return
	 */
	public static boolean isDate(String szDate){
		boolean flag = true;
		switch(szDate.length()){
		case 4: flag = isYear(szDate); break;
		case 6: 
		case 8: 
			flag = isYearMonthday(szDate); 
			break;
		default:
			return false;
		}
		return flag;
	}
	
	/**
	 * 本函数用于校验传进来的字符串是否为合法的日期 
	 * 中间带-
	 * @param day
	 * @return
	 */
	public static boolean isDate2(String szDate){
		String szCvDate = szDate.replaceAll("-", "");
		return isDate(szCvDate);
	}	
	
	/**
	 * 本函数用于校验传进来的字符串是否为月的最后一天 
	 * 首先需要校验其位数是否正确
	 * 8位是年月日 10位是年-月-日
	 * @param day
	 * @return
	 */
	public static boolean isEndofMonth(String ymd){
		boolean flag = true;
		String day = null;
		switch(ymd.length()){
		case 8: 
			day = ymd;
			flag = isYearMonthday(day); 
			break;
		case 10: 
			if(ymd.charAt(4) != '-' && ymd.charAt(7) != '-')
				return false;
			day = ymd.substring(0, 4)+ymd.substring(5, 7)+ymd.substring(8, 10);
			flag = isYearMonthday(day); 
			break;
		default: 
			return false;
		}
		
		int nf = Integer.parseInt(day.substring(0, 4));
		int yf = Integer.parseInt(day.substring(4, 6));
		int rq = Integer.parseInt(day.substring(6, 8));

		int nextday = DateUtil.getDate(nf, yf, rq, 1);
		if( flag){
			if( (nextday % 100) != 1){
				flag = false;
			}
		}
		return flag;
	}
	
	public static boolean isEndofYear(String ymd){
		boolean flag = true;
		String day = null;
		switch(ymd.length()){
		case 8: 
			day = ymd;
			flag = isYearMonthday(day); 
			break;
		case 10: 
			if(ymd.charAt(4) != '-' && ymd.charAt(7) != '-')
				return false;
			day = ymd.substring(0, 4)+ymd.substring(5, 7)+ymd.substring(8, 10);
			flag = isYearMonthday(day); 
			break;
		default: 
			return false;
		}
		
		int nf = Integer.parseInt(day.substring(0, 4));
		int yf = Integer.parseInt(day.substring(4, 6));
		int rq = Integer.parseInt(day.substring(6, 8));

		int nextday = DateUtil.getDate(nf, yf, rq, 1);
		if( flag){
			if( (nextday % 10000) != 101){
				flag = false;
			}
		}
		return flag;
	}
    public static void main(String[] args){
    	System.out.println(DateVerify.isDate("20130505"));    	
    	System.out.println(DateVerify.isDate2("20130505"));    	
    	System.out.println(DateVerify.isDate2("2013-05-05"));
    	System.out.println(DateVerify.isDate2("2013/05/05"));
    	System.out.println(DateVerify.isDate2("2013-5-5"));    	
		System.out.println("2013-05-05".compareTo("2013-05-06"));
		System.out.println("2013-05-05".compareTo("2013-05-05"));
		System.out.println("2013-05-05".compareTo("2013-05-02"));		
    }	
}
