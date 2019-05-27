package com.irdstudio.smcenter.core.assembly.imdbcp;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.irdstudio.smcenter.core.util.pub.PathUtil;

/**
 * 主要用于启动连接池
 * 用于补充原连接池
 * @author 李广民
 * @version 1.0
 * @date 2008-07-25
 *
 */
public class PoolService {
	
	public static void start() throws Exception{
		Properties pram = new Properties();
		readFromXML(pram);
		DBManager.setConfigs(pram);
		DBManager.getInstance();
	}
	
	/**
	 * 	以干预方式启动连接池,读取XML配置后仍干预其主要属性
	 * 指定连接字符串及用户名、密码、schema
	 * @param connStr 连接字符串
	 * @param dbUser  用户名
	 * @param dbPwd 密码
	 * @param dbSchema 模式
	 * @throws Exception 
	 */
	public static void startWithIntervene(String connStr, String dbUser,
			String dbPwd, String dbSchema) throws Exception {
		Properties pram = new Properties();
		readFromXML(pram);
		pram.setProperty("ds_1_Url", connStr);
		pram.setProperty("ds_1_User", dbUser);
		pram.setProperty("ds_1_Password", dbPwd);
		pram.setProperty("ds_1_ext_currentSchema", dbSchema);
		DBManager.setConfigs(pram);
		DBManager.getInstance();	
	}
	
	/**
	 * 关闭数据源
	 * 并中断监控进程
	 */
	public static void closeDataSource(){
		DBManager.getInstance().close();
	}
	
	/**
	 * 从指定文件中读取配置信息
	 * @param prop
	 * @return
	 * @throws Exception
	 */
	public static Properties readFromXML(Properties prop) throws Exception {
		
		SAXBuilder sb = new SAXBuilder();
		String qq4 = null;
		
		//从文件构造一个Document，因为XML文件中已经指定了编码，所以这里不必了
		String fullXmlFilePath = PathUtil.getClassRootPath() + "env.xml";
		Document doc = sb.build(new FileInputStream(fullXmlFilePath));

	    Element root = doc.getRootElement();

	    //得到根元素
	    List<?> qq1 = root.getChildren();

	    //得到根元素所有子元素的集合
	    List<?> qq2 = null;
	    for (Iterator<?> iter1 = qq1.iterator(); iter1.hasNext(); ) {
	    	Element root1 = (Element) iter1.next();
	    	qq2 = root1.getChildren();
	    	for (Iterator<?> iter2 = qq2.iterator(); iter2.hasNext(); ) {
	    		Element qq3 = (Element) iter2.next();
	    		if (qq3.getName().indexOf("dao") != -1
	    				|| qq3.getName().indexOf("cmd") != -1) {
	    			qq4 = qq3.getName().substring(3);
	    		} else {
	    			qq4 = qq3.getName();
	    		}
	    		prop.setProperty(qq4, qq3.getText());
	    	}
	    }
	    return prop;
	}
	
	public static void main(String[] args) throws Exception{
		start();
	}
}
