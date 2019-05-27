package com.irdstudio.smcenter.core.assembly.jxp.parser;
/**
 * @docRoot
 * 参数解释者,负责将其中的参数直接解释成值
 * @author 李广明
 * @version 1.0
 * @date 2006-11-15
 */
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.irdstudio.smcenter.core.assembly.jxp.util.LogUtil;
import com.irdstudio.smcenter.core.util.vo.VariableValue;
public class ParamParser {
	
	private final static char NORMAL_VARIABLE_BEGIN = '[';	//标准变量起始标识符
	private final static char NORMAL_VARIABLE_END 	= ']';	//标签变量结束标识符
	private final static char FIELD_VARIABLE_BEGIN  = '{';	//字段变量起始标识符
	private final static char FIELD_VARIABLE_END  	= '}';	//字段变量结束标识符
	
	/**
	 * 解释数据源(SQL)
	 * @param vv
	 * @param rs
	 * @return
	 */
	public final static String parseVariable(String str,VariableValue vv,ResultSet rs){	

		int normalIndex = -1;
		int fieldIndex = -1;
		List<String> normalVariable = new ArrayList<String>();
		List<String> fieldVariable  = new ArrayList<String>();
		StringBuffer sb = new StringBuffer(str);
		
		for (int i = 0; i < sb.length(); i++) {
			switch(sb.charAt(i)){
				case NORMAL_VARIABLE_BEGIN:
					normalIndex = i+1;
					break;
				case NORMAL_VARIABLE_END:
					if(normalIndex > -1)
						normalVariable.add(sb.substring(normalIndex, i));
					normalIndex = -1;
					break;
				case FIELD_VARIABLE_BEGIN:
					fieldIndex = i + 1;
					break;
				case FIELD_VARIABLE_END:
					if (fieldIndex > -1)
						fieldVariable.add(sb.substring(fieldIndex, i));
					fieldIndex = -1;
					break;
			}
		}
		
		//解释标准变量
		for (int i = 0; i < normalVariable.size(); i++) {
			String value = vv.getValue(normalVariable.get(i));
			StringBuffer varName = new StringBuffer();
			varName.append('\\');
			varName.append(NORMAL_VARIABLE_BEGIN);
			varName.append(normalVariable.get(i));
			varName.append('\\');
			varName.append(NORMAL_VARIABLE_END);
			str = str.replaceAll(varName.toString(),value);
		}
		
		//解释字段变量
		for (int i = 0; i < fieldVariable.size() && rs != null; i++) {
			String value = "";
			try {
				value = rs.getString(fieldVariable.get(i).toString());				
			} catch (SQLException e) {
				LogUtil.out("取字段[" 
						+ fieldVariable.get(i).toString()
						+ "]出错");
				LogUtil.writeError(e);
			}
			StringBuffer varName = new StringBuffer();
			varName.append('\\');
			varName.append(FIELD_VARIABLE_BEGIN);
			varName.append(fieldVariable.get(i));
			varName.append('\\');
			varName.append(FIELD_VARIABLE_END);
			value = value == null ? "" : value;
			//对其进行转码操作
			try {
				value = new String(value.getBytes("ISO8859-1"));
			} catch (UnsupportedEncodingException e) {
				LogUtil.out("转码出错");
				e.printStackTrace();
			}
			str = str.replaceAll(varName.toString(),value);
		}
		LogUtil.log("[变量解释器]:[" + str + "]");
		return str;
	}
}
