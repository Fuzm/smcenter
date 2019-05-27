package com.irdstudio.smcenter.core.assembly.socket.atom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.irdstudio.smcenter.core.assembly.socket.util.PackUtil;
import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;
/**
 * 虚拟原子交易类,实现了接口中的部分方法
 * 其它原子交易可将此类作为父类进行继承,只需要实现其中的一个方法
 * @author 李广民
 * @version 1.0
 * @date 2008-08-26
 */
public abstract class AbstractAtom implements IAtom{
	
	protected static String LOG_HEAD = "[交易接口]:[原子交易]:";
	
	protected Connection conn = null;			//连接对象
	protected UniKeyValueObject outUvo = null;	//返回结果
	protected UniKeyValueObject inUvo = null;	//入口参数
	
	protected PreparedStatement ps = null;		//操作对象
	protected Statement stmt = null;			//操作对象
	protected ResultSet rs = null;				//结果集
	
	public boolean execute() {
		boolean flag = false;
		try {
			if (conn == null)
				throw new Exception("没有为原子交易设置连接对象");
			if (inUvo == null)
				throw new Exception("没有为原子交易设置参数");
			outUvo = doExecute(conn, inUvo);
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
			outUvo = PackUtil.backErrorPack(getAtomJym(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			outUvo = PackUtil.backErrorPack(getAtomJym(), e.getMessage());
		}
		return flag;
	}

	public UniKeyValueObject getResultPack() {
		return outUvo;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public void setParam(UniKeyValueObject inUvo) {
		this.inUvo = inUvo;
	}
	
	/**
	 * 关闭JDBC对象
	 * @param rs
	 * @param ps
	 * @param stmt
	 */
	protected void close(ResultSet rs, PreparedStatement ps, Statement stmt) {
		try {
			if (rs != null){
				rs.close();
				rs = null;
			}
			if (ps != null){
				ps.close();
				ps = null;
			}
			if (stmt != null){
				stmt.close();
				stmt = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭资源
	 */
	public void closeResource(){
		close(rs,ps,stmt);
	}
	
	/**
	 * 子类只需要实现此方法即可,并且子类并不需要捕获异常
	 * @param conn
	 * @param inUvo
	 */
	public abstract UniKeyValueObject doExecute(Connection conn,
			UniKeyValueObject inUvo) throws SQLException, Exception;
	
	
	/**
	 * 由子类返回交易码
	 * @return
	 */
	public abstract String getAtomJym();
}
