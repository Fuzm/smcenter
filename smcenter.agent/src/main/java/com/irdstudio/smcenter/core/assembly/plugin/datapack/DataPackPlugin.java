package com.irdstudio.smcenter.core.assembly.plugin.datapack;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.irdstudio.smcenter.core.assembly.jxp.conf.ConfigureEntry;
import com.irdstudio.smcenter.core.assembly.jxp.loader.MainLoader;
import com.irdstudio.smcenter.core.assembly.jxp.parser.CoreParser;
import com.irdstudio.smcenter.core.assembly.jxp.util.LogUtil;
import com.irdstudio.smcenter.core.assembly.plugin.AbstractPlugin;
import com.irdstudio.smcenter.core.util.vo.VariableValue;
/**
 * 根据配置生成要报送的数据文件
 * @author guangming.li
 * @version 1.0
 * @date 2013-11-12
 */
public class DataPackPlugin extends AbstractPlugin{
	
	/* 组包配置信息 */
	List<PluginDatapackConf> dpcList;

	@Override
	protected boolean doReadConfigureFromDB(Connection conn,
			String szConfIdentify) throws SQLException {
		PluginDatapackConfDao dpcDao = new PluginDatapackConfDao(conn);
		dpcList = dpcDao.queryPluginDatapackConfWithCond(
				" where plugin_conf_id='" + szConfIdentify + "'",
				"order by conf_sort");
		if (dpcList.size() < 1) {
			context.szLastErrorMsg = "未读取到配置标识为：" + szConfIdentify + "的组包配置!";
			return false;
		} else {
			return true;
		}
	}	

	/**
	 * 根据配置循环读取组包配置并行成相应的报文文件
	 */
	public boolean execute() {

		boolean flag = true;
		
		// 循环配置的组包配置列表，逐个执行
		for (int i = 0; i < dpcList.size(); i++) {

			PluginDatapackConf pdc = dpcList.get(i);
			// 判断否在有效期内,不在有效期内的配置直接跳过
			if (isInValidConf(context.getCurrentDataDate(), pdc.getValidDate(),
					pdc.getInvalidDate())) {
				continue;
			}
			
			boolean curFlag = true;
			this.logger.info("准备生成数据报文：" + pdc.getPackDesc() + "...");
				
			// 准备可供报文配置文件使用的系统变量
			VariableValue vv = context.vv;
			vv.addVariable("report_date_10", context.getCurrentDataDate());
			vv.addVariable("report_date_8", context.getCurrentDataDate()
					.replaceAll("-", ""));
			vv.addVariable("report_year_4", context.getCurrentDataDate()
					.substring(0, 4));
			vv.addVariable("report_month_2", context.getCurrentDataDate()
					.substring(5, 7));
			
			// 解释目标文件名称中的变量,得到要生成的完整文件名称
			String targetFile = context.toParseSysVariable(pdc
					.getPackGenerateFile());
			// 配置文件名称,不可以在其中定义变量
			String configFile = context.toParseSysVariable(pdc
					.getPackConfigFile());
			
			// 生成目标报文文件
		    OutputStreamWriter fo = null;
			try {
				
				// 装载数据报文生成配置文件
				LogUtil.setLogger(this.logger);
				ConfigureEntry cxd = MainLoader.loadCoreXml(configFile);
				if (cxd == null) {
					throw new Exception("报文组包配置文件不存在或文件格式存在错误!");
				}
				
				// 初始化要生成的目标文件
				fo = new OutputStreamWriter(new FileOutputStream(targetFile),
						pdc.getPackFileEncoding());
				fo.write("<?xml version=\"1.0\" encoding=\""
						+ pdc.getPackFileEncoding() + "\" standalone=\"no\"?>");
				
				// 调用解释器执行
				CoreParser parser = new CoreParser();
				parser.linkConfigureEntry(cxd);
				parser.linkFileObject(fo);
				parser.linkVariableValue(vv);
				if (parser.run()) {
					this.writeSuccessLog("生成数据报文文件");
				} else {
					this.writeFaildLog("生成数据报文文件", targetFile);
				}
				
			} catch (FileNotFoundException e) {
				curFlag = false;
				context.szLastErrorMsg = e.getMessage();
				e.printStackTrace();
			} catch (IOException e) {
				curFlag = false;
				context.szLastErrorMsg = e.getMessage();				
				e.printStackTrace();
			} catch (Exception e){
				curFlag = false;
				context.szLastErrorMsg = e.getMessage();				
				e.printStackTrace();
			} finally{
				if(fo != null){
					try {
						fo.close();
						fo = null;
					} catch (IOException e) {
						this.writeFaildLog("关闭生成的数据报文文件",e.getMessage());
						e.printStackTrace();
					}
				}
				// 记日志
				if (curFlag) {
					this.writeSuccessLog("根据" + configFile + "生成" + targetFile);
				} else {
					this.writeFaildLog("根据" + configFile + "生成" + targetFile);
					flag = curFlag;
				}
			}
		}
		return flag;
	}
}
