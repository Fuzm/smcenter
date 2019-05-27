package com.irdstudio.smcenter.core.assembly.socket.util;

import java.util.Iterator;
import java.util.Map;

import com.irdstudio.smcenter.core.assembly.socket.element.BusinessConf;
import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;

/**
 * 组包辅助类,用于将需要发送的数据进行组包
 * 及将接收到的数据进行拆包
 * @author 李广明
 * @version 1.0
 * @date 2008-05-20
 *
 */
public class PackUtil {
	
	public static final String NO_BUSINESS 			= "不支持该交易";
	public static final String NO_RETURN_DATA 		= "交易执行完成,但无任何数据返回";
	public static final String CUSTOM_CLASS_FAILD 	= "使用自定义类处理交易失败";
	public static final String INSTANCE_ERROR	 	= "实例化该交易的处理类时失败";
	public static final String CLASS_NOT_FOUND	 	= "该交易的处理类未找到";

	/**
	 * 用于将字符串解析成包
	 * @param inMsg
	 * @return
	 */
	public final static UniKeyValueObject getPack(String inMsg){
		UniKeyValueObject uvo = new UniKeyValueObject();
		String[] packs = inMsg.split(BusinessConf.memberSplit);
		for(int i=0;i<packs.length;i++){
			String datas[] = packs[i].split(BusinessConf.keyValueSplit);
			if (datas.length > 1)
				uvo.addVariable(datas[0], datas[1]);
		}
		return uvo;
	}
	
	/**
	 * 用于将需要发送的数据拆成字符串
	 * @param uvo
	 * @return
	 */
	public final static String parsePack(UniKeyValueObject uvo){
		Map varMap = uvo.getMap();
		StringBuffer sb = new StringBuffer();
		Iterator it = varMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			sb.append(key);
			sb.append(BusinessConf.keyValueSplit);
			sb.append(value);
			sb.append(BusinessConf.memberSplit);
		}
//		if (sb.length() > 0)
//			sb.deleteCharAt(sb.length() - 1);		
		String tmpStr = "0000" + sb.toString().getBytes().length;
		return tmpStr.substring(tmpStr.length() - 4) + sb.toString();
	}

	/**
	 * 用于返回一个包含错误信息的包
	 * @param jym
	 * @param errMsg
	 * @return
	 */
	public final static UniKeyValueObject backErrorPack(String jym,String errMsg) {
		UniKeyValueObject outUvo = new UniKeyValueObject();
		outUvo.addVariable("JYM", jym);
		outUvo.addVariable("YDM", "01");
		outUvo.addVariable("YDXX", errMsg);
		return outUvo;
	}
	
	/**
	 * 用于返回一个包含错误信息的包
	 * @param jym
	 * @param errMsg
	 * @return
	 */
	public final static UniKeyValueObject backErrorPack(String errMsg) {
		return backErrorPack("0000",errMsg);
	}	
	
	/**
	 * 返回交易成功信息
	 * @param jym
	 * @return
	 */
	public final static UniKeyValueObject backSuccessfulPack(String jym){
		UniKeyValueObject outUvo = new UniKeyValueObject();
		outUvo.addVariable("JYM", jym);
		outUvo.addVariable("YDM", "00");
		outUvo.addVariable("YDXX", "交易成功");
		return outUvo;
	}
	
	/**
	 * 返回交易成功信息
	 * @return
	 */
	public final static UniKeyValueObject backSuccessfulPack(){
		return backSuccessfulPack("0000");
	}	
	
	/**
	 * 专用于返回参数校验失败的错误包
	 * @param jym
	 * @param paramDesc
	 * @param paramName
	 * @return
	 */
	public final static UniKeyValueObject backParamErrorPack(String jym,
			String paramDesc, String paramName) {
		StringBuffer errMsg = new StringBuffer("参数[");
		errMsg.append(paramDesc).append(":");
		errMsg.append(paramName);
		errMsg.append("]不符合交易要求");
		return backErrorPack(jym, "");
	}
	
	public static void main(String[] args){
		XmlLoadUtil.loadFromXml("busi.cfg.xml");
		UniKeyValueObject uvo = PackUtil.getPack("JYM 10010" + (char) 13
				+ "JJH 014579741" + (char) 13 + "DKJE 1000.08");
		System.out.println(PackUtil.parsePack(uvo));
	}

}
