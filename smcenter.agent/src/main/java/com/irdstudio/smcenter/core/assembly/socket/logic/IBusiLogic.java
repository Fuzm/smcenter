package com.irdstudio.smcenter.core.assembly.socket.logic;

import java.sql.SQLException;

import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;

/**
 * 银行承兑汇票退回通知类的处理
 * @author 李广明
 * @version 1.0
 * @date 2008-06-24
 *
 */
public interface IBusiLogic {
	
	public UniKeyValueObject execute(UniKeyValueObject inUvo) throws SQLException;
	
}
