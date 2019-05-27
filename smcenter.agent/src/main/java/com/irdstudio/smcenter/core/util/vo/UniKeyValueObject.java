package com.irdstudio.smcenter.core.util.vo;
/**
 * @docRoot
 * 变量容器类
 * @author 李广明
 * @version 1.1
 * @date 2006-11-15
 * @modify 2014-07-07
 * 		更换为Java 5中支持高并发高吞吐量的线程安全的HashMap
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.irdstudio.smcenter.core.util.pub.Convert;
public class UniKeyValueObject {

	// 定义存放变量值的容器
	private Map<String, Object> varMap = null;

	/**
	 *  默认构造函数
	 *  线程安全,用于支持高并发
	 */
	public UniKeyValueObject() {
		varMap = new ConcurrentHashMap<String, Object>();
	}
	
	/**
	 * 根据选择构造(如果传false，则构造为非线程安全的MAP)
	 * 如果不是在并发环境使用，可以使用hashMap
	 * @param isThreadSafe
	 */
	public UniKeyValueObject(boolean isThreadSafe) {
		if (isThreadSafe) {
			varMap = new ConcurrentHashMap<String, Object>();
		} else {
			varMap = new HashMap<String, Object>();
		}
	}
	
	/**
	 * 向容器中增加变量
	 * @param varName
	 * @param varValue
	 */
	public void addVariable(String varName,String varValue){		
		varName = varName.toUpperCase();
		if(null == varValue){
			varMap.put(varName,"");
		} else {
			varMap.put(varName,varValue);
		}
	}
	
	/**
	 * 向容器顺中存放对象
	 * @param varName
	 * @param varValue
	 */
	public void addVariable(String varName,Object varValue){
		varName = varName.toUpperCase();
		if(null == varValue){
			varMap.put(varName,"");
		} else {
			varMap.put(varName,varValue);
		}
	}	
	
	/**
	 * 从容器中取得变量的值
	 * @param varName
	 */
	public String getValue(String varName){
		varName = varName.toUpperCase();
		Object objR = varMap.get(varName);
		if(objR == null) return "";
		return (String)objR;
	}
	
	/**
	 * 返回整型值
	 * @param varName
	 * @return
	 */
	public int getIntValue(String varName) {
		Object value = varMap.get(varName.toUpperCase());
		if(value == null) return 0;
		return Convert.StrToInt(value.toString(), 0);
	}
	
	/**
	 * 从容器中取得变量的值,如果容器不存在该键值,且默认值不为空,则返回默认值
	 * @param varName
	 */
	public String getValue(String varName,String defaultv){
		varName = varName.toUpperCase();
		Object objR = varMap.get(varName);
		if(objR == null&&defaultv!=null){
			return defaultv;
		}else if (objR == null&&defaultv==null){
			 return "";
		}else{
			return (String)objR;
		} 
	}
	
	/**
	 * 多态的从容器中取得变量的值
	 * @param obj
	 * @return
	 */
	public String getValue(Object obj){
		if(obj == null) return "";
		return getValue(obj.toString().toUpperCase());
	}
	
	/**
	 * 根据对象获得对象
	 * @param obj
	 * @return
	 */
	public Object getObject(Object obj){
		if(obj == null) return "";
		return varMap.get(obj);
	}
	
	public boolean isExist(Object obj){
		if(obj == null) return false;
		if(varMap.get(obj) == null){
			return false;
		}
		return true;
	}
	
	/**
	 * 如果存在变量,先删除,后填入
	 * 如果不存在变量,直接填入
	 * @param varName
	 * @param value
	 */
	public void merge(String varName,String value){
		varName = varName.toUpperCase();
		Object objR = varMap.get(varName);
		if(objR == null) varMap.put(varName, value);
		else {
			varMap.remove(objR);
			varMap.put(varName, value);
		}
	}
	
	/**
	 * 删除一个键值
	 * @param varName
	 */
	public void remove(String varName) {
		if (varName == null)
			return;
		varMap.remove(varName.toUpperCase());
	}
	
	/**
	 * 清空存储的数据
	 */
	public void clear(){
		varMap.clear();
	}
	
	/**
	 * 将本类的核心,即用于存放数据的MAP返回
	 * @return
	 */
	public Map<String, Object> getMap(){
		return varMap;
	}
	
	public String toString(){
		StringBuffer sb=new StringBuffer("{");
		for (Iterator<?> iter = this.getMap().keySet().iterator(); iter.hasNext();) {
			String f = (String) iter.next();
			sb.append(f).append(this.getValue(f)).append(",");//.append("\n");
		}
		sb.append("}");
		return sb.toString();
	}
}