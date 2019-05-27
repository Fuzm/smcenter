package com.irdstudio.smcenter.core.assembly.jxp.func;
/**
 * @docRoot
 * Jxp内置函数库,用于解释本身可以支持的函数
 * @author 李广明
 * @version 1.0
 * @date 2006-11-15
 */
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import com.irdstudio.smcenter.core.assembly.jxp.conf.DefineSection;
import com.irdstudio.smcenter.core.assembly.jxp.util.LogUtil;
import com.irdstudio.smcenter.core.util.pub.Convert;
import com.irdstudio.smcenter.core.util.pub.SimpleDateUtil;
public class JxpFunc {

	//定义内置函数库所需要的构造参数
	private DefineSection define = null;

	//内置函数库中所支持的函数
	private static final Map<String, String> funLib = new Hashtable<String, String>();
	static{
		funLib.put("tran","2");
		funLib.put("getValue","1");
		funLib.put("sum","-1");
		funLib.put("concat","-1");
		funLib.put("DateFormat","2");
		funLib.put("substring","3");
		funLib.put("FloatFormat","3");
		funLib.put("DateDiff","-1");
	}

	public JxpFunc(DefineSection define) {
		this.define = define;
	}

	/**
	 * 入口方法
	 * @param funcName
	 * @param param
	 * @return
	 */
	public String callJxpFunc(String funcName, String param) {
		//使用反射机制将需要映射到需要调用的内置函数
		String result = "";
		Method doMethod = null;
		Class<? extends JxpFunc> thisClass = this.getClass();
		Class[] parameterTypes = new Class[] { String.class };
		Object[] arguments = new Object[] {param};
		try {
			doMethod = thisClass.getMethod(funcName.toLowerCase(),
					parameterTypes);
			result = (String) doMethod.invoke(this, arguments);
		} catch (NoSuchMethodException e) {
			LogUtil.out("不支持[" + funcName + "]函数!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 转换函数,根据定义,将值转换成相应的内容
	 * @param param
	 * @return
	 */
	public String tran(String param){

		//分解参数并判断参数个数
		String[] params = splitParam(param);
		if (params.length < 3) {
			LogUtil.out("Error:内置函数调用错误!");
			LogUtil.out("Usage:tran(<param1>,<param2>,[param2])");
		}

		//第二个参数应当由定义区中取得
		Map<?, ?> value = (Hashtable<?, ?>) define.getDefine(params[1]);
		if (value == null
				|| value.get(params[0]) == null) {
			if (params.length == 3)
				return params[2];
			else
				return "not found";
		}
		return (String) value.get(params[0]);
	}

	/**
	 * 用于连接字符串
	 * @param param
	 * @return
	 */
	public String concat(String param){
		String[] params = splitParam(param);
		String result = "";
		for (int i = 0; i < params.length; i++) {
			result.concat(params[i]);
		}
		return result;
	}

	/**
	 * 用于求和
	 * @param param
	 * @return
	 */
	public String sum(String param){
		long result = 0;
		String[] params = splitParam(param);
		for (int i = 0; i < params.length; i++) {
			result += Convert.StrToInt(params[i],0);
		}
		return new Long(result).toString();
	}

	/**
	 * 主要用于将字段变量
	 * 标准变量的值直接取出来后返回
	 * @param param
	 * @return
	 */
	public String getvalue(String param){
		return param;
	}

	/**
	 * 返回指定格式的日期串格式
	 * @param param
	 * @return
	 */
	public String dateformat(String param){
		String[] params = splitParam(param);
		if (params.length != 2) {
			LogUtil.out("Usage:dateformat(datestr,format)");
			return "param error";
		}
		if("".equals(params[0].trim())) return "";
		if ("yyyymmdd".equals(params[1].toLowerCase())) {
			StringBuffer date = new StringBuffer();
			try {
				SimpleDateUtil.getDateElements(params[0]);
				date.append(SimpleDateUtil.getYear());
				if(SimpleDateUtil.getMonth() < 10)
					date.append('0');
				date.append(SimpleDateUtil.getMonth());
				if(SimpleDateUtil.getDay() < 10)
					date.append('0');
				date.append(SimpleDateUtil.getDay());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return date.toString();
		} else
			return SimpleDateUtil.formatDate(params[0], params[1]);
	}


	/**
	 * 返回指定格式的浮点数
	 * @param param
	 * @return
	 */
	public String floatformat(String param){
		StringBuffer result = new StringBuffer();
		String[] params = splitParam(param);
		if(params.length != 2){
			LogUtil.out("Usage:floatformat(float,format)");
			return "param error";
		}
		if (params[0] == null ||
				"".equals(params[0].trim()))
			result.append('0');
		else
			result.append(params[0]);
		int digitIndex = result.indexOf(".");
		int digitLength = Convert.StrToInt(params[1],0);
		String zs = "";
		String xs = "";
		if (digitIndex > 0) {
			zs = result.substring(0, digitIndex);
			xs = result.substring(digitIndex + 1);
		}
		else{
			zs = result.toString();
		}
		//补齐小数位
		for (int i = 0; i < digitLength; i++) {
			xs += "0";
		}
		//按格式生成数据
		if (digitLength > 0) {
			result.delete(0, result.length());
			result.append(zs);
			result.append('.');
			result.append(xs.substring(0, digitLength));
		} else {
			result.delete(0, result.length());
			result.append(zs);
		}
		return result.toString();
	}

	/**
	 * 返回指定长度的字符串
	 * @param param
	 * @return
	 */
	public String substring(String param){
		String[] params = splitParam(param);
		if (params.length < 2) {
			LogUtil.out("Usage:substring(<str>,<begin>,[end])");
			return "param error";
		}
		int begin = Convert.StrToInt(params[1], 0);
		int end = 0;
		if (params.length == 3)
			end = Convert.StrToInt(params[2], 0);
		if (params[0] == null || "".equals(params[0]))
			return "";
		if (end == 0 || params[0].length() < end)
			return params[0].substring(begin);
		else
			return params[0].substring(begin, end);
	}

	/**
	 * 返回两个日期之间的相隔天数
	 * @param param
	 * @return
	 */
	public String datediff(String param){
		long result = 0;
		String[] params = splitParam(param);
		if (params.length < 2) {
			LogUtil.out("Usage:datediff(d1,[...])");
			return "param error";
		}

		if (params[0] == null || "".equals(params[0]))
			return new Long(result).toString();

		for (int i = 1; i < params.length; i++) {
			if (params[i] != null
					&& !"".equals(params[i])) {
				try {
					result = SimpleDateUtil.diffDate(params[0],params[i]);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(result > 0)
					break;
			}
		}
		result = result < 0 ? 0 : result;
		return new Long(result).toString();
	}

	/**
	 * @param param
	 * @return
	 */
	public String getyqts(String param){
		long result = 0;
		String[] params = splitParam(param);
		if (params.length <3) {
			LogUtil.out("Usage:getyqtx(nf,yf,date");
			return "param error";
		}

		if (params[0] == null || "".equals(params[0]))
			new Long(result).toString();

		String fljzrq = this.getmonthlastday(params[0] + "," + params[1]);
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		sf.setLenient(false);
		Date date = null;
		try {
			date = sf.parse(fljzrq);
			fljzrq = SimpleDateUtil.formatDate(date, "yyyy-MM-dd");

		} catch (java.text.ParseException e) {
			e.printStackTrace();
			LogUtil.out("日期解释错误!");
		}		        
		return this.datediff(params[2].trim() + "," + fljzrq);
	}

	/**
	 * 返回某年某月的最后一天是几号
	 * @param param
	 * @return
	 */
	public String getmonthlastday(String param){
		String result = "";
		String[] params = splitParam(param);
		if (params.length != 2) {
			LogUtil.out("Usage:getMonthLastDay(<nf,yf>)");
			return "param error";
		}
		int nf = Convert.StrToInt(params[0],2006);
		int yf = Convert.StrToInt(params[1],1);
		result = SimpleDateUtil.getMonthLastDayStr(nf,yf);
		return result;
	}

	/**
	 * 分解函数,用于分解参数
	 * @param param
	 * @return
	 */
	private String[] splitParam(String param){
		if (param.charAt(param.length() - 1) == ',')
			param = param.concat(" ");
		return param.split(",");
	}

	/**
	 * 用于校验函数库中是否支持该函数
	 * @return
	 */
	public boolean isExists(){
		return true;
	}

	public static void main(String[] args){
		JxpFunc func = new JxpFunc(null);
		System.err.println(func.callJxpFunc("getyqts", "2006,09,2006-01-01"));
	}
}
