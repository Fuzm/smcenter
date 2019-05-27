package com.irdstudio.ssm.framework.util;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件工具类
 * @author liwy
 *
 * time:2016年8月23日下午5:37:38
 */
public class PropsConfig{
    
	/****
	 *增加根据环境变量来获取配置文件信息,环境变量名ECF_CONFIG
	 * 这里使用confi+变量+.properties格式
	 * 优先使用config.properties文件
	 * ******/
	public static String MECP_CONFIG = "ECF_CONFIG";
	public static String fname = "system";
	public static String suffix = ".properties";
	
	private static Properties props = new Properties();
	
	static{
		load();
	}
	
	private static synchronized void load() {
		
		try {
			if(props==null || props.isEmpty()){
				 String  f = System.getenv(MECP_CONFIG);
				 InputStream is = PropsConfig.class.getClassLoader().getResourceAsStream(f!=null?(fname+f+suffix):(fname+suffix));
				 props.load(is);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
		}
			
	}
	
	public static synchronized void init(){
		if(props==null || props.isEmpty()){
			load() ;
		}
	}

	public  static String getPropValue(String key) {
		if(props == null || props.isEmpty()){
			load();
		}
		return (String)props.get(key);
	}
	
	public static int getPropValue(String key,int defaultV) {
		return getPropValue(key)!=null?Integer.parseInt(getPropValue(key)):defaultV;
	}
	
	public static String getPropValue(String key,String defaultV) {
		return getPropValue(key)!=null?getPropValue(key):defaultV;
	}
	
	public static Properties getPproperties(){
		init();
		return props;
	}
	
}
