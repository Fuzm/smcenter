package com.irdstudio.smcenter.core.assembly.plugin.dataload;
/**
 * @docRoot:
 * 完成从文本文件导数据到表中
 * @author guangming.li
 * @version 2.1
 * @since 1.0 2006-04-15
 * @lastmodified 2014-05-23 优化性能
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.rmi.server.ExportException;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.irdstudio.smcenter.core.tinycore.jdbc.meta.MetaTable;
import com.irdstudio.smcenter.core.tinycore.jdbc.session.CustomTransaction;
import com.irdstudio.smcenter.core.tinycore.jdbc.session.TransactionUtil;
import com.irdstudio.smcenter.core.tinycore.log.ILogger;
public class ImportFromTxt{

	/* 文本文件装载配置属性 */
	private PluginLoadConf conf = null;
	/* 文本文件路径 */
	private String fullFilePath = null;
	/* 文件字段数据分隔符(注意在Java中有许多字符需要转义) */
	private String dataSplit = "";
	/* 用于执行批量装的JDBC对象 */
	private PreparedStatement pst = null;
	/* 是否读完标志 */
	private boolean isReadedComplete;
	/* 文件流(按缓冲区大小读取) */
	private BufferedReader br;
	/* 文件总共记录数统计 */
	private int fileRecordCount;
	/* 文件记载装载失败数据统计 */
	private int faildRecordCount;
	/* 记录当前批量提交语句的数量 */
	private int currBatchCount;
	/* 目标表元数据对象 */
	private MetaTable table;
	/* Insert语句(批量执行语句) */
	private String sqlHead;
	/* 事务处理类*/
	private CustomTransaction tran = null;
	/* 日志记录对象 */
	private ILogger logger = null;
	/* 数据库连接(由调用者传递) */
	private Connection conn = null;

	
	
	/**
	 * 构造函数(传入装载配置,连接,及文本日志记录对象)
	 * @param loadConf
	 * @param logger
	 * @param conn
	 */
	public ImportFromTxt(PluginLoadConf loadConf, ILogger logger,
			Connection conn) {
		this.conf = loadConf;
		this.fullFilePath = loadConf.getLoadFromFile();
		this.logger = logger;
		this.conn = conn;

	}
	
	public boolean run() {
		
		boolean flag = true;
		
		// 取得连接
		double before = System.currentTimeMillis();

		// 取得目标表的元数据信息
		fillMetaTable(conn);
		
		try {
			// 构建文件流
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					fullFilePath), conf.getFileCharset()), conf
					.getLoadBufferSize() * 1024);
			
			// 初始化分隔符
			dataSplit = conf.getLoadSeparator();
			if (dataSplit.trim().equals("|"))
				dataSplit = "\\" + dataSplit;			

			// 根据拥有的字段,生成insert语句前半部分固定值及后半部分的占位符
			StringBuffer iSql = new StringBuffer("INSERT INTO ");
			StringBuffer vSql = new StringBuffer(" VALUES(");
			iSql.append(conf.getTableName()).append("(");
			for (int k = 0; k < table.fieldCount - 1; k++) {
				iSql.append(table.fields[k]).append(",");
				vSql.append("?,");
			}
			iSql.append(table.fields[table.fieldCount - 1]).append(")");
			vSql.append("?)");
			this.sqlHead = iSql.append(vSql).toString();
			
			// 从文件流中读数据(默认一次读出五千条)
			tran = TransactionUtil.createTransaction(conn);
			pst = conn.prepareStatement(this.sqlHead);
			pst.setFetchSize(1000);
			int start = 0, end = 1000;
			while (!isReadedComplete) {
				fetchDataToBatchPst(start, end);
				batchInsertIntoTable();
				start = end + 1;
				end = end + 1000;
			}
			
			//显示总共多少条记录,以及成功导入了多少条
			logger.info("文本文件总记录数:" + fileRecordCount);
			logger.info("导入成功记录数:" + (fileRecordCount - faildRecordCount));
			logger.info("导入失败记录数:" + (faildRecordCount));
			
			double after = System.currentTimeMillis();
			logger.info("耗时[" + (after - before) / 1000 + "]秒");
			
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error("导入数据出错!", e);
			flag = false;
		}
		finally{
			//关掉对象,释放资料
			try{
				//关闭文件流
				if(br !=null){
					br.close();
					br = null;
				}
			}catch(IOException e){
				logger.error("关闭文件流对象时异常!",e);
			}
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			pst = null;
			conf = null;
			table = null;
			TransactionUtil.releaseTransaction(tran);
		}
		return flag;
	}
	
	/**
	 * 获得表的元数
	 * @param conn
	 */
	private void fillMetaTable(Connection conn) {
		if (null == conf.getLoadFields() || "".equals(conf.getLoadFields())) {
			table = MetaTable.getInstWithTable(conn, conf.getTableName());
		} else {
			table = MetaTable.getInstWithQuery(conn, "select "
					+ conf.getLoadFields() + " from " + conf.getTableName());
		}
	}

	/**
	 * 批量写入数据库
	 * @param sqlList
	 */
	private void batchInsertIntoTable() {
		try {
			long t1 = System.currentTimeMillis();
			pst.executeBatch();
			tran.commit();
			pst.clearBatch();
			logger.info("批量执行耗时："
					+ (new BigDecimal(System.currentTimeMillis() - t1)
							.divide(new BigDecimal(1000.00))) + "秒:每次条数="
					+ pst.getFetchSize());
		} catch (BatchUpdateException e) {			
			try {
				//faildRecordCount += pst.getUpdateCount();
				faildRecordCount += currBatchCount;
				tran.rollback();
			} catch (SQLException e1) {
				logger.info("事务回滚时出错!", e);
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.info("批量提交时出错!", e);
			//faildRecordCount += e.getUpdateCounts().length;
		} catch (SQLException e) {			
			try {
				tran.rollback();
			} catch (SQLException e1) {
				logger.info("事务回滚时出错!", e);
				e1.printStackTrace();
			}			
			e.printStackTrace();
			//faildRecordCount += pst.getUpdateCount();
			faildRecordCount += currBatchCount;
		} finally{
			// 恢复批量语句记数及清空批量语句
			currBatchCount = 0;
			try {
				pst.clearBatch();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取指定行数的记录并绑定到批量执行对象
	 * @param start
	 * @param end
	 * @return
	 * @throws ExportException
	 */
	public void fetchDataToBatchPst(int start, int end) throws Exception {
		String s = null;
		try {
			s = br.readLine();
			// 得到总条数和是否已读完的标志
			if (s == null) {
				isReadedComplete = true;
			}
			while (s != null) {
				// 有可能数据中有换行，所以读出来是空行，但接着又会有一行是数据
				if (!"".equals(s.trim())) {
					fileRecordCount++;
					if (fileRecordCount >= start) {
						if (bindValueToBatchSQL(s)) {
							this.pst.addBatch();
							currBatchCount++;
						}
						if (fileRecordCount == end)
							break;
					}
				}
				s = br.readLine();
			}
		} catch (IOException e) {
			throw new Exception(e);
		}
	}

	/**
	 * 绑定字段值到批量执行对象(pst)
	 * @param values
	 * @return
	 */
	private boolean bindValueToBatchSQL(String values){

		// 分解后得到一行包含的字段
		String[] frecord = values.split(dataSplit, -1);
		
		// 验证字段个数与数据个数是否匹配(最后一位必须带分隔符,所以减一)
		int dataLength = frecord.length;
		if (dataLength < table.fieldCount) {
			faildRecordCount++;
			logger.info("文件[" + fullFilePath + "]第[" + fileRecordCount
					+ "]行的字段数[" + dataLength + "]少于数据库中的字段数["
					+ table.fieldCount + "]");
			return false;
		}

		// 组装每一个字段(以表字段数为准去找数据)
		try {
			for (int i = 0; i < table.fieldCount; i++) {
				String data = frecord[i].trim();
				this.pst.setObject(i + 1, data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
		return true;
	}
	
	/**
	 * 返回文件中的总记录数
	 * @return
	 */
	public int getFileRecordCount() {
		return fileRecordCount;
	}

	/**
	 * 返回装载失败的记录数
	 * @return
	 */
	public int getFaildRecordCount() {
		return faildRecordCount;
	}	
}
