package com.irdstudio.smcenter.core.assembly.jxp.conf;
/**
 * @docRoot
 * 存放定义信息(常量)
 * @author 李广明
 * @version 1.0
 * @date 2006-11-13
 */
import java.util.Hashtable;
import java.util.Map;
public class DefineSection {

	//来存放常量信息的MAP容器
	private Map<Object, Object> constArea = new Hashtable<Object, Object>();
	
	/**
	 * 从定义区中取得一个值
	 * @param obj
	 * @return
	 */
	public Object getDefine(Object obj){
		return constArea.get(obj);
	}
	
	/**
	 * 往定义区中填入一个定义
	 * @param obj1
	 * @param obj2
	 */
	public void putDefine(Object obj1,Object obj2){
		constArea.put(obj1,obj2);
	}
	
	/**
	 * 清空定义区
	 */
	public void clear(){
		constArea.clear();
	}
	
	public Map<Object, Object> getConstArea() {
		return constArea;
	}

	public void setConstArea(Map<Object, Object> constArea) {
		this.constArea = constArea;
	}
}
