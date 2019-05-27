package com.irdstudio.smcenter.core.assembly.plugin.fileop;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.irdstudio.smcenter.core.assembly.plugin.AbstractPlugin;
import com.irdstudio.smcenter.core.util.date.DateCalculate;
import com.irdstudio.smcenter.core.util.date.DateConvert;
/**
 * 文件操作应用插件
 * 包括文件压缩、删除、加密、是否存在等操作(根据配置表)
 * @author guangming.li
 * @version 1.1 
 * @since 1.0 2013-11-07
 * @modify 2014-05-05 增加功能
 */
public class FileOpPlugin extends AbstractPlugin {
	
	/* 存放文件操作配置数据 */
	private List<PluginFileopConf> pfocList;
	/* 文件操作方式描述(分析配置后得到) */
	private String fileOpDesc = "";
	/* 文件操作目标(目录或文件,分析配置后得到) */
	private String fileOpTarget = "";
	/* 文件操作方式常量定义-文件/目录是否存在 */
	private static String FILE_OP_EXIST = "01";
	/* 文件操作方式常量定义-文件/目录压缩 */
	private static String FILE_OP_COMPRESS = "02";
	/* 文件操作方式常量定义-文件/目录删除 */
	private static String FILE_OP_REMOVE = "03";
	/* 文件操作方式常量定义-文件/目录加密 */
	private static String FILE_OP_ENCRYPT = "04";

	@Override
	protected boolean doReadConfigureFromDB(Connection conn,
			String szConfIdentify) throws SQLException {
		PluginFileopConfDao confDao = new PluginFileopConfDao(conn);
		pfocList = confDao.queryWithCond(" where plugin_conf_id='"
				+ szConfIdentify + "'", "order by conf_sort");
		return true;
	}

	/**
	 * 执行文件处理
	 */
	public boolean execute() {

		boolean flag = true;

		// 循环配置的文件操作列表，逐个执行
		for (int i = 0; i < pfocList.size(); i++) {

			PluginFileopConf pfoc = pfocList.get(i);

			// 将上次操作日期放入到系统变量区
			context.vv.addVariable("fileop.target_date", DateConvert
					.toString(DateCalculate.addDays(DateConvert
							.toDateWithSeparate1(pfoc.getLastOpDate()), pfoc
							.getDistanceDay())));
			context.vv.addVariable("fileop.target_date_8", context.vv.getValue(
					"fileop.target_date").replaceAll("-", ""));

			// 根据文件操作方式值来决定做何种操作
			boolean bResult = false;
			fileOpTarget = context.toParseSysVariable(pfoc.getFileTarget())
					.replace('\\', '/');
			if (fileOpTarget.endsWith("/"))
				fileOpTarget = fileOpTarget.substring(0,
						fileOpTarget.length() - 1);
			if (FILE_OP_EXIST.equals(pfoc.getFileOp())) {
				fileOpDesc = "判断文件是否存在";
				bResult = isFileExist();
			} else if (FILE_OP_COMPRESS.equals(pfoc.getFileOp())) {
				fileOpDesc = "文件/目录压缩";
				bResult = doCompressFile(pfoc);
			} else if (FILE_OP_REMOVE.equals(pfoc.getFileOp())) {
				fileOpDesc = "文件/目录删除";
				bResult = doDeleteFile(pfoc);
			} else if (FILE_OP_ENCRYPT.equals(pfoc.getFileOp())) {
				fileOpDesc = "文件/目录加密";
				bResult = doEncryptFile(pfoc);
			}

			// 判断是否调用成功
			if (bResult) {
				this.writeSuccessLog("执行" + fileOpDesc + ":" + fileOpTarget);
				// 更新上次操作日期为今天
				String sql = "update plugin_fileop_conf set last_op_date='"
						+ context.getCurrentDataDate()
						+ "' where plugin_conf_id='"
						+ pfoc.getPluginConfId()
						+ "' and conf_sort=" + pfoc.getConfSort();
				if (!this.executeSql(sql)) {
					// 如果更新上次操作日期出错,记日志并标志为失败
					flag = false;
					this.writeFaildLog("更新上次操作日期");
				}
			} else {
				flag = false;
				this.writeFaildLog("执行" + fileOpDesc + ":" + fileOpTarget);
			}
		}
		return flag;
	}
	/**
	 * 判断文件是否存在
	 * @return
	 */
	private boolean isFileExist() {
		if (initFileParam()) {
			File file = new File(fileOpTarget);
			return file.exists();
		}
		return false;
	}

	/**
	 * 初始化文件操作参数是否符合要求
	 * @return
	 */
	private boolean initFileParam() {
		
		// 如果操作目标为空指针或空值,则记一条失败日志并跳过
		if (fileOpTarget == null || "".equals(fileOpTarget)) {
			context.szLastErrorMsg = fileOpTarget;
			this.writeFaildLog("操作目标为空值,不能进行操作!...");
			return false;
		}

		this.logger
				.info("........................................................................");
		this.logger.info("准备进行" + fileOpDesc + "：" + fileOpTarget + "...");
		
		return true;
	}
	
	/**
	 * 压缩文件/目录
	 * @return
	 */
	private boolean doCompressFile(PluginFileopConf pfoc) {
		if (!initFileParam()) {
			return false;
		}
		// 根据操作周期配置判断是否要到达下次处理日期
		if (!isArrivedNextOpDate(pfoc)) {
			// 如果没有到达处理日期,直接跳过
			return true;
		}
		// TODO:压缩文件
		return true;
	}
	
	/**
	 * 加密文件/目录
	 * @param pfoc
	 * @return
	 */
	private boolean doEncryptFile(PluginFileopConf pfoc) {
		if (!initFileParam()) {
			return false;
		}
		// 根据操作周期配置判断是否要到达下次处理日期
		if (!isArrivedNextOpDate(pfoc)) {
			// 如果没有到达处理日期,直接跳过
			return true;
		}
		// TODO:压缩文件
		return true;
	}

	/**
	 * 删除文件/目录
	 * @param pfoc
	 * @return
	 */
	private boolean doDeleteFile(PluginFileopConf pfoc) {
		if (!initFileParam()) {
			return false;
		}
		// 根据操作周期配置判断是否要到达下次处理日期
		if (!isArrivedNextOpDate(pfoc)) {
			// 如果没有到达处理日期,直接跳过
			return true;
		}
		File file = new File(fileOpTarget);
		return file.delete();
	}
	/**
	 * 判断是否到达下一处理日期
	 * @param pfoc
	 * @return
	 */
	private boolean isArrivedNextOpDate(PluginFileopConf pfoc) {
		
		boolean bFlag = false;
		if (pfoc.getLastOpDate() == null || "".equals(pfoc.getLastOpDate())) {
			// 如果没有设置上次操作日期,默认以当前日期来判断（配置表中要求必须设置该日期)
			pfoc.setLastOpDate(context.getCurrentDataDate());
		}
		
		// 取得下次操作日期(上次操作日期+周期)
		String nextOpDate = DateConvert.toString(DateCalculate.addDays(
				DateConvert.toDateWithSeparate1(pfoc.getLastOpDate()),
				pfoc.getOpCycleDay()));
		
		// 判断下次操作日期是否与今天的数据日期相等或大于
		if (context.getCurrentDataDate().compareTo(nextOpDate) >= 0) {
			bFlag = true;
		}
		
		return bFlag;
	}
}
