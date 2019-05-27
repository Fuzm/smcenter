package com.irdstudio.smcenter.agent.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置文件加载
 * 
 * 
 * @author Cytus_
 * @since 2018年5月17日 上午10:36:15
 * @version 1.0
 *
 */
public class PropertiesUtil {
	
	private static Map<String, Properties> propertiesMap = new ConcurrentHashMap<String, Properties>();

	public static synchronized Properties init(String prop) {
		
		if (propertiesMap.containsKey(prop)) {
			return propertiesMap.get(prop);
		}
		
		try {
			Properties properties = new Properties();
			ResourceBundle res = ResourceBundle.getBundle(prop);
			for (String key : res.keySet()) {
				properties.put(key, res.getString(key));
			}
			return properties;
		} catch (Exception e) {
			return initLoad(prop);
		}
	}
	
	private static Properties initLoad(final String prop) {
		Properties properties = new Properties();
		String locationPath = PropertiesUtil.class.getResource("/").toString().replace("file:/","");
		File filed = new File(locationPath);
		File[] files = null;
		if (filed.isDirectory()) {
			files = filed.listFiles(new FilenameFilter() {
				
				public boolean accept(File file, String s) {
					return file.exists() && s.toLowerCase().equals(prop +".properties");
				}
			});
		}
		
		if (files != null && files.length > 0) {
			InputStream is = null;
			try {
				for (File file : files) {
					Properties p = new Properties();
					is = new BufferedInputStream(new FileInputStream(file));
					p.load(is);
					is.close();
					properties.putAll(p);
				}
			} catch (Exception e) {
				AnsiLog.error(e.getMessage(), e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
					}
				}
			}
		}
		return properties;
	}
	
	public static String getPropertyByKey(String propKey, String key) {
		Properties prop = propertiesMap.get(propKey);
		if (Objects.isNull(prop) || prop.isEmpty()) {
			prop = init(propKey);
		}
		if (Objects.nonNull(prop))
			return prop.getProperty(key);
		return null;
	}
	
	public static String getApplicationKey(String key) {
		String propKey = "application";
		Properties prop = propertiesMap.get(propKey);
		if (Objects.isNull(prop) || prop.isEmpty()) {
			prop = init(propKey);
		}
		if (Objects.nonNull(prop))
			return prop.getProperty(key);
		return null;
	}
	
}
