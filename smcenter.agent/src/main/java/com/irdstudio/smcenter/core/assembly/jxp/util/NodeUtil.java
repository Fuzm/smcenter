package com.irdstudio.smcenter.core.assembly.jxp.util;
/**
 * 辅助进行配置节点解释
 * @author guangming.li
 * @version 1.0
 * @date 2013-12-19
 */
public class NodeUtil {

	public final static int SINGLE 		= 1;	//单节点
	public final static int REPEAT 		= 2;	//多节点
	public final static int NOSUPPORT 	= 0;	//不支持
	
	/**
	 * 由节点类型名称返回其对应的整型
	 * @param type
	 * @return
	 */
	public final static int getNodeType(String type){
		if (type == null || "".equals(type))
			return NOSUPPORT;
		if ("SINGLE".equals(type.trim().toUpperCase())) {
			return SINGLE;
		} else if ("REPEAT".equals(type.trim().toUpperCase())) {
			return REPEAT;
		}
		return NOSUPPORT;
	}

}
