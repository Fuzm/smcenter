package com.irdstudio.smcenter.core.assembly.jxp.conf;
/**
 * @docRoot
 * 用来存放SQL段的信息
 * @author 李广明
 * @version 1.1
 * @date 2006-11-13
 * @modify:
 * 		增加一个可供外部控制解释者的函数
 *
 */
public class SqlSection {

	private String[] sqls = null;
	private boolean flag = true;
	
	/**
	 * 用于暴露给其它程序调用
	 * 用于控制内部的解释器是否执行SQL区的语句
	 * @param isNeedRun
	 */
	public void disableRun(boolean isNeedRun) {
		this.flag = !isNeedRun;
	}
	
	/**
	 * 用于判断是否需要允许执行SQL区的语句
	 * @return
	 */
	public boolean isRun(){
		return this.flag;
	}

	public String[] getSqls() {
		return sqls;
	}

	public void setSqls(String[] sqls) {
		this.sqls = sqls;
	}

}
