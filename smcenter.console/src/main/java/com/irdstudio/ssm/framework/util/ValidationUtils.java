/**
 * 
 */
package com.irdstudio.ssm.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.irdstudio.ssm.framework.constant.Sex;

/**
 * <p>Project:			<p>
 * <p>Module:			<p>
 * <p>Description:	数据验证工具�?	<p>
 *
 * @author wangjx
 * @date 2016�?6�?14�? 上午9:59:28
 */
public class ValidationUtils {

	/** 
	* Email正则表达�?
	*/
	public static final String EMAIL = "\\w+(\\.\\w+)*@\\w+(\\.\\w+)+";

	/** 
	 * 电话号码正则表达�?
	 */
	public static final String PHONE = "(^(\\d{2,4}[-_－�?�]?)?\\d{3,8}([-_－�?�]?\\d{3,8})?([-_－�?�]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";

	/** 
	 * 手机号码正则表达�?
	 */
	public static final String MOBILE = "^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\\d{8}$";

	/** 
	 * 正整数正则表达式 
	 */
	public static final String INTEGER_NEGATIVE = "^[1-9]\\d*|0$|^[1-9]\\d*\\.0+?";

	/** 
	 * 负整数正则表达式 
	 */
	public static final String INTEGER_POSITIVE = "^-[1-9]\\d*|0$";

	/** 
	 * 邮编正则表达�?  [0-9]\d{5}(?!\d) 国内6位邮�? 
	 */
	public static final String CODE = "[0-9]\\d{5}(?!\\d)";

	/** 
	 * 过滤特殊字符串正�? 
	 *
	 */
	public static final String STR_SPECIAL = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#�?%…�??&*（）—�??+|{}【�?��?�；：�?��?��?��?�，、？]";

	/*** 
	 * 日期正则 支持�? 
	 *  YYYY-MM-DD  
	 */
	public static final String DATE_FORMAT1 = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";

	/** 
	 * URL正则表达�? 
	  * 匹配 http www ftp 
	 */
	public static final String URL = "^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?" + "(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*" + "(\\w*:)*(\\w*\\+)*(\\w*\\.)*" + "(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$";

	/**
	 * 机构代码
	 */
	public static final String ORG_CODE = "^[A-Z0-9]{8}-[A-Z0-9]$";

	/**
	 * 匹配数字组成的字符串  ^[0-9]+$ 
	 */
	public static final String STR_NUM = "^[0-9]+$";

	/**
	 * 金额类型
	 */
	public static final String CURRENCY = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$";
	
	/** 小数类型*/
	//public static final String DECIMALS_NUM = "([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])";
	public static final String DECIMALS_NUM = "([1-9]\\d*\\.?\\d*)|(0\\.\\d*)";
	// //------------------验证方法


	/**
	 * 
	 * @Description 判断时候为小数
	 * @param str
	 * @return
	 */
	public static boolean isDecimalsNum( String str ) {
		return regular( str, DECIMALS_NUM );
	}
	
	/** 
	 * 字符串null赋�?�默认�??  
	 * @param str    目标字符�? 
	 * @param defaut 默认�? 
	 * @return String 
	 */
	public static String nulltoStr( String str, String defaut ) {
		return StringUtils.isEmpty( str ) ? defaut : str;
	}

	/** 
	 * 判断字段是否为Email 符合返回ture 
	 * @param str 
	 * @return boolean 
	 */
	public static boolean isEmail( String str ) {
		return regular( str, EMAIL );
	}

	/** 
	 * 判断是否为电话号�? 符合返回ture 
	 * @param str 
	 * @return boolean 
	 */
	public static boolean isPhone( String str ) {
		return regular( str, PHONE );
	}

	/** 
	 * 判断是否为手机号�? 符合返回ture 
	 * @param str 
	 * @return boolean 
	 */
	public static boolean isMobile( String str ) {
		return regular( str, MOBILE );
	}

	/** 
	 * 判断是否为Url 符合返回ture 
	 * @param str 
	 * @return boolean 
	 */
	public static boolean isUrl( String str ) {
		return regular( str, URL );
	}

	/** 
	 * 判断字段是否为正整数正则表达�? >=0 符合返回ture 
	 * @param str 
	 * @return boolean 
	 */
	public static boolean isINTEGER_NEGATIVE( String str ) {
		return regular( str, INTEGER_NEGATIVE );
	}

	/** 
	 * 判断字段是否为负整数正则表达�? <=0 符合返回ture 
	 * @param str 
	 * @return boolean 
	 */
	public static boolean isINTEGER_POSITIVE( String str ) {
		return regular( str, INTEGER_POSITIVE );
	}

	/**
	 * 验证2010-12-10
	 * @param str
	 * @return
	 */
	public static boolean isDate1( String str ) {
		return regular( str, DATE_FORMAT1 );
	}

	/** 
	 * 判断字段是否超长 
	 * 字串为空返回fasle, 超过长度{leng}返回ture 反之返回false 
	 * @param str 
	 * @param leng 
	 * @return boolean 
	 */
	public static boolean isLengOut( String str, int leng ) {
		return StringUtils.isEmpty( str ) ? false : str.trim().length() > leng;
	}

	/** 
	 * 判断字段是否为邮�? 符合返回ture 
	 * @param str 
	 * @return boolean 
	 */
	public static boolean isCode( String str ) {
		return regular( str, CODE );
	}

	/** 
	 * 过滤特殊字符�? 返回过滤后的字符�? 
	 * @param str 
	 * @return boolean 
	 */
	public static String filterStr( String str ) {
		Pattern p = Pattern.compile( STR_SPECIAL );
		Matcher m = p.matcher( str );
		return m.replaceAll( "" ).trim();
	}

	/**
	 * 校验机构代码格式
	 * @return
	 */
	public static boolean isOrgCode( String str ) {
		return regular( str, ORG_CODE );
	}

	/** 
	 * 判断字符串是不是数字组成 
	 * @param str 
	 * @return boolean 
	 */
	public static boolean isStrNUM( String str ) {
		return regular( str, STR_NUM );
	}

	/** 
	 * 判断是否为金额类�?
	 * @param str 
	 * @return boolean 
	 */
	public static boolean isCurrency( String str ) {
		return regular( str, CURRENCY );
	}

	/** 
	 * 匹配是否符合正则表达式pattern 匹配返回true 
	 * @param str 匹配的字符串 
	 * @param pattern 匹配模式 
	 * @return boolean 
	 */
	private static boolean regular( String str, String pattern ) {
		if( null == str || str.trim().length() <= 0 )
			return false;
		Pattern p = Pattern.compile( pattern );
		Matcher m = p.matcher( str );
		return m.matches();
	}
	

	
	private final static String SUCCESS = "true";

	private final static String BAD_LENGTH = "银行卡号长度必须在16到19之间";

	private final static String NOT_NUMBER = "银行卡必须全部为数字";

	private final static String ILLEGAL_NUMBER = "银行卡不符合规则";
	
	private final static String strBin ="10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99,90";
	/**
	 * 功能：判断字符串是否为数字
	 */
	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 验证日期字符串是否是YYYY-MM-DD格式
	 */
	public static boolean isDataFormat(String str) {
		boolean flag = false;
		String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern pattern1 = Pattern.compile(regxStr);
		Matcher isNo = pattern1.matcher(str);
		if (isNo.matches()) {
			flag = true;
		}
		return flag;
	}
	
	
	/**
	 * 功能：设置地区编码
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Hashtable getAreaCode() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	@SuppressWarnings("rawtypes")
	public static boolean idCardValidate(String IDStr) throws ParseException, NumberFormatException, java.text.ParseException {
		IDStr = IDStr.trim().toUpperCase();
		String[] ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			//身份证号码长度应该为15位或18位
			return false;
		}
		// =======================(end)========================

		// ================ 数字 除最后一位都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			//身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。
			return false;
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
			//身份证生日无效。
			return false;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
				|| (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
			//身份证生日不在有效范围。
			return false;
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			//身份证月份无效
			return false;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			//身份证日期无效
			return false;
		}
		//二月的生日检验
		//1.能被400整除的年份；
		//2.能被4整除但同时不能被100整除的年份。
		
		if("02".equals(strMonth)){
			if(Integer.parseInt(strYear)%400==0){
				if (Integer.parseInt(strDay) > 30 || Integer.parseInt(strDay) == 0) {
					//身份证日期无效
					return false;
				}
			}else{
				if (Integer.parseInt(strDay) > 29 || Integer.parseInt(strDay) == 0) {
					//身份证日期无效
					return false;
				}
			}
			
			if(Integer.parseInt(strYear)%4==0){
				if(Integer.parseInt(strYear)%100!=0){
					if (Integer.parseInt(strDay) > 30 || Integer.parseInt(strDay) == 0) {
						//身份证日期无效
						return false;
					}
				}else{
					if (Integer.parseInt(strDay) > 29 || Integer.parseInt(strDay) == 0) {
						//身份证日期无效
						return false;
					}
				}
			}
		}
		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		Hashtable h = getAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			//身份证地区编码错误。
			return false;
		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				//身份证无效，不是合法的身份证号码
				return false;
			}
		} else {
			return true;
		}
		// =====================(end)=====================
		return true;
	}



	/**
	 * @param checkType 校验类型：0校验手机号码，1校验座机号码，2两者都校验满足其一就可
	 * @param phoneNum
	 * */
	public static boolean validPhoneNum(String checkType,String phoneNum){
		boolean flag=false;
		Pattern p1 = null;
		Pattern p2 = null;
		Matcher m = null;
		p1 = Pattern.compile("^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\\d{8})?$");
		p2 = Pattern.compile("^(0[0-9]{2,3}\\-)?([1-9][0-9]{6,7})$");
		if("0".equals(checkType)){
			System.out.println(phoneNum.length());
			if(phoneNum.length()!=11){
				return false;
			}else{
				m = p1.matcher(phoneNum);
				flag = m.matches();
			}
		}else if("1".equals(checkType)){
			if(phoneNum.length()<11||phoneNum.length()>=16){
				return false;
			}else{
				m = p2.matcher(phoneNum);
				flag = m.matches();
			}
		}else if("2".equals(checkType)){
			if(!((phoneNum.length() == 11 && p1.matcher(phoneNum).matches())||(phoneNum.length()<16&&p2.matcher(phoneNum).matches()))){
				return false;
			}else{
				flag = true;
			}
		}
		return flag;
	}
		/**银行卡号的验证
		 Description: 银行卡号Luhm校验,检验数字算法（Luhn Check Digit Algorithm），也叫做模数10公式，是一种简单的算法，用于验证银行卡、信用卡号码的有效性的算法。
		 Luhm校验规则：16位银行卡号（19位通用）:
		 1.将未带校验位的 15（或18）位卡号从右依次编号 1 到 15（18），位于奇数位号上的数字乘以 2。
		 2.将奇位乘积的个十位全部相加，再加上所有偶数位上的数字。
		 3.将加法和加上校验位能被 10 整除。
		
		 开头6位
		 	private final static String strBin ="10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";
		 * 
		 */
	public static String luhmCheck(String bankno) {
		
		if (bankno.length() < 16 || bankno.length() > 19) {
		return BAD_LENGTH;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher match = pattern.matcher(bankno);
		if (match.matches() == false) {
			return NOT_NUMBER;
		}
		if (strBin.indexOf(bankno.substring(0, 2)) == -1) {
			return "银行卡号开头6位不符合规范";
		}
		int lastNum = Integer.parseInt(bankno.substring(bankno.length() - 1,bankno.length()));// 取出最后一位（与luhm进行比较）
		String first15Num = bankno.substring(0, bankno.length() - 1);// 前15或18位
		// System.out.println(first15Num);
		char[] newArr = new char[first15Num.length()]; // 倒叙装入newArr
		char[] tempArr = first15Num.toCharArray();
		for (int i = 0; i < tempArr.length; i++) {
			newArr[tempArr.length - 1 - i] = tempArr[i];
		}
		int[] arrSingleNum = new int[newArr.length]; // 奇数位*2的积 <9
		int[] arrSingleNum2 = new int[newArr.length];// 奇数位*2的积 >9
		int[] arrDoubleNum = new int[newArr.length]; // 偶数位数组
		
		for (int j = 0; j < newArr.length; j++) {
			if ((j + 1) % 2 == 1) {// 奇数位
			if ((int) (newArr[j] - 48) * 2 < 9)
			arrSingleNum[j] = (int) (newArr[j] - 48) * 2;
			else
			arrSingleNum2[j] = (int) (newArr[j]- 48) * 2;
			} else
			// 偶数位
			arrDoubleNum[j] = (int) (newArr[j] - 48);
		}
		int[] arrSingleNumChild = new int[newArr.length]; // 奇数位*2 >9
		// 的分割之后的数组个位数
		int[] arrSingleNum2Child = new int[newArr.length];// 奇数位*2 >9
		// 的分割之后的数组十位数
		
		
		for (int h = 0; h < arrSingleNum2.length; h++) {
			arrSingleNumChild[h] = (arrSingleNum2[h]) % 10;
			arrSingleNum2Child[h] = (arrSingleNum2[h]) / 10;
		}
	
		int sumSingleNum = 0; // 奇数位*2 < 9 的数组之和
		int sumDoubleNum = 0; // 偶数位数组之和
		int sumSingleNumChild = 0; // 奇数位*2 >9 的分割之后的数组个位数之和
		int sumSingleNum2Child = 0; // 奇数位*2 >9 的分割之后的数组十位数之和
		int sumTotal = 0;
		for (int m = 0; m < arrSingleNum.length; m++) {
			sumSingleNum = sumSingleNum + arrSingleNum[m];
		}
		
		
		for (int n = 0; n < arrDoubleNum.length; n++) {
			sumDoubleNum = sumDoubleNum + arrDoubleNum[n];
		}
		
		
		for (int p = 0; p < arrSingleNumChild.length; p++) {
			sumSingleNumChild = sumSingleNumChild + arrSingleNumChild[p];
			sumSingleNum2Child = sumSingleNum2Child + arrSingleNum2Child[p];
		}
		
		
		sumTotal = sumSingleNum + sumDoubleNum + sumSingleNumChild
		+ sumSingleNum2Child;
		
		
		// 计算Luhm值
		int k = sumTotal % 10 == 0 ? 10 : sumTotal % 10;
		int luhm = 10 - k;
		
		
		if(lastNum==luhm) {
			return SUCCESS;// 验证通过
		} else {
			return ILLEGAL_NUMBER;//验证失败
		}
	}
				

    /**加权因子*/
    private static int[] wi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    /**本体码*/
    private static char[] ai = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    /**正则表达式*/
    private static String regex = "^\\d{17}[0-9Xx]{1}$";
    
    /**
     * 校验18位身份证合法性
     * @param id 18位中国居民身份证
     * @return
     */
    public static boolean verifyIdNumber(String id){
        boolean flag = false;
        if(StringUtil.isStrEmpty(id)){
            return false;
        }
        if(id.length()!=18){
            return false;
        }
        
        if(!id.toUpperCase().matches(regex)){
            return false;
        }
        
        //前17位
        String pre17 = id.substring(0, 17);
        //最后一位
        String last1 = id.substring(17, 18).toUpperCase();
        //计算最后一位
        String last1_ = getLastOne(pre17);
        if(last1.equals(last1_)){
            flag = true;
        }
        return flag;
    }
    
    /**
     * 获取最后一位
     * @param pre17
     * @return
     */
    private static String getLastOne(String pre17){
        int total = 0;
        for (int i = 0; i < pre17.length(); i++) {
            int n = Integer.parseInt("" + pre17.charAt(i));
            total += n * wi[i];
        }
        int lastOne = total%11;
        return String.valueOf(ai[lastOne]);
    }
    
    /**
     * 根据18位身份证号码判断性别
     * @param id 18位中国居民身份证
     * @return
     */
    public static Sex getSexFromIdNumber(String id) {
        int last2 = Integer.parseInt(id.substring(16, 17));
        return last2%2==0 ? Sex.FEMALE : Sex.MALE;
    }
    
    /**
     * 是否男性
     * @param id 18位中国居民身份证
     * @return
     */
    public static boolean isMale(String id) {
        return Sex.MALE.equals(getSexFromIdNumber(id));
    }
    
    /**
     * 是否女性
     * @param id 18位中国居民身份证
     * @return
     */
    public static boolean isFemale(String id) {
        return Sex.FEMALE.equals(getSexFromIdNumber(id));
    }
    
    /**
     * 从身份证号码中获取出生日期
     * @param id 18位中国居民身份证
     * @return 日期格式：yyyy-MM-dd
     */
    public static String getBirthdayFromIdNumber(String id) {
        return id.substring(6, 10) + "-" + id.substring(10, 12) + "-" + id.substring(12, 14);
    }
    
    /**
     * 获取18位身份证号码后4位
     * @param id
     * @return
     */
    public static String getLast4Numbers(String id) {
        return id.substring(14, 18);
    }

    /**
     * 根据身份证计算年龄
     * @param id
     * @return
     */
	public static int getAgeFromIdNumber(String id) {
	    int age = 0;
	    String birthday = getBirthdayFromIdNumber(id);
	    String currDay = TimeUtil.getCurDate();
	    
	    int by = Integer.parseInt(birthday.substring(0, 4));
	    int bm = Integer.parseInt(birthday.substring(5, 7));
	    int bd = Integer.parseInt(birthday.substring(8, 10));
	    
	    int cy = Integer.parseInt(currDay.substring(0, 4));
        int cm = Integer.parseInt(currDay.substring(5, 7));
        int cd = Integer.parseInt(currDay.substring(8, 10));
        
        age = cy - by;
        //当前月份小于出生月份，年龄-1
        if(cm < bm) {
            --age;
        } else if(cm == bm) {
            //月份相同
            age = cd <= bd ? --age : age;
        }
        
	    return age < 0 ? 0 : age;
	}
}
