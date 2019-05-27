package com.irdstudio.smcenter.core.assembly.socket.core.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.irdstudio.smcenter.core.assembly.socket.element.BusinessAccept;
import com.irdstudio.smcenter.core.assembly.socket.element.BusinessDeal;
import com.irdstudio.smcenter.core.assembly.socket.element.BusinessReturn;
import com.irdstudio.smcenter.core.assembly.socket.element.NodeType;
import com.irdstudio.smcenter.core.assembly.socket.element.ResultDesc;
import com.irdstudio.smcenter.core.assembly.socket.util.PackUtil;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;
import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;

/**
 * {@docRoot}
 * 用于判断ACCETP配置中参数的正确性
 * 用于执行NODE节点
 * 用于执行RETURN标签中配置信息并组成包返回
 * @author 李广明
 * @version 1.0
 * @date 2008-05-16
 *
 */
public class CoreXmlParser {

	private FuncParser func = null;
	private Logger logger = null;

	public CoreXmlParser(Logger logger) {
		this.func = new FuncParser();
		this.logger = logger;
	}
	
	/**
	 * 参数正确性校验
	 * @param inUvo
	 * @param accept
	 * @return
	 */
	public boolean toCheck(UniKeyValueObject inUvo, BusinessAccept accept) {
		if (inUvo.getValue(accept.getParamName()) == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 根据描述生成用于应答的包
	 * @param br
	 * @param inUvo
	 * @return
	 * @throws SQLException 
	 */
	public UniKeyValueObject toPack(BusinessReturn br,
			UniKeyValueObject inUvo) throws SQLException {
		
		//取得交易码值,并对一些必要的JDBC对象进行初始化
		ResultSet rs = null;
		Statement stmt = null;
		Map ds = new Hashtable();
		UniKeyValueObject outUvo = null;
		String jym = inUvo.getValue("JYM");
		Connection conn = TConnPool.getDefaultPool().getConnection();		
		try {
			
			//执行DS描述中的数据源得到记录集并保存到hashtable中
			stmt = conn.createStatement();
			Iterator it = br.getDs().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
				String sql = ParamParser.parseVariable(value, inUvo, null);
				logger.debug(sql);
				rs = stmt.executeQuery(sql);
				rs.next();
				ds.put(key, rs);
			}
			
			//根据描述从DS中的记录集中取得数据进行组包(注意进行记录集的关闭操作)
			outUvo = new UniKeyValueObject();
			outUvo.addVariable("JYM", jym);
			outUvo.addVariable("YDM", "00");
			List result = br.getResult();
			for (int i = 0; i < result.size(); i++) {
				ResultDesc desc = (ResultDesc) result.get(i);
				ResultSet refRs = (ResultSet) ds.get(desc.getDs());
				//如果指定了从那个字段取值,即value不为空,那么从ds中的rs直接取值
				logger.debug(desc.getKey());
				if (!"".equals(desc.getValue())) {
					outUvo.addVariable(desc.getKey(),
							refRs.getString(desc.getValue()));
				} else if (!"".equals(desc.getFunc())) {
					outUvo.addVariable(desc.getKey(), func.callFunc(desc
							.getFunc(), desc.getParam()));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			outUvo = PackUtil.backErrorPack(jym, e.getMessage());
		} finally {
			try {
				//循环关闭hashtable中的记录集,然后才可以进行stmt的关闭
				Iterator it = ds.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry entry = (Map.Entry) it.next();
					ResultSet refRs = (ResultSet) entry.getValue();
					if (refRs != null) {
						refRs.close();
						refRs = null;
					}
				}
				if (rs != null)
					rs.close();
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			TConnPool.getDefaultPool().releaseConnection(conn);
		}
		return outUvo;
	}
	
	/**
	 * 根据DEAL标签的描述,解释并执行其中的SQL语句(递归) 
	 * @param deal
	 * @throws Exception
	 */
	public void executeSql(BusinessDeal deal, UniKeyValueObject inUvo,
			ResultSet parentRs) throws Exception {
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = TConnPool.getDefaultPool().getConnection();
		String sql = ParamParser.parseVariable(deal.getSql(), inUvo, parentRs);
		logger.debug(sql);
		try {
			//如果是循环节点,则在循环中进行递归
			if (NodeType.REPEAT == deal.getNodeType()) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					if (deal.getNext().size() > 0) {
						for (int i = 0; i < deal.getNext().size(); i++) {
							executeSql((BusinessDeal) deal.getNext().get(i),
									inUvo, rs);
						}
					}
				}
			//如果为非循环节点,直接执行后进行递归
			} else if (NodeType.SINGLE == deal.getNodeType()) {
				stmt = conn.createStatement();
				stmt.execute(sql);
				for (int i = 0; i < deal.getNext().size(); i++) {
					executeSql((BusinessDeal) deal.getNext().get(i), inUvo,
							parentRs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("执行SQL语句错误!");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			TConnPool.getDefaultPool().releaseConnection(conn);
		}
	}
}
