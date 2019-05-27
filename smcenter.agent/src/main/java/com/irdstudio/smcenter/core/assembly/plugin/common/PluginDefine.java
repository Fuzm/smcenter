package com.irdstudio.smcenter.core.assembly.plugin.common;
/**
 * 数据表定义类[表名:插件定义]
 * @author 代码自动生成
 * @version 1.0
 * @date 2013-10-10
 */
public class PluginDefine {

	/* 插件ID */
	private int pluginId;
	/* 应用插件名称 */
	private String pluginName;
	/* 应用插件处理类 */
	private String pluginDealClass;
	/* 应用插件描述 */
	private String pluginDesc;
	/* 是否需要其它数据源变量 */
	private String needOtherDsVar;

	public String toString() {
		return "PluginDefine [pluginId=" + pluginId + ", pluginName="
				+ pluginName + ", pluginDealClass=" + pluginDealClass
				+ ", pluginDesc=" + pluginDesc + ", needOtherDsVar="
				+ needOtherDsVar + "]";
	}

	public String getNeedOtherDsVar() {
		return needOtherDsVar;
	}

	public void setNeedOtherDsVar(String needOtherDsVar) {
		this.needOtherDsVar = needOtherDsVar;
	}
	
	public int getPluginId() {
		return pluginId;
	}

	public void setPluginId(int pluginId) {
		this.pluginId = pluginId;
	}

	public String getPluginName() {
		return pluginName;
	}

	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}

	public String getPluginDealClass() {
		return pluginDealClass;
	}

	public void setPluginDealClass(String pluginDealClass) {
		this.pluginDealClass = pluginDealClass;
	}

	public String getPluginDesc() {
		return pluginDesc;
	}

	public void setPluginDesc(String pluginDesc) {
		this.pluginDesc = pluginDesc;
	}
}
