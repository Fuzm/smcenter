package com.irdstudio.smcenter.core.util.sequence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.irdstudio.smcenter.core.tinycore.log.ILogger;
import com.irdstudio.smcenter.core.tinycore.log.TLogger;
import com.irdstudio.smcenter.core.util.pub.ConnectionUtil;

/**
 * 该类是生成自定义序列的服务类，向外部应用程序只提供一个入口调用getNextValue，生成唯一的序列号
 * 该序列号的生成不依赖于特定的数据库，如需增加序列，可以通过维护s_autocode表去实现。
 */
public class SequenceService {
	
	private static final ILogger logger = TLogger.getLogger("SEQ");
	
	/* 序列有效日期 */
	private static String sequenceDate = null;
	
	/* 缓冲步长 */
	private final static int BUFFER_STEP = 20;

	/* 序号缓冲器 */
	private static HashMap<String, SerialNumber> serialNumberBuffer = new HashMap<String, SerialNumber>();
	
	private static SequenceService seqUtil = new SequenceService();
	/**
	 * 最基础取序号方法
	 * @param bizType
	 * @param owner
	 * @param connection
	 * @return
	 */
	public static synchronized String getNextValue(String bizType,
			String owner, Connection connection) throws Exception {
		// 根据bizType与owner组成关键字
		String symbol = bizType+owner;
		// 如果在map中存在,则取map中的序号类,没有则从数据库中取
		SerialNumber sbean = null;
		
		String openday = ConnectionUtil.getSysTime();
		
		if(!openday.equals(sequenceDate)) {
			//假如序列日期与营业日期不一致，则是切日操作，丢弃掉缓存中的所有序列值
			serialNumberBuffer.clear();
			sequenceDate = openday;
		}
		
		if(serialNumberBuffer.containsKey(symbol)) {
			sbean = serialNumberBuffer.get(symbol);
			if(sbean.currentValue.equals(sbean.bufferedValue)) {
				// 序号类中的值已经到达缓冲后值,则从数据库取一次值
				sbean = querySequenceFromDB2(bizType, owner);
				sbean.currentValue += sbean.step;
				serialNumberBuffer.put(symbol, sbean);

			} else {
				// 如果未到达,直接加上步长
				sbean.currentValue += sbean.step;
			}
		} else {
			sbean = seqUtil.new SerialNumber();
			sbean = querySequenceFromDB2(bizType, owner);
			serialNumberBuffer.put(symbol, sbean);
		}

		return new Long(sbean.currentValue).toString();
	}
	
	private class SerialNumber {
		/* 当前值 */
		public Long currentValue;
		/* 步长 */
		public int step;
		/* 缓冲步长 */
		public int bufferStep = BUFFER_STEP;
		/* 缓冲后序号值 */
		public Long bufferedValue;
		/* 是否使用数据库序列标识 */
		public boolean flag;
	}	
	

	
	/**
	 * 自定义生成序列，不依赖于特性的数据库，该序列的维护是维护s_autocode表
	 * @param bizType    s_autocode.ATYPE
	 * @param owner      s_autocode.OWNER
	 * @param connection
	 * @return
	 * @throws EMPException
	 */
	private static synchronized SerialNumber querySequenceFromDB2(String bizType, String owner) throws Exception {
		
		ResultSet rs = null;
		SerialNumber sbean= seqUtil.new SerialNumber();
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt = null;
		Connection connection = null;
		
		try{
			
			connection = ConnectionUtil.getConnection();
			
			String sqlStr = "select INITCYCLE, CUR_SERNUM from s_autocode where ATYPE=? and OWNER=?";
			String sqlStr2 = "update s_autocode set CUR_SERNUM = ? where ATYPE=? and OWNER=?";
			
			pstmt = connection.prepareStatement(sqlStr);
			pstmt.setString(1, bizType);
			pstmt.setString(2, owner);
			rs = pstmt.executeQuery();
			if(rs == null){
				logger.info("Can not found the sequence by [bizType="+bizType+" and owner="+owner+"]");
				throw new Exception("Can not found the sequence by [bizType="+bizType+" and owner="+owner+"]" );
			}
			
			if(rs.next()){

				sbean.step = new Integer(rs.getString("INITCYCLE"));
				sbean.currentValue = new Long(rs.getString("CUR_SERNUM"));
				sbean.bufferedValue = sbean.currentValue + sbean.bufferStep-1;
				sbean.flag = false;
				
				pstmt2 = connection.prepareStatement(sqlStr2);
				pstmt2.setLong(1, sbean.bufferedValue);
				pstmt2.setString(2, bizType);
				pstmt2.setString(3, owner);
				pstmt2.executeUpdate();
				
			}else{
				logger.error("Can not found the sequence by [bizType="+bizType+" and owner="+owner+"], beacuse Sequence next is erro ");
				throw new Exception("Can not found the sequence by [bizType="+bizType+" and owner="+owner+"], beacuse Sequence  next is erro ");
			}
			
			return sbean;
		}catch(Exception e){
			logger.error("The CMISSequenceService4Oracle occur an error:"+e.getMessage());
			throw new Exception(e);
		}finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {e.printStackTrace();}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {e.printStackTrace();}
			}
			
			if (pstmt2 != null) {
				try {
					pstmt2.close();
				} catch (SQLException e) {e.printStackTrace();}
			}
			
			ConnectionUtil.releaseConnection(connection);
		}
	}


	/**
	 * 
	 * @param sequenceName	数据库序列名
	 * @param connection
	 * @return
	 * @throws EMPException
	 */
	public static synchronized String querySequenceFromDB(String sequenceName, Connection connection) throws Exception {
		String sqlStr = null;
		ResultSet rs = null;
		String cur_sernum=null;
		Statement stmt = null;
		try{
			sqlStr = "select to_char("+sequenceName+".nextval) from dual";
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlStr);
			if(rs == null){
				logger.error("Can not found the sequence: " + sequenceName);
				throw new Exception("Can not found the sequence: " + sequenceName);
			}
			
			if(rs.next()){
				cur_sernum = rs.getString(1);
				cur_sernum = String.format("%1$6s",cur_sernum).replaceAll("\\s", "0");
			}else{
				logger.error("Can not found the sequence: " + sequenceName + ", beacuse Sequence  next is erro ");
				throw new Exception("Can not found the sequence: " + sequenceName + ", beacuse Sequence  next is erro ");
			}
			return cur_sernum;
		}catch(Exception e){
			logger.error("The CMISSequenceService4Oracle occur an error:" + e.getMessage());
			throw new Exception(e);
		}finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {	e.printStackTrace();}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {	e.printStackTrace();}
			}
		}
	}

}
