package com.irdstudio.smcenter.core.util.pub;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public final class DecimalUtil {
  private static BigDecimal one = new BigDecimal("1");

  public DecimalUtil() {
  }


  /**
  * 按参数设定保留小数，不四舍五入
  * @param db double 原始数据
  * @param saveDigit 保留位数
  * @return double
  * zjb
  */
  public static double round(double db,int saveDigit){
    if(saveDigit < 0) saveDigit =0;

    String strZero = "";
    java.text.DecimalFormat df = null;
    for(;saveDigit >0;saveDigit--){
      strZero +="0";
    }
    if(strZero.length()<1){
      df = new java.text.DecimalFormat("####");
    }
    else{
      df = new java.text.DecimalFormat("####."+strZero);
    }

    double db_ret;
    try{
        db_ret = Double.parseDouble(df.format(db));
    }catch(Exception e){
        e.printStackTrace();
        db_ret = db;
    }
    return db_ret;
  }

  /**
* 提供精确的小数位四舍五入处理。
* @param v 需要四舍五入的数字
* @param scale 小数点后保留几位
* @return 四舍五入后的结果
*/
public static float round(float v,int scale){
  if(scale<0){
     throw new IllegalArgumentException("The scale must be a positive integer or zero");
  }
  BigDecimal b = new BigDecimal(Float.toString(v));
  //BigDecimal one = new BigDecimal("1");
  return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).floatValue();
}



/**
 * 提供精确的小数位四舍五入处理。
 * @param v 需要四舍五入的数字
 * @param scale 小数点后保留几位,为0时取整
 * @return 四舍五入后的结果 zjb
 */
public static String round(String v, int scale) {
  if (scale < 0) {
    throw new IllegalArgumentException(
        "The scale must be a positive integer or zero");
  }
  BigDecimal b = new BigDecimal(v);
  if(scale ==0){
    return String.valueOf(b.divide(one, scale, BigDecimal.ROUND_HALF_UP).intValue());
  }
  else{
    String format = ".";
    for (int i = 0; i < scale; i++) {
        format += "0";
    }
    return (new DecimalFormat("####" + format).format(b.divide(one, scale,
        BigDecimal.ROUND_HALF_UP))).toString();
  }
}

public static void main(String[] args){
 System.err.print(DecimalUtil.round("+1232.086",2));
}

}
