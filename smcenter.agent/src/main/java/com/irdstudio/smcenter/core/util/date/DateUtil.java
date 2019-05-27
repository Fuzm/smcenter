package com.irdstudio.smcenter.core.util.date;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期辅助类
 * @author everyone
 * @version 1.0
 * @date 2007-10-26
 *
 */
public class DateUtil {

	public static List<?> getBetweenDate(String beginDate,String endDate){
		List<Integer> dates = new ArrayList<Integer>();
		if(beginDate.length() != endDate.length()){
			return null;
		}
		
		switch(beginDate.length()){
			case 4: dates = getBetweenDate4(beginDate,endDate); break;
			case 6: dates = getBetweenDate6(beginDate,endDate); break;
			case 8: dates = getBetweenDate8(beginDate,endDate); break;
			default: return null;
		}
		
		return dates;
	}
	
	public static List<Integer> getBetweenDate4(String beginDate,String endDate){  
		List<Integer> betweendays = new ArrayList<Integer>();
		int beginrq = Integer.parseInt(beginDate);
		int endrq = Integer.parseInt(endDate);
		int between = 0;
		for(int i=0;i <= endrq - beginrq; i++){
			between = beginrq + i;
			betweendays.add(new Integer(between));
		}
		return betweendays;
	}
	
	public static List<Integer> getBetweenDate6(String beginDate,String endDate){
		List<Integer> betweendays = new ArrayList<Integer>();
		int beginrq = Integer.parseInt(beginDate);
		int endrq = Integer.parseInt(endDate);
		int between = 0;
		int yf1 = beginrq % 100;
		int nf1 = (int)(beginrq / 100);
		int yf2 = endrq % 100;
		int nf2 = (int)(endrq / 100);
		for(int i=0;i<=(nf2-nf1)*12-(yf2-yf1); i++){
			between = (nf1 + (yf1 + i)/12)*100;
			if((yf1+i)%12 == 0)
				between += 12;
			else
				between += (yf1 + (yf1 + i)%12);
			betweendays.add(new Integer(between));
		}
		return betweendays;
	}
	
	public static List<Integer> getBetweenDate8(String beginDate,String endDate){
		List<Integer> betweendays = new ArrayList<Integer>();
		int beginrq = Integer.parseInt(beginDate);
		int endrq = Integer.parseInt(endDate);
		int between = 0;
		int yf1 = (beginrq /100) % 100;
		int nf1 = beginrq / 10000;
		int rq1 = beginrq % 100;
		int yf2 = (endrq /100) % 100;
		int nf2 = endrq / 10000;
		int rq2 = endrq % 100;
		for(int i=0;i<=((nf2-nf1)*12-(yf2-yf1))*31+(rq2-rq1); i++){
			between = DateUtil.getDate(nf1, yf1, rq1, i);
/*			between = (nf1 + (rq1 + i - 1)/(31*12))*10000;
			if((yf1 + ((rq1 + i - 1)/31)%12)== 0)
				between += 12*100; 
			else
				between += (yf1 + ((rq1 + i - 1)/31)%12)*100;
			
			if((rq1 + i)%31 == 0)
				between += 31;
			else
				between += (rq1 + i)%31;*/
			betweendays.add(new Integer(between));
		}
		return betweendays;
	}
	
	public static int getDate( int nf, int yf, int rq, int relaValue){
		boolean rn = false;
		boolean flag = true;
		boolean bz = true;
		int date = 0;
		int days = 0;
		
	    //判断是否为闰年2月29日
		if(((nf%4)==0 && ((nf%400)==0 || (nf%100)!=0)) && yf==2 && rq==29)
			rn = true;
		
    	rq += relaValue;

    	//循环对月份及日期超出范围时进行相应修改
    	while(flag){
	    	if( yf > 12){
	    		yf -= 12;
	    		nf += 1;
	    		continue;
	    	}else if( yf < 1){
	    		yf += 12;
	    		nf -= 1;
	    		continue;
	    	}
	    	
	    	/*
	    	 * 公元纪年的年数可以被四整除，即为闰年；
	    	 * 被100整除而不能被400整除为平年；
	    	 * 被100整除也可被400整除的为闰年。
	    	 */
	    	switch(yf){
	    	case 1:
	    	case 3:
	    	case 5:
	    	case 7:
	    	case 8:
	    	case 10:
	    	case 12:
	    		days = 31;
	    		break;
	    	case 4:
	    	case 6:
	    	case 9:
	    	case 11:
	    		days = 30;
	    		break;
	    	case 2:
	    		if((nf%4)==0 && ((nf%400)==0 || (nf%100)!=0))
	    			days = 29;
	    		else
	    			days = 28;
	    		break;
	    	}
	    	
	    	/*
	    	 * 当日期大于当月日期时，要减去当月的天数，月份加1
	    	 * 当日期小于当月日期时，要加上上月的天数，月份减1
	    	*/
	    	if(rq > days){
    			rq -= days;
    			yf += 1;
    			if(rn)
    				rq -= 1;
    			continue;
    		}else if(rq < 1){
    			if(bz){
    				rq += days;
    				bz = false;
    				continue;
    			}else{
    				yf -= 1;
    				bz = true;
    				continue;
    			}
    		}
	    	if(!bz){
	    		yf -= 1;
	    		bz = true;
	    		continue;
	    	}
	    	flag = false;
	    }
	    
	    if( nf < 1900){
			System.out.println("年份数值太小: " + nf);
			return 0;
	    }else if( nf > 9999){
			System.out.println("年份数值太大: " + nf);
			return 0;
	    }
	    
    	date = nf*10000 + yf*100 + rq;
    	
		return date;
	}
	
	/**
	 * 获取两个日期之间的天数
	 * @param start		起始日期
	 * @param end		结束日期
	 * @param isShort	是否使用短格式,如 2007年02月28日为: "070228" true采用短格式，false不采用短格式
	 * 				缺省时使用长格式,如 "20070228",两个日期格式相同
	 * @return		要求起始日期比结束日期要早,否则,将返回 -1
	 */
	public static int getDaysBetween(String start, String end, boolean isShort) {
		String startDate = DateUtil.formatDate(start, isShort);
		String endDate = DateUtil.formatDate(end, isShort);

		Calendar calStart = null;
		Calendar calEnd = null;
		if(startDate!=null && endDate!=null){
			calStart=new GregorianCalendar(getYear(startDate, false),
				getMonth(startDate, false) - 1,
				getDay(startDate, false));
			calEnd=new GregorianCalendar(getYear(endDate, false),
				getMonth(endDate, false) - 1,
				getDay(endDate, false));
			long lngStart = calStart.getTimeInMillis();
			long lngEnd = calEnd.getTimeInMillis();
			if (lngStart > lngEnd) {
				return -1;
			}
			int nDay = (int) ( (lngEnd - lngStart) / (24 * 60 * 60 * 1000));
			return nDay;
		}
		return -1;
	}
	/**
	 * 将指定日期转换为日历中合法的日期
	 * 如:	2007年2月29日将转换为 2007年3月1日
	 * 		包括将 2008年0月12日转换为2008年1月12日,
	 * 		但不包括 将 2008年13月10日转换为2009年1月10日的情况
	 * @param date		采用长格式 "20070212" 或短格式 "070212" 的字符串形式,
	 * 		注:短格式缺省年份从1900年计起,若表示 2000年后的年份,请使用长格式
	 * @param isShort	是否采用短格式
	 * @return	返回合法的长格式日期
	 */
	public static String formatDate(String date, boolean isShort) {
		if (!isShort && (date==null || date.length() != 8)) { //长格式
			return null;
		}
		if (isShort && (date==null || date.length() != 6)) { //短格式
			return null;
		}

		String res = null;
		Calendar calendar = null;
		int nYear = 0, nMonth = 0, nDay = 0;

		if (!isShort) { //长格式
			nYear = Integer.parseInt(date.substring(0, 4));
			nMonth = Integer.parseInt(date.substring(4, 6));
			nDay = Integer.parseInt(date.substring(6, date.length()));
			if (nMonth == 0) {
				calendar = new GregorianCalendar(nYear, nMonth, nDay);
			}
			else {
				calendar = new GregorianCalendar(nYear, nMonth - 1, nDay);
			}
		}
		else { //短格式
			nYear = Integer.parseInt(date.substring(0, 2));
			nMonth = Integer.parseInt(date.substring(2, 4));
			nDay = Integer.parseInt(date.substring(4, date.length()));
			if (nMonth == 0) {
				calendar = new GregorianCalendar(nYear + 1900, nMonth, nDay);
			}
			else {
				calendar = new GregorianCalendar(nYear + 1900, nMonth - 1, nDay);
			}
		}
		res = new Integer(calendar.get(Calendar.YEAR)).toString();

		nMonth = calendar.get(Calendar.MONTH) + 1;
		if (nMonth < 10) {
			res = res + "0" + new Integer(calendar.get(Calendar.MONTH) + 1).toString();
		}
		else {
			res = res + new Integer(calendar.get(Calendar.MONTH) + 1).toString();

		}
		nDay = calendar.get(Calendar.DAY_OF_MONTH);
		if (nDay < 10) {
			res = res + "0" +
			new Integer(calendar.get(Calendar.DAY_OF_MONTH)).toString();
		}
		else {
			res = res + new Integer(calendar.get(Calendar.DAY_OF_MONTH)).toString();
			//System.out.println(calendar);
		}
		return res;
	}
	/**
	 * 使用 formatStr 格式串将当前时间 currentTimeMillis 格式化
	 * @param formatStr	格式串
	 * @return	返回格式化后的字符串
	 * 	如果formatStr格式串为空,则使用"yyyy-MM-dd HH:mm:ss"格式串
	 */
	public static String timeMillis2Format(String formatStr) {
		String reg =
			"^((yyyy年MM月dd日)|(yyyy\\-MM\\-dd)|(yyyy/MM/dd)|(MM/dd/yyyy)|(MM/dd,yyyy)|(yyyyMMdd)){1}(\\s{1}HH:mm:ss){0,1}";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(formatStr);
		String res = "0000-00-00 00:00:00";
		long timeMillis = System.currentTimeMillis();
		if (formatStr == null || "".equals(formatStr) || !matcher.matches()) {
			formatStr = "yyyy-MM-dd HH:mm:ss";
		}
		Date date = new Date(timeMillis);
		DateFormat dateFormat = new SimpleDateFormat(formatStr);

		res = dateFormat.format(date);
		return res;
	}
	/**
	 * 获取日期字符串中的年份值
	 * @param date
	 * @param isShort
	 * @return
	 */
	public  static int getYear(String date, boolean isShort) {
		date = DateUtil.formatDate(date, isShort);
		int nYear = Integer.parseInt(date.substring(0, 4));
		return nYear;
	}
	/**
	 * 获取日期字符串中的月份值
	 * @param date
	 * @param isShort
	 * @return
	 */
	public static int getMonth(String date, boolean isShort) {
		date = DateUtil.formatDate(date, isShort);
		int nMonth = Integer.parseInt(date.substring(4, 6));
		return nMonth;
	}

	/**
	 * 获取日期字符串中的日期(一月中第几天)值
	 * @param date
	 * @param isShort
	 * @return
	 */
	public static int getDay(String date, boolean isShort) {
		date = DateUtil.formatDate(date, isShort);
		int nDay = Integer.parseInt(date.substring(6, date.length()));
		return nDay;
	}

	public static String convertToShort(String date) {
		return date.replaceAll("-", "");
	}
	
	/**
	 * 将字符串类型的日期转换为一个Date（java.util.Date）
	 * 
	 * @param dateString
	 *            需要转换为Date的字符串
	 * @return dataTime Date
	 */
	public final static java.util.Date string2UtilDateYMD(String dateString)
			throws java.text.ParseException {
		DateFormat dateFormat;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		dateFormat.setLenient(false);
		java.util.Date timeDate = dateFormat.parse(dateString);// util类型
		return timeDate;
	}
	
	/**
	 * 
	 * 根据开始日期,期限,期限类型计算结束日期
	 * @param  String 开始日期 yyyy-MM-dd(必传)
	 * @param  BigDecimal 期限(必传)
	 * @param  String 期限类型 D-天 M-月 Y-年(必传)
	 * @return String 结束日期 yyyy-MM-dd
	 * @throws Exception 异常
	 */
	public final static String calculateEndDate(String startDate_str, BigDecimal term, String type) throws Exception{
		if(startDate_str == null || term == null || type == null || startDate_str.equals("") || type.equals("")){
			return "时间计算所需参数为空，请检查";
		}
		String endDate_str = "";//结束日期字符串
		Date startDate = DateUtil.string2UtilDateYMD(startDate_str);//开始日期Date类型
		Calendar startDate_cal = Calendar.getInstance();//开始日期Calendar类型
		startDate_cal.setTime(startDate);
		int term_int = term.intValue();
		if(type.equals("D")){
			startDate_cal.add(Calendar.DATE, term_int);
		} else if(type.equals("M")){
			startDate_cal.add(Calendar.MONTH, term_int);
		} else if(type.equals("Y")){
			startDate_cal.add(Calendar.YEAR, term_int);
		}
		Date endDate = startDate_cal.getTime();
		endDate_str = DateUtil.formatDate2Str(endDate, "yyyy-MM-dd");
		
		return endDate_str;
	}
	
	/**
	 * 格式化日期 (格式:yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param inDate
	 *            要格式化的日期
	 * @return 格式化后的日期
	 */
	public final static String formatDate2Str(Date inDate) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(inDate);
	}
	
	/**
	 * 根据指定都日期格式格式化日期 格式化日期 (格式:yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param inDate
	 *            要格式化的日期
	 * @return 格式化后的日期
	 */
	public final static String formatDate2Str(Date inDate, String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(inDate);
	}
	
	public static void main(String args[]){
		String str=DateUtil.timeMillis2Format("yyyyMMdd");
		System.err.println(str);
		int i=DateUtil.getDaysBetween("20080714", "20080713", false);
		System.err.println(i);
	}
}
