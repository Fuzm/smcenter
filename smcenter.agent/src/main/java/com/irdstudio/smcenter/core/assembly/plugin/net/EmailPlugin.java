package com.irdstudio.smcenter.core.assembly.plugin.net;

import java.sql.Connection;
import java.sql.SQLException;

import com.irdstudio.smcenter.core.assembly.plugin.AbstractPlugin;

/**
 * 将指定的文件发送给指定的人员
 * @author guangming.li
 * @version 1.0
 * @date 2013-11-12
 */
public class EmailPlugin extends AbstractPlugin{


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
