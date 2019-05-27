package com.irdstudio.smcenter.core.assembly.plugin.dataload;/** * 01.数据装载配置表(plugin_load_conf) * @author 代码自动生成 * @version 1.0 * @date 2014-04-29 */public class PluginLoadConf {	/* 配置标识符 */	private String pluginConfId;	/* 配置顺序 */	private int confSort;	/* 数据表名称 */	private String tableName;	/* 表中文名称 */	private String tableCnname;	/* 数据表类型 */	private String tableType;	/* 供数系统名称 */	private String upSysname;	/* 数据装载方式 */	private String tableLoadMode;	/* 数据文件 */	private String loadFromFile;	/* 文件编码格式 */	private String fileCharset;	/* 缓冲区大小(K) */	private int loadBufferSize;	/* 数据分隔符 */	private String loadSeparator;	/* 指定字段 */	private String loadFields;	/* 建表语句 */	private String createTableDdl;	/* 装载前执行语句 */	private String beforeLoadSql;	/* 装载后执行语句 */	private String afterLoadSql;	/* 是否允许条数为零 */	private String fileRowFlag;	/* 是否忽略警告 */	private String loadWarnFlag;	/* 差异比较方式 */	private String diffCompMethod;	/* 比较阀值(%) */	private int limitPercent;	/* 差异处理方式 */	private String diffDealMethod;	/* 装载失败处理方式 */	private String loadFaildDeal;	/* 最后更新日期 */	private String lastModifyDate;	public PluginLoadConf(){	}	public void setPluginConfId(String pluginConfId) {		this.pluginConfId = pluginConfId;	}	public String getPluginConfId() {		return pluginConfId;	}	public void setConfSort(int confSort) {		this.confSort = confSort;	}	public int getConfSort() {		return confSort;	}	public void setTableName(String tableName) {		this.tableName = tableName;	}	public String getTableName() {		return tableName;	}	public void setTableCnname(String tableCnname) {		this.tableCnname = tableCnname;	}	public String getTableCnname() {		return tableCnname;	}	public void setTableType(String tableType) {		this.tableType = tableType;	}	public String getTableType() {		return tableType;	}	public void setUpSysname(String upSysname) {		this.upSysname = upSysname;	}	public String getUpSysname() {		return upSysname;	}	public void setTableLoadMode(String tableLoadMode) {		this.tableLoadMode = tableLoadMode;	}	public String getTableLoadMode() {		return tableLoadMode;	}	public void setLoadFromFile(String loadFromFile) {		this.loadFromFile = loadFromFile;	}	public String getLoadFromFile() {		return loadFromFile;	}	public void setFileCharset(String fileCharset) {		this.fileCharset = fileCharset;	}	public String getFileCharset() {		return fileCharset;	}	public void setLoadBufferSize(int loadBufferSize) {		this.loadBufferSize = loadBufferSize;	}	public int getLoadBufferSize() {		return loadBufferSize;	}	public void setLoadSeparator(String loadSeparator) {		this.loadSeparator = loadSeparator;	}	public String getLoadSeparator() {		return loadSeparator;	}	public void setLoadFields(String loadFields) {		this.loadFields = loadFields;	}	public String getLoadFields() {		return loadFields;	}	public void setCreateTableDdl(String createTableDdl) {		this.createTableDdl = createTableDdl;	}	public String getCreateTableDdl() {		return createTableDdl;	}	public void setBeforeLoadSql(String beforeLoadSql) {		this.beforeLoadSql = beforeLoadSql;	}	public String getBeforeLoadSql() {		return beforeLoadSql;	}	public void setAfterLoadSql(String afterLoadSql) {		this.afterLoadSql = afterLoadSql;	}	public String getAfterLoadSql() {		return afterLoadSql;	}	public void setFileRowFlag(String fileRowFlag) {		this.fileRowFlag = fileRowFlag;	}	public String getFileRowFlag() {		return fileRowFlag;	}	public void setLoadWarnFlag(String loadWarnFlag) {		this.loadWarnFlag = loadWarnFlag;	}	public String getLoadWarnFlag() {		return loadWarnFlag;	}	public void setDiffCompMethod(String diffCompMethod) {		this.diffCompMethod = diffCompMethod;	}	public String getDiffCompMethod() {		return diffCompMethod;	}	public void setLimitPercent(int limitPercent) {		this.limitPercent = limitPercent;	}	public int getLimitPercent() {		return limitPercent;	}	public void setDiffDealMethod(String diffDealMethod) {		this.diffDealMethod = diffDealMethod;	}	public String getDiffDealMethod() {		return diffDealMethod;	}	public void setLoadFaildDeal(String loadFaildDeal) {		this.loadFaildDeal = loadFaildDeal;	}	public String getLoadFaildDeal() {		return loadFaildDeal;	}	public void setLastModifyDate(String lastModifyDate) {		this.lastModifyDate = lastModifyDate;	}	public String getLastModifyDate() {		return lastModifyDate;	}}