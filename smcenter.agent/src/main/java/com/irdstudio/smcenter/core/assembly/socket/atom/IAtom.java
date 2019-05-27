package com.irdstudio.smcenter.core.assembly.socket.atom;

import java.sql.Connection;

import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;

/**
 * 原子交易类(所有原子交易的接口类)
 * @author 李广民
 * @version 1.0
 * @date 2008-08-26
 */
public interface IAtom {
	
	/**
	 * 用于向原子交易传递参数
	 * @param inUvo
	 */
	public void setParam(UniKeyValueObject inUvo);
	
	/**
	 * 用于向原子交易传递连接对象
	 * @param conn
	 */
	public void setConnection(Connection conn);
	
	/**
	 * 用于执行原子交易
	 * @return 返回成功或失败
	 * 并可以通过getResultPack()方法获取详细错误信息
	 */
	public boolean execute();

	/**
	 * 返回执行结果信息包
	 * @return
	 */
	public UniKeyValueObject getResultPack();
	
	/**
	 * 关闭资源
	 */
	public void closeResource();
}
