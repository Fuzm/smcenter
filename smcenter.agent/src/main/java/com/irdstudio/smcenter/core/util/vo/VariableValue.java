package com.irdstudio.smcenter.core.util.vo;
/**
 * @docRoot
 * 变量容器类
 * @author 李广明
 * @version 1.0
 * @date 2006-11-15
 *
 */
import java.util.Hashtable;
import java.util.Map;
public class VariableValue {

	//定义存放变量值的容器
	private Map<String, String> varMap = null;
	
	public VariableValue(){
		varMap = new Hashtable<String, String>();
	}
	
	/**
	 * 向容器中增加变量
	 * @param varName
	 * @param varValue
	 */
	public void addVariable(String varName, String varValue) {
		if (varValue == null)
			varValue = "";
		varName = varName.toUpperCase();
		varMap.put(varName, varValue);
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
	 * 多态的从容器中取得变量的值
	 * @param obj
	 * @return
	 */
	public String getValue(Object obj){
		if(obj == null) return "";
		return getValue(obj.toString().toUpperCase());
	}
}