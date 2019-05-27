/**
 * <p>Title: 资源接口</p>
 * <p>Description: 资源监控层</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 北京易初电子技术有限公司</p>
 * @author 黄瑞文
 * @version 1.0
 * 2006-11-22
 */

package com.irdstudio.smcenter.core.assembly.imdbcp.resource;

import java.sql.SQLException;

/**
 * 资源监控层，资源接口
 * @author hrw
 */
public interface RsInterface {
	
	/** 最后执行的SQL */
	public String getLastSql();
	
	/** 最后执行的SQL时间 */
	public long getLastSqlTime();
	
	/**
	 * 设置最后执行的SQL
	 * @param sql
	 */
	public void setLastSql(String sql);
	
	/** 连接 */
	public RsConnection getBaseConnnection();

	/** 连接 */
	public void setBaseConnnection(RsConnection conn);
	
	/** 关闭资源 */
	public void close() throws SQLException;
}
