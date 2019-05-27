package com.irdstudio.smcenter.core.util.parse;
/**
 * @docRoot
 * 变量解释,负责将其中的变量解释出来
 * @author guangming.li
 * @version 1.0
 * @date 2013-10-25
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.irdstudio.smcenter.core.util.vo.VariableValue;

public class VariableParserUtil {
	
	/* 标准变量起始标识符 */
	private final static char NORMAL_VARIABLE_BEGIN = '[';
	/* 标签变量结束标识符 */
	private final static char NORMAL_VARIABLE_END = ']';
	/* 字段变量起始标识符 */
	private final static char FIELD_VARIABLE_BEGIN = '{';
	/* 字段变量结束标识符 */
	private final static char FIELD_VARIABLE_END = '}';
	
	
	
	/**
	 * 将字符串的变量解释出来
	 * @param vv
	 * @return
	 */
	public final static String parseString(String str, VariableValue vv) {

		int normalIndex = -1;
		List<String> normalVariable = new ArrayList<String>();
		StringBuffer sb = new StringBuffer(str);

		for (int i = 0; i < sb.length(); i++) {
			switch (sb.charAt(i)) {
			case NORMAL_VARIABLE_BEGIN:
				normalIndex = i + 1;
				break;
			case NORMAL_VARIABLE_END:
				if (normalIndex > -1)
					normalVariable.add(sb.substring(normalIndex, i));
				normalIndex = -1;
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
			str = str.replaceAll(varName.toString(), value);
		}
		
		return str;
	}
	
	/**
	 * 解释字段变量
	 * @param str
	 * @param rs
	 * @return
	 */
	public final static String parseString(String str, ResultSet rs) {

		int fieldIndex = -1;
		List<String> fieldVariable = new ArrayList<String>();
		StringBuffer sb = new StringBuffer(str);

		for (int i = 0; i < sb.length(); i++) {
			switch (sb.charAt(i)) {
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

		// 解释字段变量
		for (int i = 0; i < fieldVariable.size() && rs != null; i++) {
			String value = "";
			try {
				value = rs.getString(fieldVariable.get(i).toString());
			} catch (SQLException e) {
				System.err.println("取字段[" + fieldVariable.get(i).toString()
						+ "]出错");
				value = fieldVariable.get(i).toString();
			}
			StringBuffer varName = new StringBuffer();
			varName.append('\\');
			varName.append(FIELD_VARIABLE_BEGIN);
			varName.append(fieldVariable.get(i));
			varName.append('\\');
			varName.append(FIELD_VARIABLE_END);
			value = value == null ? "" : value;
			str = str.replaceAll(varName.toString(), value);
		}
		return str;
		
	}
	
}
