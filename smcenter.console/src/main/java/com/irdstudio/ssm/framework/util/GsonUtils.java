package com.irdstudio.ssm.framework.util;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * JSON转换器
 * 
 *
 */
public class GsonUtils {
	
	
	private static Gson gson;
	
	/**
	 * 静态代码块(初始化GSON)
	 */
	static{
	    GsonBuilder gb=new GsonBuilder();  
        gb.registerTypeAdapter(java.util.Date.class , new  Date2LongTypeAdapter()).setDateFormat(DateFormat.LONG);  
		gson = gb.create();
	}
	
	/**
	 * 获取PHP请求JSON串(填充Bean对象)
	 * @param <T>
	 * @param json
	 * @param classOfT
	 * @return <T> T
	 */
	public static <T> T getJson(String json,Class<T> classOfT){
		return  gson.fromJson(json, classOfT);
	}
	
	public static <T> T getJson(String json,Type typeOfT){
		return  gson.fromJson(json, typeOfT);
	}
	
	/**
	 * 将对象转换为JSON字符串
	 * @param obj
	 * @return String
	 */
	public static String toJson(Object obj){
		return gson.toJson(obj);
	}
	
	
	/**
	 * 返回格式 : {"code":"0","data":"",desc:"exec success"}
	 * @return
	 */
	public static String retJson(){
		return GsonUtils.retJson("0", "","success");
	}
	
	
	/**
	 * 返回格式 : {"code":"0","data":{}/[{},{}...],desc:"exec success"}
	 * @return
	 */
	public static String retJson(Object data){
		return GsonUtils.retJson("0", data,"success");
	}
	
	/**
	 * 返回格式 : {"code":"11","data":"",desc:"exec success"}
	 * @return
	 */
	public static String retJson(String errcode,String errdesc){
		return GsonUtils.retJson(errcode,"",errdesc);
	}
	
	/**
	 * 用户自定义Json
	 * @param code
	 * @param data
	 * @param others
	 * @param totals
	 * @param errcode
	 * @param desc
	 * @return
	 */
	public  static String retJson(String code,Object data,String desc){
		Map<String,Object> map = new HashMap<String,Object>();
		String retJson;
		map.put("code",code);
		if(data != null && !"".equals(data.toString().trim())){
            map.put("data",data);
		}else{
			map.put("data","");
		}
		if(desc != null && !"".equals(desc)){
			map.put("desc", desc);
		}else{
			map.put("desc", "");
		}
		retJson = GsonUtils.toJson(map);
		map.clear();map = null;
		return retJson;
	}
}
