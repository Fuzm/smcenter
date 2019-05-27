package com.irdstudio.smcenter.core.util.finance;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 金融类数据转换辅助类
 * @author 李广民
 * @version 1.0
 * @date 2008-09-01
 */
public class TransUtil {
	
	/**
	 * 将字符串转换成利率
	 * @param ll
	 * @return
	 * @throws Exception
	 */
	public static String getLL(String ll) throws Exception{
		if ( ll.equals(""))
			throw new Exception("利率为空");
		BigDecimal big_ll = new BigDecimal(ll);
		ll = big_ll.divide(new BigDecimal("1.2"),4,BigDecimal.ROUND_HALF_UP).toString();
		return ll;
	}
	
	/**
	 * 将十位数据的日期转换成8位的不带分隔符的日期
	 * @param date
	 * @return
	 */
	public static String formatDate(String date){
		if (date.length() == 10)
			date = date.substring(0, 4) + date.substring(5, 7)
					+ date.substring(8, 10);
		return date;
	}
	
	/**
	 * 取得今天的日期
	 * @return
	 */
	public static String getToday(){
		String TimeFormat1 = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(TimeFormat1);
		return sdf.format(new Date());		
	}
}
