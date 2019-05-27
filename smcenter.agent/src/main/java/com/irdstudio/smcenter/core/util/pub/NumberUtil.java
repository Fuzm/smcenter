package com.irdstudio.smcenter.core.util.pub;
/**
 * 简单数字处理类
 * @author cky
 * @version 1.0
 */
public class NumberUtil {
  public static double parseDouble(String num){

    if(num==null||num.trim().equals("null")||num.trim().equals("")){
      return 0;
    }else{
      return Double.parseDouble(num);
    }
  }
  public static int parseInt(String num){
    if (num==null||num.trim().equals("null")||num.trim().equals("")) {
      return 0;
    }
    else {
     return  Integer.parseInt(num);
    }
  }
  public static double parseDouble(String num, double defaultValue) {
	  try{
		if (num == null || num.trim().equals("null") || num.trim().equals("")) {
			return 0;
		} else {
			return Double.parseDouble(num);
		}
	  }
	  catch(Exception e){
	  		return defaultValue;
	  } 
	}
  	
}
