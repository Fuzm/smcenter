package com.irdstudio.smcenter.core.tinycore.jdbc.meta;
/**
 * @docRoot:
 * 用来描述表的常规信息
 * @author 李广明
 * @version 1.0
 * @date 2006-04-10
 */
import java.sql.*;

import com.irdstudio.smcenter.core.tinycore.log.TLogger;

public class MetaTable {
	
	//定义变量
	private boolean isExist		= false;	//表是否存在
	public int fieldCount 		= 0;		//字段总数
	public String[] fields 		= null;		//字段集合
	public int[] fieldLengths 	= null;		//字段长度集合
	public int[] fieldTypes		= null;		//字段类型集合
	public String[] signs		= null;		//判断该类型是否需要加单引号之类
	
	
	/**
	 * 使用表名实例化一个MetaTable类
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public static MetaTable getInstWithTable(Connection conn, String tableName) {
		String sql = "select * from " + tableName + " where 1=2";
		return new MetaTable(conn, sql);
	}

	/**
	 * 使用查询语句实例化一个MetaTable类
	 * @param conn
	 * @param sql
	 * @return
	 */
	public static MetaTable getInstWithQuery(Connection conn, String sql) {		
		return new MetaTable(conn, "select * from (" + sql + ") a where 1=2");
	}
	
	/**
	 * 构造函数,传入连接及表名,取得表的元数据信息
	 * @param conn
	 * @param tableName
	 */
	private MetaTable(Connection conn,String sql){
		// 不取记录,只取元数据信息
		Statement st = null;
		try {
			st = conn.createStatement();

			// 如果有元数据,置标记
			ResultSetMetaData meta = st.executeQuery(sql).getMetaData();
			this.fieldCount = meta.getColumnCount();
			if (fieldCount > 0) {
				isExist = true;
			}

			// 读取元数据信息,存入到相关的变量
			this.fields = new String[fieldCount];
			this.fieldLengths = new int[fieldCount];
			this.fieldTypes = new int[fieldCount];
			this.signs = new String[fieldCount];
			for (int i = 0; i < fieldCount; i++) {
				// 取得列名
				fields[i] = meta.getColumnName(i + 1);
				// 取得列长
				fieldLengths[i] = meta.getColumnDisplaySize(i + 1);
				// 取得列类型
				fieldTypes[i] = meta.getColumnType(i + 1);
				// 根据数据类型,判断是否需要加单引号
				signs[i] = getSign(fieldTypes[i]);
				// 如果是数字类型，而又允许为空，那么就会出现 如 ,'',,)类似的错误,只能不insert
			}

			// 关闭对象,释放对象
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			// 写日志,但不关闭连接
			TLogger.getLogger("JDBC.META:").error("获取元数据时,发生错误!\n" + sql);
		}
	}
	
	/**
	 * 根据数据类型决定是否需要带单引号
	 * @param dataType
	 * @return
	 */
	public String getSign(int dataType){
		//判断数据类型,数字类型不需要单引号
		String tmp = "";
		switch(dataType){			
			case Types.TINYINT:
			case Types.SMALLINT:
			case Types.INTEGER:								
			case Types.FLOAT:				
			case Types.DECIMAL:
			case Types.DOUBLE:
				//以上数字类型,均不需要单引号
				break;
			default :
				tmp = "'";
				break;
		}
		return tmp;
	}
	
	/**
	 * 返回表是否存在,存在返回True
	 * @return
	 */
	public boolean isTableExist(){
		return isExist;
	}
	
}
