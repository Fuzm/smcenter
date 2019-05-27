package com.irdstudio.smcenter.core.assembly.plugin.sc;

public class PluginScData {
	
    
    /* 配置标识符 */
	private String pluginConfId;
	/* 配置顺序 */
	private int confSort;
	/* 转存清数描述 */
	private String scDesc;
	/* 处理类型 */
	private String opType;
	/* 处理sql语句 */
	private String opSql;
	/* 写入表名 */
	private String wirteTable;
	/* 区表保存月数 */
	private int saveM;
	/* DS失败处理方式 */
	private String scFaildDeal;
	/* 生效日期 */
	private String validDate;
	/* 失效日期 */
	private String invalidDate;
	/* 最后更新日期 */
	private String lastModifyDate;
	
	public void setPluginConfId(String pluginConfId) {
		this.pluginConfId = pluginConfId;
	}

	public String getPluginConfId() {
		return pluginConfId;
	}

	public void setConfSort(int confSort) {
		this.confSort = confSort;
	}

	public int getConfSort() {
		return confSort;
	}
	public void setScDesc(String scDesc) {
		this.scDesc = scDesc;
	}

	public String getScDesc() {
		return scDesc;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getOpType() {
		return opType;
	}
	public void setOpSql(String opSql) {
		this.opSql = opSql;
	}

	public String getOpSql() {
		return opSql;
	}
	
	public void setWirteTable(String wirteTable) {
		this.wirteTable = wirteTable;
	}

	public String getWirteTable() {
		return wirteTable;
	}
	
	public void setSaveM(int saveM) {
		this.saveM = saveM;
	}

	public int getSaveM() {
		return saveM;
	}
	
	
	
	public void setScFaildDeal(String scFaildDeal) {
		this.scFaildDeal = scFaildDeal;
	}

	public String getScFaildDeal() {
		return scFaildDeal;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setInvalidDate(String invalidDate) {
		this.invalidDate = invalidDate;
	}

	public String getInvalidDate() {
		return invalidDate;
	}

	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public String getLastModifyDate() {
		return lastModifyDate;
	}
}
