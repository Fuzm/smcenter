package com.irdstudio.smcenter.core.assembly.jxp.parser;
/**
 * @docRoot
 * SQL段中的语句解释者(执行者)
 * @author 李广明
 * @version 1.0
 * @date 2006-11-21
 *
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.irdstudio.smcenter.core.assembly.jxp.conf.SqlSection;
import com.irdstudio.smcenter.core.assembly.jxp.util.LogUtil;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;
import com.irdstudio.smcenter.core.util.vo.VariableValue;
public class SqlParser {

	private SqlSection sd = null;
	private VariableValue vv = null;
	
	/**
	 * 构造函数
	 * @param sd
	 */
	public SqlParser(SqlSection sd,VariableValue vv){
		this.sd = sd;
		this.vv = vv;
	}
	
	/**
	 * 获取SQL解释者实例
	 * @param sd
	 * @return
	 */
	public static SqlParser getInstance(SqlSection sd, VariableValue vv) {
		return new SqlParser(sd,vv);
	}
	
	public void run(){
		
		String sql = "";
		double before = System.currentTimeMillis();
		String sqls[] = sd.getSqls();
		LogUtil.out("执行SQL区中的SQL语句...");
		Connection conn = null;
		try {
			conn = TConnPool.getDefaultPool().getConnection();
			for (int i = 0; i < sqls.length; i++) {
				
				if (sqls[i] == null 
						|| "".equals(sqls[i].trim()))
					continue;
				
				// 定义变量
				Statement st = null;
				
				// 主体,执行SQL命令
				try {
					//将SQL语句中的变量解释出来
					sql = ParamParser.parseVariable(sqls[i], vv, null);
					LogUtil.out(sql);
					st = conn.createStatement();
					st.execute(sql);
				} catch (SQLException e) {
					LogUtil.log("执行:" 
							+ sql
							+ "出错!\n" );
					LogUtil.log(e.getMessage(),"ISO-8859-1");
				}
				finally {		
					if (st != null) {
						try {
							st.close();
						} catch (SQLException e) {
						}
					}
					st = null;
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			TConnPool.getDefaultPool().releaseConnection(conn);
		}
		
		
		double after = System.currentTimeMillis();
		LogUtil.out("need ["+(after-before)/1000+"] second");
	}
}
