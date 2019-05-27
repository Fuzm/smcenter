package com.irdstudio.smcenter.core.assembly.etl.conf;
/**
 * @docRoot:
 * 	专门用于读取属性文件
 * @author 李广明
 * @version 1.0
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
public class ReadProperties {
	
	//用来打开属性文件的状态
	public final static int STATE_NOEXIST 	  = 2;
	public final static int STATE_IOEXCEPTION = 3;
	public final static int STATE_OK		  = 1;	
	
	//定义一些初始化的变量及常量
	private Properties prop = null;
	
	/**
	 * 读取指定的配置文件
	 * @return
	 * @throws FileNotFoundException 
	 */
	public int openFile(String filePath){
		int fileState = STATE_OK;
		try{
			FileInputStream fis = new FileInputStream(filePath);
			prop = new Properties();
			prop.load(fis);			
		}
		catch(FileNotFoundException e){
			fileState = STATE_NOEXIST;
		}
		catch(IOException ie){
			fileState = STATE_IOEXCEPTION;
		}
		return fileState;
	}
	
	/**
	 * 根据KEY读取值,这里并不作prop是否有效的判断
	 * 调用该类时,使用者应当在openFile后,先行判断
	 * 打开是否是成功的,如未判断,引用此方法可能会导
	 * 致抛异常
	 * @param key
	 * @return
	 */
	public String get(String key){
		return prop.getProperty(key);
	}
	
	/**
	 * 将配置文件信息输出到控制台
	 */
	public void list(){
		prop.list(System.out);
	}
	
	/**
	 * 关闭配置文件
	 * @return 无返回值
	 */
	public void closeFile(){
		//如何关闭配置文件,暂无方法
		//等以后补充完成
		prop.clear();
		prop = null;
	}
}
