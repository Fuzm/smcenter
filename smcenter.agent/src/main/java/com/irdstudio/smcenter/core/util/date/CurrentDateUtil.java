package com.irdstudio.smcenter.core.util.date;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * 以当前日期为参考取得相应值的函数集
 * @author admin
 * @version 1.0
 * @date 2007-10-4
 */
public class CurrentDateUtil {
	
	
	/**
	 * 取得当天日期,带-分隔符
	 * @return
	 */
	public static String getTodayDate() {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar
				.getInstance().getTime());
		return date;
	}
	
	/**
	 * 获取当前时间,精确到秒
	 * @return
	 */
	public static String getTodayDateEx(){
		String date = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar
				.getInstance().getTime());
		return date;
	}
	
	/**
	 * 获取当前时间,精确到秒,带分隔符
	 * @return
	 */
	public static String getTodayDateEx2(){
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar
				.getInstance().getTime());
		return date;
	}
	
	/**
	 * 获取当前时间,精确到毫秒(线程安全)
	 * @return
	 */
	public synchronized static String getTodayDateEx3() {
		String date = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(Calendar
				.getInstance().getTime());
		return date;
	}
	
	/**
	 * 获取当前时间,精确到毫秒(线程安全)
	 * @return
	 */
	public synchronized static String getTodayDateEx4() {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS")
				.format(Calendar.getInstance().getTime());
		return date;
	}	
	
	/**
	 * 返回当前时间
	 * @return
	 */
	public static String getCurrentTime(){
		String time = new SimpleDateFormat("HH:mm:ss").format(Calendar
				.getInstance().getTime());
		return time;
	}
	
	/**
	 * 获取当前年,格式2007
	 * @return
	 */
	public static String getYear(){
	    String date=new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime()); 
	    //System.out.println("Today is " + date); 
		return date;
	}
	/**
	 * 获取当前年月,格式YYYYMM
	 * @return
	 */
	public static String getYearMonth(){
	    String date=new SimpleDateFormat("yyyyMM").format(Calendar.getInstance().getTime()); 
		return date;
	}
	
	/**
	 * 获取当前年月日,格式YYYYMMDDD
	 * @return
	 */
	public static String getYearMonthDay(){
	    String date=new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()); 
		return date;
	}
	
	/**
	 * 获取去年,格式YYYY
	 * @return
	 */
	public static String getFrontYear(){
	    NumberFormat yy = NumberFormat.getNumberInstance();
	    yy.setGroupingUsed(false);
	    Calendar cal = Calendar.getInstance();
	    int nf=cal.get(Calendar.YEAR);
	    nf -= 1;
	    String mydate=yy.format(nf);

		return mydate;
	}
	
	/**
	 * 获取去年年月,格式YYYYMM,
	 * @return
	 */
	public static String getFrontYm(){
	    NumberFormat yy = NumberFormat.getNumberInstance();
	    yy.setGroupingUsed(false);
	    Calendar cal = Calendar.getInstance();
	    int nf=cal.get(Calendar.YEAR);
	    int yf=cal.get(Calendar.MONTH);
	    nf -= 1;
	    yf += 1;
	    String mydate=yy.format(nf*100 + yf);

		return mydate;
	}
	
	/**
	 * 获取去年年月日,格式YYYYMMDD
	 * @return
	 */
	public static String getFrontYmd(){
		/*
	    NumberFormat yy = NumberFormat.getNumberInstance();
	    yy.setGroupingUsed(false);
	    Calendar cal = Calendar.getInstance();
	    Integer nf=cal.get(Calendar.YEAR);
	    Integer yf=cal.get(Calendar.MONTH);
	    Integer rq=cal.get(Calendar.DATE);
	    nf -= 1;
	    yf += 1;
	    String mydate=yy.format(nf*10000 + yf*100 + rq);
	    */

		String mydate=CurrentDateUtil.getRelaDate(DateConst.YEAR, -1, DateConst.YYYYMMDD);	
		
		return mydate;
	}
	
	/**
	 * 获取相对值,第一句为指定相应日期的类型
	 * 例如:类型为"天",relaValue为-2,即表示要取前两天是是那一天
	 * 例如:类型为"年",relaValue为3,表示取当前年往后的第三年的日期
	 * 此方法可作为上述几个函数的基础函数
	 * 类型及返回值格式参考DateConst类
	 * @param type
	 * @param relaValue
	 * @parm dateFormat 指定返回日期的格式
	 * @return
	 */
	public static String getRelaDate(int dateType,int relaValue,String dateFormat){
	    //String date=new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()); 
		String mydate = new String();
		boolean flag = true;
		boolean bz = true;
		boolean rn = false;
		int date = 0;
		int dd = 0;
		
		
		//取当前年月日
		NumberFormat yy = NumberFormat.getNumberInstance();
	    yy.setGroupingUsed(false);
	    Calendar cal = Calendar.getInstance();
	    int nf=cal.get(Calendar.YEAR);
	    int yf=cal.get(Calendar.MONTH);
	    int rq=cal.get(Calendar.DATE);
	    yf += 1;
	    
	    //判断是否为闰年2月29日
		if(((nf%4)==0 && ((nf%400)==0 || (nf%100)!=0)) && yf==2 && rq==29)
			rn = true;
		
	    //根据日期类型，对当前日期进行加减
    	if(dateType == DateConst.DAY){
    		rq += relaValue;
    	}else if(dateType == DateConst.MONTH){
    		yf += relaValue;
    	}else if(dateType == DateConst.YEAR){
    		nf += relaValue;
    	}else{
    		System.out.println("无此日期类型: " + dateType);
    		return null;
    	}

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
	    		dd = 31;
	    		break;
	    	case 4:
	    	case 6:
	    	case 9:
	    	case 11:
	    		dd = 30;
	    		break;
	    	case 2:
	    		if((nf%4)==0 && ((nf%400)==0 || (nf%100)!=0))
	    			dd = 29;
	    		else
	    			dd = 28;
	    		break;
	    	}
	    	
	    	/*
	    	 * 当日期大于当月日期时，要减去当月的天数，月份加1
	    	 * 当日期小于当月日期时，要加上上月的天数，月份减1
	    	*/
	    	if(rq > dd){
    			rq -= dd;
    			yf += 1;
    			if(rn)
    				rq -= 1;
    			continue;
    		}else if(rq < 1){
    			if(bz){
    				rq += dd;
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
			return null;
	    }else if( nf > 9999){
			System.out.println("年份数值太大: " + nf);
			return null;
	    }
	    
	    //根据日期格式进行相应的赋值
	    if(dateFormat == DateConst.YYYY){
	    	date = nf;
	    }else if(dateFormat == DateConst.YYYYMM){
	    	date = nf*100 + yf;
	    }else if(dateFormat == DateConst.YYYYMMDD){
	    	date = nf*10000 + yf*100 + rq;
	    }else{
			System.out.println("无此日期格式: " + dateFormat);
			return null;
	    }
	    
	    mydate=yy.format(date);
		return mydate;
	}
	
	public static void main(String[] args){
		String nextDate = getRelaDate(2,1,"YYYYMMDD");
		StringBuffer sb = new StringBuffer();
		sb.append(nextDate.substring(0,4));
		sb.append("-");
		sb.append(nextDate.substring(4,6));
		sb.append("-");
		sb.append(nextDate.substring(6));
		System.err.print(sb);
	}
}
