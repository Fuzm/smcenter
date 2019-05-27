package com.irdstudio.smcenter.core.assembly.plugin.net;

import java.sql.Connection;
import java.sql.SQLException;

import com.irdstudio.smcenter.core.assembly.plugin.AbstractPlugin;
/**
 * FTP应用插件,可将指定的文件上传到指定的FTP服务器
 * 或下载指定服务器上的文件到本地的目录
 * @author guangming.li
 * @date 2013-11-12
 * @version 1.0
 */
public class FtpPlugin extends AbstractPlugin{

	
	public boolean execute() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean doReadConfigureFromDB(Connection conn,
			String szConfIdentify) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
