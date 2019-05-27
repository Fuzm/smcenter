package com.irdstudio.smcenter.core.assembly.socket.log;
/**
 * 交易日志
 * @author 李广民
 * @version 1.0
 * @date 2008-11-04
 */
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.irdstudio.smcenter.core.assembly.socket.util.PackUtil;
import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;
public class BusiLog extends AbstractBusiLog{
	
	private double beginTime;
	
	public BusiLog(){
		//交易时间默认取当前时间
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(Calendar.getInstance().getTime());
		addValue("f_busi_date", date);
		beginTime = System.currentTimeMillis();
	}

	/**
	 * 交易元素(即交易报文)
	 * @param inUvo
	 */
	public void setBusiElement(UniKeyValueObject inUvo) {
		addValue("f_busi_code", inUvo.getValue("JYM"));
		addValue("f_busi_element", PackUtil.parsePack(inUvo));
		addValue("f_busi_client", inUvo.getValue("CLIENTIP"));
		inUvo.remove("CLIENTIP");
		String ip = inUvo.getValue("USERIP");
		String userId = inUvo.getValue("CURUSERID");
		if (!"".equals(ip)) {
			addValue("f_busi_client", ip);
			inUvo.remove("USERIP");
		}
		if (!"".equals(userId)) {
			addValue("f_busi_userid", userId);
			inUvo.remove("CURUSERID");
		}
	}
	
	/**
	 * 交易结果(即该交易的反馈结果)
	 * @param backUvo
	 */
	public void setBusiResult(UniKeyValueObject backUvo){
		addValue("f_busi_result", PackUtil.parsePack(backUvo));
		double after = System.currentTimeMillis();
		addValue("f_busi_cost_time", new Double((after - beginTime) / 1000.0000));
		addValue("f_busi_flag",backUvo.getValue("YDM"));
	}

	public String getLogTableName() {
		return "s_busi_log";
	}
}
