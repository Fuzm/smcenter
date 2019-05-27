package com.irdstudio.smcenter.core.assembly.etl.export.txt;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.irdstudio.smcenter.core.assembly.etl.export.Export;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;
import com.irdstudio.smcenter.core.tinycore.log.ILogger;
import com.irdstudio.smcenter.core.tinycore.log.TLogger;
/**
 * @docRoot:
 * 从数据库中导出记录成文件文件
 * @author Administrator
 *
 */

public class ExportTxt extends Export{
	
	//定义变量
	private String tableName = null;
	private String fullFilePath = null;
	private ILogger logger = null;
	
	/**
	 * 构造函数,传入表名及导入后生成的文件名
	 * @param tableName
	 * @param fullFilePath
	 */
	public ExportTxt(String tableName,String fullFilePath){
		this.tableName = tableName;
		this.fullFilePath = fullFilePath;
		this.logger = TLogger.getLogger("Export");
	}
	
	/**
	 * 导出数据
	 */
	public boolean run() {
		
		BufferedOutputStream fo = null;
		
		//建立要进行出导出的文件流对象
		try {
	          File newFile = new File(fullFilePath);	          
	          if (newFile.exists()){
	        	  newFile.delete();
	        	  newFile.createNewFile();
	          }
	          fo = new BufferedOutputStream(
						new FileOutputStream(newFile));
		}
		catch(IOException e){
			e.printStackTrace();
			logger.info("导出数据时初始化目标文件出错!");
			return false;
		}
		
		//从连接池中取出连接
		Connection conn = null;
		try {
			conn = TConnPool.getDefaultPool().getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Statement st = null;		
		
		//循环取出数据,进行导出
		try {
			st = conn.createStatement();
			st.execute("select * from " + tableName);
			ResultSet rs = st.getResultSet();
			int iCount = rs.getMetaData().getColumnCount();
			StringBuffer sb = null;
			while(rs.next()){
				sb = new StringBuffer();
				
				for (int i = 1; i <= iCount; i++) {
					sb.append(rs.getString(i));
					sb.append("|");
				}
				
				sb.append("\n");
				
				try {
					fo.write(new String(sb.toString().getBytes("ISO-8859-1"),
							"GBK").getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				sb = null;
			}			
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("从数据库中导出数据时出错!",e);
		}
		finally{
			TConnPool.getDefaultPool().releaseConnection(conn);
			try {
				fo.flush();
				fo.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("关闭文件对象时出错!",e);
			}
		}
		return true;
	}
}
