package com.irdstudio.smcenter.core.util.sequence;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.irdstudio.smcenter.core.util.pub.ConnectionUtil;
import com.irdstudio.smcenter.core.util.pub.UUIDUtil;

public class SequenceUtil {
	
	private static String getCurrentDate_yyyymmdd() throws Exception {
		//return new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		return ConnectionUtil.getSysTime().replaceAll("-", "");
	}
	
	/**
	 * 押品编号生成序列
	 * @param orgid    机构号
	 * @param connection
	 * @return
	 * @throws EMPException
	 */
	public static synchronized String getGuarantySeq(String orgid, Connection connection) throws Exception {
		return "GUA"+orgid+getCurrentDate_yyyymmdd() + 
			String.format("%04d", new Long(SequenceService.getNextValue("GUA", orgid, connection)));
	}
	
	public static synchronized String getExcelFileSeq(Connection connection) throws Exception {
		return "EXCEL"+getCurrentDate_yyyymmdd() + 
			String.format("%06d", new Long(SequenceService.getNextValue("EXCEL", "ALL", connection)));
	}
	
	public static synchronized String getChannelSeq(String type, Connection connection) throws Exception {
		return "c"+new SimpleDateFormat("MMdd").format(Calendar.getInstance().getTime()) + 
			String.format("%07d", new Long(SequenceService.getNextValue("CHANNEL", "ALL", connection)));
	}
		
	public static synchronized String getDemo1(Connection connection) throws Exception {
		return getCurrentDate_yyyymmdd() + 
			String.format("%09d", new Long(SequenceService.getNextValue(null, "ALL", connection)));
	}
	
	/**
	 * 生成综合服务系统管理日志流水号
	 * @param connection
	 * @return
	 * @throws EMPException
	 */
	public static synchronized String getSrvsAdminSerialNo(Connection connection) throws Exception {
		return "MG"
				+ getCurrentDate_yyyymmdd()
				+ String.format("%07d", new Long(SequenceService.getNextValue(
						"IMSRVS_ADMIN", "ALL", connection)));
	}
	
	/**
	 * 生成综合服务系统服务调用流水号
	 * @param connection
	 * @return
	 * @throws EMPException
	 */
	public static synchronized String getSrvsCalledSerialNo(Connection connection) throws Exception {
		return "SN"
				+ getCurrentDate_yyyymmdd()
				+ String.format("%07d", new Long(SequenceService.getNextValue(
						"IMSRVS_CALLED", "ALL", connection)));
	}
	
	/**
	 * 生成风险预警唯一编号
	 * @param connection
	 * @return
	 * @throws EMPException
	 */
	public static synchronized String getGrtRiskInfoSerialNo(Connection connection) throws Exception{
		return "RSK"
		       + getCurrentDate_yyyymmdd()
		       + String.format("%07d", new Long(SequenceService.getNextValue(
						"RSK", "ALL", connection)));
	}
	
	/**
	 * 生成日终批量唯一编号
	 * @param connection
	 * @return
	 * @throws EMPException
	 * @author wujun1
	 */
	public static synchronized String getBatchSerialNo(Connection connection) throws Exception{
		return "RZ"
		       + getCurrentDate_yyyymmdd()
		       + String.format("%07d", new Long(SequenceService.getNextValue(
						"BAT", "ALL", connection)));
	}
	
	
	public static synchronized String getUnidPk( ) {
		return UUIDUtil.getUUID();
	}
	
	public static synchronized String getCommonSerialNo(Connection connection) throws Exception{
		return "LS"
		       + getCurrentDate_yyyymmdd()
		       + String.format("%09d", new Long(SequenceService.getNextValue(
						"LS", "ALL", connection)));
	}

	/**
	 * 费用计划表主键
	 */
	public static synchronized String getPvpFeePlanNo(Connection connection) throws Exception{
		return "FEE"
		       + getCurrentDate_yyyymmdd()
		       + String.format("%06d", new Long(SequenceService.getNextValue(
						"LS", "ALL", connection)));
	}
	/**
	 * 还款登记表主键
	 */
	public static synchronized String getRetNo(Connection connection) throws Exception{
		return "LS"
		       + getCurrentDate_yyyymmdd()
		       + String.format("%06d", new Long(SequenceService.getNextValue(
						"LS", "ALL", connection)));
	}
}
