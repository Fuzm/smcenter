package com.irdstudio.ssm.framework.util;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
 

/**
 * 
 * String工具类
 * @author chenw
 * 
 */
public class StringUtil {
	/**
	 * 分隔符";"
	 */
	public static String Separate_FenHao = ";";

	/**
	 * 分隔符"|"
	 */
	public static String Separate_Split = "\\|";

	/**
	 * 分隔符":"
	 */
	public static String Separate_MaoHao = ":";

	/**
	 * 分隔符"="
	 */
	public static String Separate_DengHao = "=";

	/**
	 * 分隔符","
	 */
	public static String Separate_DouHao = ",";

	/**
	 * 分隔符"_"
	 */
	public static String Separate_Xiahuaxian = "_";

	/**
	 * 判断字符串是否为空 
	 * @param source
	 * @return 为空返回true；
	 */
	public static boolean isEmpty(String source) {
		return StringUtils.isEmpty(source);
	}
	/**
	 * 判断字符串是否为空 或者为" "
	 * @param source
	 * @return 为空 或者为" "返回true；
	 */
	public static boolean isNullorBank(String source) {
			if(null ==source||"".equals(source.trim()))
				return true;
			else
				return false;
	}
	
	/**
	 * 判断字符串是否为空或者为null字符串
	 * @param str
	 * @return
	 */
	public static boolean isStrEmpty(String str) {
		return isEmpty(str) || "null".equals(str.trim());
	}
	
	/**
	 * 判断字符串是否不为空
	 * @param source
	 * @return
	 */
	public static boolean isNotEmpty(String source) {
		return !isEmpty(source);
	}
	
	/**
	 * 判断字符串是否不为空或者不为null字符串
	 * @param str
	 * @return
	 */
	public static boolean isStrNotEmpty(String str) {
		return !isStrEmpty(str);
	}

	/**
	 * 获取默认字符串，为空或null时，返回：""
	 * 
	 * @param source
	 * @return
	 */
	public static String nullToEmpty(String source) {
		return isStrEmpty(source) ? "" : source;
	}

	/**
	 * 如果字符串为空、空字符串，或"null"时返回"0"
	 * 
	 * @param str
	 * @return
	 */
	public static String nullToZero(String str) {
		return isStrEmpty(str) ? "0" : str.trim();
	}
 
	 
	/**
	 * 字符串转换为 Long
	 * 
	 * @param inStr
	 * @return
	 */
	public static Long String2Long(String inStr) {
		try {
			if (inStr == null || "".equals(inStr.trim()) || "0".equals(inStr.trim())) {
				return null;
			} else {
				return Long.parseLong(inStr);
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 清除不支持显示的HTML内容
	 * 
	 * @param strValue
	 * @return
	 */
	public static String clearHTML(String strValue) {
		if ("".equals(strValue))
			return "";
		if (strValue.indexOf("<") == -1)
			return strValue;
		while (strValue.toUpperCase().indexOf("<SCRIPT") > -1) {
			int begin = strValue.toUpperCase().indexOf("<SCRIPT");
			int end = strValue.toUpperCase().indexOf("</SCRIPT>");
			strValue = strValue.substring(0, begin)
					+ strValue.substring(end + 9);
		}
		while (strValue.toUpperCase().indexOf("<IFRAME") > -1) {
			int begin = strValue.toUpperCase().indexOf("<IFRAME");
			int end = strValue.toUpperCase().indexOf("</IFRAME>");
			strValue = strValue.substring(0, begin)
					+ strValue.substring(end + 9);
		}
		while (strValue.toUpperCase().indexOf("<NOSCRIPT") > -1) {
			int begin = strValue.toUpperCase().indexOf("<NOSCRIPT");
			int end = strValue.toUpperCase().indexOf("</NOSCRIPT>");
			strValue = strValue.substring(0, begin)
					+ strValue.substring(end + 11);
		}
		while (strValue.toUpperCase().indexOf("<!--") > -1) {
			int begin = strValue.indexOf("<!--");
			int end = strValue.indexOf("-->");
			strValue = strValue.substring(0, begin)
					+ strValue.substring(end + 3);
		}
		return strValue;
	}

	/**
	 * 将字符串转换为unicode编码的BYTE数组
	 * 
	 * @param inStr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] getBytesFromStr(String inStr)
			throws Exception {
		byte[] retBytes = inStr.getBytes("unicode");
		return retBytes;
	}

	/**
	 * 将unicode编码的BYTE数组转换为字符串
	 * 
	 * @param inBytes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getStrFromBytes(byte[] inBytes)
			throws Exception {
	    String retStr = new String(inBytes, "unicode");
		return retStr;
	}

	/**
	 * 
	 * 取指定字符串的指定长度子字串
	 * 
	 * @param strAll
	 * @param strLen
	 * @return
	 */
	public static final String subStr(String strAll, int strLen) {
		String strNew = nullToEmpty(strAll);
		String myStr = "";
		if (strNew.length() >= strLen) {
			myStr = strNew.substring(0, strLen);
		} else {
			myStr = strNew;
		}
		return myStr;
	}

	/**
	 * 
	 * 字符串替换 s 搜索字符串 s1 要查找字符串 s2 要替换字符串
	 * 
	 * @param s
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static String replace(String s, String s1, String s2) {
		if (s == null)
			return null;
		int i = 0;
		if ((i = s.indexOf(s1, i)) >= 0) {
			char ac[] = s.toCharArray();
			char ac1[] = s2.toCharArray();
			int j = s1.length();
			StringBuffer stringbuffer = new StringBuffer(ac.length);
			stringbuffer.append(ac, 0, i).append(ac1);
			i += j;
			int k;
			for (k = i; (i = s.indexOf(s1, i)) > 0; k = i) {
				stringbuffer.append(ac, k, i - k).append(ac1);
				i += j;
			}
			stringbuffer.append(ac, k, ac.length - k);
			return stringbuffer.toString();
		} else {
			return s;
		}
	}

	/**
	 * 字符串分割
	 * 
	 * 
	 * @param source
	 * @param seperate
	 * @return
	 */
	public static String[] split(String source, String seperate) {
		String[] wordLists;
		if (source == null) {
			wordLists = new String[1];
			wordLists[0] = source;
			return wordLists;
		}
		StringTokenizer st = new StringTokenizer(source, seperate);
		int total = st.countTokens();
		wordLists = new String[total];
		for (int i = 0; i < total; i++) {
			wordLists[i] = st.nextToken();
		}
		return wordLists;
	}

	/**
	 * 字符串分割
	 * 
	 * 
	 * @param source
	 * @param delim
	 * @return
	 */
	public static String[] split(String source, char delim) {
		return split(source, String.valueOf(delim));
	}
 
	/**
	 * 
	 * 将字符串数组合并成一个以 delim 分隔的字符串
	 * 
	 * @param array
	 * @param delim
	 * @return
	 */
	public static String combineArray(String[] array, String delim) {
		if (array == null || array.length == 0) {
			return "";
		}
		int length = array.length - 1;
		if (delim == null) {
			delim = "";
		}
		StringBuffer result = new StringBuffer(length * 8);
		for (int i = 0; i < length; i++) {
			result.append(array[i]);
			result.append(delim);
		}
		result.append(array[length]);
		return result.toString();
	}

	/**
	 * 以指定的字符和长度生成一个该字符的指定长度的字符串。
	 * 
	 * 
	 * @param c
	 * @param length
	 * @return
	 */
	public static String fillString(char c, int length) {
		String ret = "";
		for (int i = 0; i < length; i++) {
			ret += c;
		}
		return ret;
	}

	/**
	 * 
	 * 字符串数组中是否包含指定的字符串
	 * 
	 * @param strings
	 * 
	 * @param string
	 * 
	 * @param caseSensitive
	 * 
	 * @return
	 */
	public static boolean contains(String[] strings, String string,
			boolean caseSensitive) {
		for (int i = 0; i < strings.length; i++) {
			if (caseSensitive == true) {
				if (strings[i].equals(string)) {
					return true;
				}
			} else {
				if (strings[i].equalsIgnoreCase(string)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 得到字符串的字节长度
	 * 
	 * @param source
	 * @return
	 */
	public static int getByteLength(String source) {
		int len = 0;
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			int highByte = c >>> 8;
			len += highByte == 0 ? 1 : 2;
		}
		return len;
	}

	/**
	 * 判断字符是否为双字节字符，如中文
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isDoubleByte(char c) {
		return !((c >>> 8) == 0);
	}

	/**
	 * 字符串Like判断，支持%
	 * 
	 * @param pattern
	 * @param string
	 * @return
	 */
	public static boolean matchString(String pattern, String string) {
		int stringLength = string.length();
		int stringIndex = 0;
		for (int patternIndex = 0; patternIndex < pattern.length(); ++patternIndex) {
			char c = pattern.charAt(patternIndex);
			if (c == '%') {
				while (stringIndex < stringLength) {
					if (matchString(pattern.substring(patternIndex + 1), string
							.substring(stringIndex))) {
						return true;
					}
					++stringIndex;
				}
			} else {
				if (stringIndex >= stringLength
						|| c != string.charAt(stringIndex)) {
					return false;
				}
				++stringIndex;
			}
		}
		return stringIndex == stringLength;
	}

	/**
	 * 
	 * 将字符串追加至目标字符串并通过seperator分隔，不考虑重复
	 * 
	 * @param destString
	 * @param sourceString
	 * @param seperator
	 * @return
	 */
	public static String addtoTotalString(String destString,
			String sourceString, String seperator) {
		if (isEmpty(destString)) {
			return sourceString;
		} else {
			return destString + seperator + sourceString;
		}
	}

	/**
	 * 
	 * 获取字符串的位置
	 * 
	 * @param destString
	 * @param remString
	 * @param separator
	 * @return
	 */
	private static int getIndexOfTotalString(String destString,
			String remString, String separator) {
		if (destString == null) {
			return -1;
		}
		String tempTotalString = separator + destString + separator;
		int index = tempTotalString.indexOf(separator + remString + separator);
		if (index != 0 && index != -1) {
			index = index - 1;
		}
		return index;
	}

	/**
	 * 
	 * 将一个字符串从一个通过separator连接的字符串中移除，只移除一次
	 * 
	 * 
	 * @param destString
	 * @param remString
	 * @return
	 */
	public static String removeFromTotalString(String destString,
			String remString, String separator) {
		if (destString == null) {
			return "";
		}
		int index = getIndexOfTotalString(destString, remString, separator);
		if (index == -1) {
			return destString;
		} else if (index == 0) {
			if (destString.equals(remString)) {
				return "";
			} else {
				return destString.substring(remString.length() + 1);
			}
		} else {
			if (destString.length() == index + remString.length()) {
				return destString.substring(0, index - 1);
			} else {
				return destString.substring(0, index)
						+ destString.substring(remString.length() + 1 + index);
			}
		}
	}

	/**
	 * 
	 * 
	 * 将一个对象转换为字符串，如果是时间类型，则转为 "yyyy-MM-dd HH:mm:ss"
	 * 
	 * 
	 * @param obj
	 * @return
	 */
	public static String object2String(Object obj) {
		if (obj == null) {
			return "";
		} else if (obj instanceof Date) {
			return DateTool.getDateTimeStr((Date) obj);
		} else {
			return obj.toString();
		}
	}

	/**
	 * 功能描述：判断输入的字符串是否为纯汉字
	 * 
	 * 
	 * @param str
	 *            传入的字符窜
	 * @return 如果是纯汉字返回true,否则返回false
	 */
	public static boolean isChinese(String str) {
		Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 替换字符串，能能够在HTML页面上直接显示(替换双引号和小于号)
	 * 
	 * @param str
	 *            String 原始字符串
	 * 
	 * @return String 替换后的字符串
	 * 
	 */
	public static String htmlencode(String str) {
		if (str == null) {
			return null;
		}
		return replace("\"", "&quot;", replace("<", "&lt;", str));
	}

	/**
	 * 替换字符串，将被编码的转换成原始码（替换成双引号和小于号）
	 * 
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String htmldecode(String str) {
		if (str == null) {
			return null;
		}

		return replace("&quot;", "\"", replace("&lt;", "<", str));
	}

	/**
	 * 功能描述：在页面上直接显示文本内容，替换小于号，空格，回车，TAB
	 * 
	 * @param str
	 *            String 原始字符串
	 * 
	 * @return String 替换后的字符串
	 * 
	 */
	public static String htmlshow(String str) {
		if (str == null) {
			return null;
		}

		str = replace("<", "&lt;", str);
		str = replace(" ", "&nbsp;", str);
		str = replace("\r\n", "<br/>", str);
		str = replace("\n", "<br/>", str);
		str = replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;", str);
		return str;
	}

	/**
	 * 功能描述：返回指定字节长度的字符串
	 * 
	 * 
	 * @param str
	 *            String 字符串
	 * 
	 * @param length
	 *            int 指定长度
	 * @return String 返回的字符串
	 */
	public static String toLength(String str, int length) {
		if (str == null) {
			return null;
		}
		if (length <= 0) {
			return "";
		}
		try {
			if (str.getBytes("GBK").length <= length) {
				return str;
			}
		} catch (Exception e) {
		}
		StringBuffer buff = new StringBuffer();

		int index = 0;
		char c;
		length -= 3;
		while (length > 0) {
			c = str.charAt(index);
			if (c < 128) {
				length--;
			} else {
				length--;
				length--;
			}
			buff.append(c);
			index++;
		}
		buff.append("...");
		return buff.toString();
	}

	/**
	 * 功能描述：判断是否为整数
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 是整数返回true,否则返回false
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断是否为浮点数，包括double和float
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 是浮点数返回true,否则返回false
	 */
	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?\\d+\\.\\d+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断是不是合法字符 c 要判断的字符
	 */
	public static boolean isLetter(String str) {
		if (str == null || str.length() < 0) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\w\\.-_]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 从指定的字符串中提取Email content 指定的字符串
	 * 
	 * @param content
	 * @return
	 */
	public static String parse(String content) {
		String email = null;
		if (content == null || content.length() < 1) {
			return email;
		}
		// 找出含有@
		int beginPos;
		int i;
		String token = "@";
		String preHalf = "";
		String sufHalf = "";

		beginPos = content.indexOf(token);
		if (beginPos > -1) {
			// 前项扫描
			String s = null;
			i = beginPos;
			while (i > 0) {
				s = content.substring(i - 1, i);
				if (isLetter(s))
					preHalf = s + preHalf;
				else
					break;
				i--;
			}
			// 后项扫描
			i = beginPos + 1;
			while (i < content.length()) {
				s = content.substring(i, i + 1);
				if (isLetter(s))
					sufHalf = sufHalf + s;
				else
					break;
				i++;
			}
			// 判断合法性

			email = preHalf + "@" + sufHalf;
			if (isEmail(email)) {
				return email;
			}
		}
		return null;
	}

	/**
	 * 功能描述：判断输入的字符串是否符合Email样式.
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 是Email样式返回true,否则返回false
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.length() < 1 || email.length() > 256) {
			return false;
		}
		Pattern pattern = Pattern
				.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		return pattern.matcher(email).matches();
	}

	/**
	 * 功能描述：人民币转成大写
	 * 
	 * @param str
	 *            数字字符串
	 * 
	 * @return String 人民币转换成大写后的字符串
	 * 
	 */
	public static String hangeToBig(String str) {
		double value;
		try {
			value = Double.parseDouble(str.trim());
		} catch (Exception e) {
			return null;
		}
		char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
		char[] vunit = { '万', '亿' }; // 段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		long midVal = (long) (value * 100); // 转化成整形

		String valStr = String.valueOf(midVal); // 转化成字符串

		String head = valStr.substring(0, valStr.length() - 2); // 取整数部分

		String rail = valStr.substring(valStr.length() - 2); // 取小数部分

		String prefix = ""; // 整数部分转化的结果

		String suffix = ""; // 小数部分转化的结果

		// 处理小数点后面的数

		if ("00".equals(rail)) { // 如果小数部分为0
			suffix = "整";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "角"
					+ digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来

		}
		// 处理小数点前面的数

		char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
		char zero = '0'; // 标志'0'表示出现过0
		byte zeroSerNum = 0; // 连续出现0的次数

		for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; // 取段内位置

			int vidx = (chDig.length - i - 1) / 4; // 取段位置
			if (chDig[i] == '0') { // 如果当前字符是0
				zeroSerNum++; // 连续0次数递增
				if (zero == '0') { // 标志
					zero = digit[0];
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; // 连续0次数清零
			if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
				prefix += zero;
				zero = '0';
			}
			prefix += digit[chDig[i] - '0']; // 转化该数字表示

			if (idx > 0)
				prefix += hunit[idx - 1];
			if (idx == 0 && vidx > 0) {
				prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿

			}
		}

		if (prefix.length() > 0)
			prefix += '圆'; // 如果整数部分存在,则有圆的字样
		return prefix + suffix; // 返回正确表示
	}

	/**
	 * 功能描述：过滤特殊字符
	 * 
	 * 
	 * @param src
	 * @return
	 */
	public static String encoding(String src) {
		if (src == null)
			return "";
		StringBuilder result = new StringBuilder();
		if (src != null) {
			src = src.trim();
			for (int pos = 0; pos < src.length(); pos++) {
				switch (src.charAt(pos)) {
				case '\"':
					result.append("&quot;");
					break;
				case '<':
					result.append("&lt;");
					break;
				case '>':
					result.append("&gt;");
					break;
				case '\'':
					result.append("&apos;");
					break;
				case '&':
					result.append("&amp;");
					break;
				case '%':
					result.append("&pc;");
					break;
				case '_':
					result.append("&ul;");
					break;
				case '#':
					result.append("&shap;");
					break;
				case '?':
					result.append("&ques;");
					break;
				default:
					result.append(src.charAt(pos));
					break;
				}
			}
		}
		return result.toString();
	}

	/**
	 * 功能描述：反过滤特殊字符
	 * 
	 * @param src
	 * @return
	 */
	public static String decoding(String src) {
		if (src == null)
			return "";
		String result = src;
		result = result.replace("&quot;", "\"").replace("&apos;", "\'");
		result = result.replace("&lt;", "<").replace("&gt;", ">");
		result = result.replace("&amp;", "&");
		result = result.replace("&pc;", "%").replace("&ul", "_");
		result = result.replace("&shap;", "#").replace("&ques", "?");
		return result;
	}

	/**
	 * 功能描述：连接字符串
	 * 
	 * @param targetString
	 *            目标字符串
	 * @param addString
	 *            需要连接的字符串
	 * @return
	 */
	public static String uniteTwoStringBySemicolon(String targetString, String addString) {
		return uniteTwoStringBySemicolon(targetString, addString, ";");
	}

	/**
	 * 功能描述：连接字符串
	 * 
	 * @param targetString
	 *            目标字符串
	 * @param addString
	 *            需要连接的字符串
	 * @param separator
	 *            分隔符
	 * @return
	 */
	public static String uniteTwoStringBySemicolon(String targetString, String addString, String separator) {
		return isEmpty(addString) ? targetString : isEmpty(targetString) ? addString : targetString + (isEmpty(separator) ? ";" : separator)  + addString;
		
	}
	
	/**
	 * 英文字符转义
	 * @param param
	 * @return
	 */
	public  static String handleSpecilChar(String param) {
		return param.replaceAll("'", "’").replaceAll("\"", "”").replaceAll(">", "﹥").replaceAll("<", "﹤");
	}
	
	/**
	 * 截取字符串，去掉后面相同的部分
	 * @param str
	 * @param remove
	 * @return
	 */
	public static String removeEndStr(String str, String remove) {
		return (isEmpty(str) || isEmpty(remove)) ? str : str.substring(0, str.length() - remove.length());
	}


	/**
	 * 数字转字符串
	 * @param number
	 * @return
	 */
	public static String number2String(Number number) {
		return Objects.nonNull(number) ? String.valueOf(number) : null;
	}
	
	
    /**
     * 判断对象、字符、集合、数组是否为空
     * @param obj
     * @return boolean
     * @decription check is null or "" or set，size=0 or list.size=0 or Array.length=0 or Map.isEmpty;
     */
    @SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof java.lang.String) {
            return "".equals(obj);
        } else if (obj instanceof java.util.Set) {
            return ((java.util.Set) obj).size() == 0;
        } else if (obj instanceof java.util.List) {
            return ((java.util.List) obj).size() == 0;
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        } else if (obj instanceof java.util.Map) {
            return ((Map) obj).isEmpty();
        }
        return false;
    }
	
}
