package com.irdstudio.smcenter.core.tinycore.jdbc.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.irdstudio.smcenter.core.tinycore.jdbc.executor.SafeReleaseUtil;
import com.irdstudio.smcenter.core.tinycore.jdbc.meta.MetaTable;
import com.irdstudio.smcenter.core.tinycore.log.TLogger;

/**
 * 数据库表与表之前的数据复制或移动
 * @author guangming.li & yuanping.zhang
 * @version 1.0
 * @date 2014-04-21
 */
public class TableMove {
	
	/* 源表名 */
	private String srcTableName = "";
	/* 目标表名 */
	private String destTableName = "";
	/* 数据库连接对象 */
	private Connection conn = null;

	/* 获取源表列信息 */
	private MetaTable srcMetaTable = null;
	/* 获取目标表列信息 */
	private MetaTable destMetaTable = null;	
	/* 保存数据移动条件(设置默认条件,防止误使用) */
	private String condition = " where 1=2";

	/* 字段映射关系 */
	private Map<String, Object> map = new HashMap<String, Object>();
	
	/**
	 * 构造函数 
	 * @param conn
	 * @param srcTableName
	 * @param destTableName
	 * @throws SQLException
	 */
	public TableMove(Connection conn, String srcTableName, String destTableName)
			throws SQLException {
		this.conn = conn;
		this.srcMetaTable = MetaTable.getInstWithTable(conn, srcTableName);
		this.destMetaTable = MetaTable.getInstWithTable(conn, destTableName);
		this.srcTableName = srcTableName;
		this.destTableName = destTableName;
		// 设置字段映射
		addFullMapping();
	}
	
	public TableMove(Connection conn, String srcTableName, String destTableName,
			boolean isAutoFullMapping) throws SQLException {
		this.conn = conn;
		this.srcMetaTable = MetaTable.getInstWithTable(conn, srcTableName);
		this.destMetaTable = MetaTable.getInstWithTable(conn, destTableName);
		this.srcTableName = srcTableName;
		this.destTableName = destTableName;
		if (isAutoFullMapping) {
			// 设置字段映射
			addFullMapping();
		}
	}
	
	
	/**
	 * 设置数据转移条件
	 * @param cond
	 */
	public void setCondition(String cond) {
		// 若条件不为空，则将条件拼接到执行sql语句后面
		if (StringUtils.isNotBlank(cond)) {
			condition = " " + cond;
		}
	}

	/**
	 * 增加字段映射关系
	 * @param destField
	 * @param srcField
	 */
	public void addFieldMapping(String destField,Object srcField) {
		// 特殊处理某些字段映射
		// 若已经存在该映射，则先将map中移除该映射，然后重新设置映射关系
		destField = destField.toUpperCase();
		if (map.get(destField) != null) {
			map.remove(destField);
		}

		map.put(destField, srcField);
		
	}
	
	/**
	 * 增加字段与值的映射关系
	 * @param destField
	 * @param srcValue
	 */
	public void addValueMapping(String destField,Object srcValue) {
		//update by 2018/11/15 Oracle统一用大写
		destField = destField.toUpperCase();
		// 特殊处理某些字段映射
		// 若已经存在该映射，则先将map中移除该映射，然后重新设置映射关系
		//destField = destField.toUpperCase();
		if (map.get(destField) != null) {
			map.remove(destField);
		}
		// 如果源值为字符串型,则加上单引号，其它类型则不加
		if (srcValue instanceof String) {
			map.put(destField, "'" + srcValue + "'");
		} else {
			map.put(destField, srcValue);
		}		
	}	
	
	/**
	 * 源表与目标之间进行全字段映射
	 * 以源表字段为准，适合表结构大致相同的情况下使用
	 */
	public void addFullMapping()throws SQLException{
		try {
			
			if(!srcMetaTable.isTableExist()){
				throw new Exception("源表【"+this.srcTableName+"】不存在！");
			}
			
			if(!destMetaTable.isTableExist()){
				throw new Exception("目标表【"+this.destTableName+"】不存在！");
			}
			
			//获取源表字段信息
			int srcFieldCount = srcMetaTable.fieldCount;//源表字段总数
			String[] srcFields = srcMetaTable.fields;//源表字段集合
			int[] srcFieldLengths = srcMetaTable.fieldLengths;//源表字段长度集合
			int[] srcFieldTypes = srcMetaTable.fieldTypes;//源表字段类型集合
			
			//获取目标表字段信息
			int destFieldCount = destMetaTable.fieldCount;//源表字段总数
			String[] destFields = destMetaTable.fields;//源表字段集合
			int[] destFieldLengths = destMetaTable.fieldLengths;//源表字段长度集合
			int[] destFieldTypes = destMetaTable.fieldTypes;//源表字段类型集合
			String[] destSigns = destMetaTable.signs;//目标表字段是否需要加单引号
			
			//以表目标表字段为准进行全字段映射
			for(int i=0;i<destFieldCount;i++){
				//目标表字段名
				String destField = destFields[i];
				//目标表字段长度
				int destFieldLength = destFieldLengths[i];
				//目标表字段类型
				int destFieldType = destFieldTypes[i];
				//目标表字段是否需要加单引号
				String srcSign = destSigns[i];
				//循环源表字段信息
				for(int j=0;j<srcFieldCount;j++){
					//源表字段名
					String srcField = srcFields[j];
					//源表字段长度
					int srcFieldLength = srcFieldLengths[j];
					//源表字段类型
					int srcFieldType = srcFieldTypes[j];
					//若源表字段名，字段长度，字段类型均与目标表相同，则建立映射关系
					if(srcField.equals(destField) && srcFieldLength == destFieldLength && srcFieldType == destFieldType){
						map.put(destField, srcField);
						break;
					}
				}
				// 若在目标表中没有找到字段映射的，则设置字段的值
				if (map.get(destField) == null) {
					// 若是需要加单引号的字段,则设置字段映射为两个单引号
					if (StringUtils.isNotBlank(srcSign)) {
						map.put(destField, "''");
					} else {
						// 若不需要加单引号的字段，则设置字段映射为0
						map.put(destField, 0);
					}
				}
			}
		} catch (Exception e) {
			//记录日志
			TLogger.getLogger("util").info(
					"源表与目标之间进行全字段映射错误：\n" + e.getMessage());
			throw new SQLException("addFullMapping is Wrong!"+e.getMessage());
		}
	}
	
	/**
	 * 构造执行sql语句
	 * @return
	 */
	private String generateInsertSql() {

		StringBuffer sql = new StringBuffer("insert into " + destTableName
				+ " (");
		// 目标表字段字符串
		StringBuffer destSqlStr = new StringBuffer();
		// 源表字段字符串
		StringBuffer srcSqlStr = new StringBuffer();

		// 循环查询源表的字段集合
		for (int i = 0; i < destMetaTable.fieldCount; i++) {
			// 源表插入表中字段拼接
			String destField = destMetaTable.fields[i];
			destSqlStr.append(destField);
			// 目标表查询字段拼接
			srcSqlStr.append(map.get(destField));
			if (i != destMetaTable.fields.length - 1) {
				destSqlStr.append(",");
				srcSqlStr.append(",");
			}
		}

		// 拼接执行sql
		sql.append(destSqlStr).append(" ) select ");
		sql.append(srcSqlStr).append(" from ").append(srcTableName);
		sql.append(condition);
		TLogger.getLogger("util").info(sql.toString());
		return sql.toString();
	}
	
	
	
	/**
	 * 执行复制
	 * 从源表复制数据到目标表
	 * @return
	 */
	public boolean executeCopy()throws SQLException{
		PreparedStatement ps = null;
		boolean executeFlag = false;
		try {
			ps = conn.prepareStatement(generateInsertSql());
			ps.execute();
			executeFlag = true;
		} catch (SQLException e) {
			throw new SQLException("executeCopy is Wrong!"
					+ e.getMessage());
		} finally{
			SafeReleaseUtil.close(ps);
		}
		return executeFlag;		
	}
	
	/**
	 * 执行剪切
	 * 从源表复制数据到目标表后,删除原表数据
	 * @return
	 */
	public boolean executeCut() throws SQLException {
		PreparedStatement ps = null;
		boolean executeFlag = false;
		try {
			// 先执行复制数据到目标表
			if (this.executeCopy()) {
				TLogger.getLogger("util").info("delete from " + srcTableName + condition);
				// 后删除源数据（根据条件）
				ps = conn.prepareStatement("delete from " + srcTableName
						+ condition);
				executeFlag = ps.execute();
			}
		} catch (SQLException e) {
			throw new SQLException("executeCut is Wrong!" + e.getMessage());
		} finally {
			SafeReleaseUtil.close(ps);	
		}
		return executeFlag;
	}
	
}
