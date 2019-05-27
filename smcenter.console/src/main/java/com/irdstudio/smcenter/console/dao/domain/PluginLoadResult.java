package com.irdstudio.smcenter.console.dao.domain;

import java.math.BigDecimal;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 数据装载结果表			
 * @author ligm
 * @date 2018-06-13
 */
public class PluginLoadResult extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 批次流水号 */
	private String batchSerialNo;
	/** 批次日期 */
	private String batchDate;
	/** 数据库表名 */
	private String tableName;
	/** 表中文名称 */
	private String tableCnname;
	/** 数据表类型 */
	private String tableType;
	/** 供数系统名称 */
	private String upSysname;
	/** 数据装载方式 */
	private String tableLoadMode;
	/** 文件路径 */
	private String loadFromFile;
	/** 文件大小 */
	private BigDecimal fileSize;
	/** 启动时间 */
	private String startTime;
	/** 结束时间 */
	private String endTime;
	/** 运行时长 */
	private BigDecimal costTime;
	/** 读入条数 */
	private int readRows;
	/** 装入条数 */
	private int loadRows;
	/** 拒绝条数 */
	private int rejectRows;
	/** 备注 */
	private String remark;
	/** 导数结果 */
	private String loadResult;
	/** 批次标识 */
	private String batchId;
	

	public void setBatchSerialNo(String batchSerialNo){
		this.batchSerialNo = batchSerialNo;
	}
	public String getBatchSerialNo(){
		return this.batchSerialNo;
	}		
	public void setBatchDate(String batchDate){
		this.batchDate = batchDate;
	}
	public String getBatchDate(){
		return this.batchDate;
	}		
	public void setTableName(String tableName){
		this.tableName = tableName;
	}
	public String getTableName(){
		return this.tableName;
	}		
	public void setTableCnname(String tableCnname){
		this.tableCnname = tableCnname;
	}
	public String getTableCnname(){
		return this.tableCnname;
	}		
	public void setTableType(String tableType){
		this.tableType = tableType;
	}
	public String getTableType(){
		return this.tableType;
	}		
	public void setUpSysname(String upSysname){
		this.upSysname = upSysname;
	}
	public String getUpSysname(){
		return this.upSysname;
	}		
	public void setTableLoadMode(String tableLoadMode){
		this.tableLoadMode = tableLoadMode;
	}
	public String getTableLoadMode(){
		return this.tableLoadMode;
	}		
	public void setLoadFromFile(String loadFromFile){
		this.loadFromFile = loadFromFile;
	}
	public String getLoadFromFile(){
		return this.loadFromFile;
	}		
	public void setFileSize(BigDecimal fileSize){
		this.fileSize = fileSize;
	}
	public BigDecimal getFileSize(){
		return this.fileSize;
	}		
	public void setStartTime(String startTime){
		this.startTime = startTime;
	}
	public String getStartTime(){
		return this.startTime;
	}		
	public void setEndTime(String endTime){
		this.endTime = endTime;
	}
	public String getEndTime(){
		return this.endTime;
	}		
	public void setCostTime(BigDecimal costTime){
		this.costTime = costTime;
	}
	public BigDecimal getCostTime(){
		return this.costTime;
	}		
	public void setReadRows(int readRows){
		this.readRows = readRows;
	}
	public int getReadRows(){
		return this.readRows;
	}		
	public void setLoadRows(int loadRows){
		this.loadRows = loadRows;
	}
	public int getLoadRows(){
		return this.loadRows;
	}		
	public void setRejectRows(int rejectRows){
		this.rejectRows = rejectRows;
	}
	public int getRejectRows(){
		return this.rejectRows;
	}		
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getRemark(){
		return this.remark;
	}		
	public void setLoadResult(String loadResult){
		this.loadResult = loadResult;
	}
	public String getLoadResult(){
		return this.loadResult;
	}		
	public void setBatchId(String batchId){
		this.batchId = batchId;
	}
	public String getBatchId(){
		return this.batchId;
	}		

}
