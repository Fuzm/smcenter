package com.irdstudio.smcenter.console.dao.domain;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 数据装载配置表			
 * @author ligm
 * @date 2018-06-13
 */
public class PluginLoadConf extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 数据库表名 */
	private String tableName;
	/** 表中文名称 */
	private String tableCnname;
	/** 数据表类型 */
	private String tableType;
	/** 数据装载方式 */
	private String tableLoadMode;
	/** 供数系统名称 */
	private String upSysname;
	/** 文件路径 */
	private String loadFromFile;
	/** 是否允许条数为零 */
	private String fileRowFlag;
	/** 是否忽略警告 */
	private String loadWarnFlag;
	/** 差异比较方式 */
	private String diffCompMethod;
	/** 比较阀值(%) */
	private int limitPercent;
	/** 差异处理方式 */
	private String diffDealMethod;
	/** 最后更新日期 */
	private String lastModifyDate;
	/** 配置标识符 */
	private String pluginConfId;
	/** 配置顺序 */
	private int confSort;
	/** 字段分隔符 */
	private String loadSeparator;
	/** 装载前执行语句 */
	private String beforeLoadSql;
	/** 装载后执行语句 */
	private String afterLoadSql;
	/** 文件编码格式 */
	private String fileCharset;
	/** 缓冲区大小(K) */
	private int loadBufferSize;
	/** 装载失败处理方式 */
	private String loadFaildDeal;
	/** 指定字段 */
	private String loadFields;
	/** 建表语句 */
	private String createTableDdl;
	

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
	public void setTableLoadMode(String tableLoadMode){
		this.tableLoadMode = tableLoadMode;
	}
	public String getTableLoadMode(){
		return this.tableLoadMode;
	}		
	public void setUpSysname(String upSysname){
		this.upSysname = upSysname;
	}
	public String getUpSysname(){
		return this.upSysname;
	}		
	public void setLoadFromFile(String loadFromFile){
		this.loadFromFile = loadFromFile;
	}
	public String getLoadFromFile(){
		return this.loadFromFile;
	}		
	public void setFileRowFlag(String fileRowFlag){
		this.fileRowFlag = fileRowFlag;
	}
	public String getFileRowFlag(){
		return this.fileRowFlag;
	}		
	public void setLoadWarnFlag(String loadWarnFlag){
		this.loadWarnFlag = loadWarnFlag;
	}
	public String getLoadWarnFlag(){
		return this.loadWarnFlag;
	}		
	public void setDiffCompMethod(String diffCompMethod){
		this.diffCompMethod = diffCompMethod;
	}
	public String getDiffCompMethod(){
		return this.diffCompMethod;
	}		
	public void setLimitPercent(int limitPercent){
		this.limitPercent = limitPercent;
	}
	public int getLimitPercent(){
		return this.limitPercent;
	}		
	public void setDiffDealMethod(String diffDealMethod){
		this.diffDealMethod = diffDealMethod;
	}
	public String getDiffDealMethod(){
		return this.diffDealMethod;
	}		
	public void setLastModifyDate(String lastModifyDate){
		this.lastModifyDate = lastModifyDate;
	}
	public String getLastModifyDate(){
		return this.lastModifyDate;
	}		
	public void setPluginConfId(String pluginConfId){
		this.pluginConfId = pluginConfId;
	}
	public String getPluginConfId(){
		return this.pluginConfId;
	}		
	public void setConfSort(int confSort){
		this.confSort = confSort;
	}
	public int getConfSort(){
		return this.confSort;
	}		
	public void setLoadSeparator(String loadSeparator){
		this.loadSeparator = loadSeparator;
	}
	public String getLoadSeparator(){
		return this.loadSeparator;
	}		
	public void setBeforeLoadSql(String beforeLoadSql){
		this.beforeLoadSql = beforeLoadSql;
	}
	public String getBeforeLoadSql(){
		return this.beforeLoadSql;
	}		
	public void setAfterLoadSql(String afterLoadSql){
		this.afterLoadSql = afterLoadSql;
	}
	public String getAfterLoadSql(){
		return this.afterLoadSql;
	}		
	public void setFileCharset(String fileCharset){
		this.fileCharset = fileCharset;
	}
	public String getFileCharset(){
		return this.fileCharset;
	}		
	public void setLoadBufferSize(int loadBufferSize){
		this.loadBufferSize = loadBufferSize;
	}
	public int getLoadBufferSize(){
		return this.loadBufferSize;
	}		
	public void setLoadFaildDeal(String loadFaildDeal){
		this.loadFaildDeal = loadFaildDeal;
	}
	public String getLoadFaildDeal(){
		return this.loadFaildDeal;
	}		
	public void setLoadFields(String loadFields){
		this.loadFields = loadFields;
	}
	public String getLoadFields(){
		return this.loadFields;
	}		
	public void setCreateTableDdl(String createTableDdl){
		this.createTableDdl = createTableDdl;
	}
	public String getCreateTableDdl(){
		return this.createTableDdl;
	}		

}
