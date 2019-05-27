package com.irdstudio.smcenter.core.assembly.socket.component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.irdstudio.smcenter.core.assembly.socket.core.AbstractBusinessBus;
import com.irdstudio.smcenter.core.assembly.socket.core.file.DirectSend;
import com.irdstudio.smcenter.core.assembly.socket.core.file.IFileSend;
import com.irdstudio.smcenter.core.assembly.socket.core.util.CoreXmlParser;
import com.irdstudio.smcenter.core.assembly.socket.core.util.FuncParser;
import com.irdstudio.smcenter.core.assembly.socket.core.util.ParamParser;
import com.irdstudio.smcenter.core.assembly.socket.element.BusinessAccept;
import com.irdstudio.smcenter.core.assembly.socket.element.BusinessConf;
import com.irdstudio.smcenter.core.assembly.socket.element.BusinessDeal;
import com.irdstudio.smcenter.core.assembly.socket.element.BusinessDesc;
import com.irdstudio.smcenter.core.assembly.socket.element.BusinessReturn;
import com.irdstudio.smcenter.core.assembly.socket.element.FileBusiMode;
import com.irdstudio.smcenter.core.assembly.socket.element.ResultDesc;
import com.irdstudio.smcenter.core.assembly.socket.util.PackUtil;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;
import com.irdstudio.smcenter.core.util.pub.FileUtil;
import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;

/**
 * 文件传输类交易的处理
 * @author 李广明
 * @version 1.0
 * @date 2008-06-19
 *
 */
public class FileSendBus extends AbstractBusinessBus {

	private FuncParser func = new FuncParser();

	public UniKeyValueObject doExecute(UniKeyValueObject inUvo) throws Exception {
		
		CoreXmlParser cxp = new CoreXmlParser(logger);

		//根据交易码获取其对应的执行描述信息
		String jym = inUvo.getValue(KEY_FIELD).trim();
		BusinessDesc desc = (BusinessDesc) BusinessConf.busiMap.get(jym);
		logger.info(desc.getDesc());
		//校验其所需要接收的参数是否齐全并符合要求
		if (desc.getAcceptParams().size() > 0) {
			BusinessAccept accept = null;
			for (int i = 0; i < desc.getAcceptParams().size(); i++) {
				accept = (BusinessAccept) desc.getAcceptParams().get(i);
				if (!cxp.toCheck(inUvo, accept)) {
					return PackUtil.backParamErrorPack(inUvo
							.getValue(KEY_FIELD), accept.getParamDesc(), accept
							.getParamName());
				}
			}
		}

		//执行处理段中的SQL
		BusinessDeal deal = desc.getBd();
		if (deal != null) {
			try {
				cxp.executeSql(deal, inUvo, null);
			} catch (Exception e) {
				e.printStackTrace();
				return PackUtil.backErrorPack(jym, e.getMessage());
			}
		}
		
		//执行返回数据的SQL生成文件,并将件进行发送
		BusinessReturn br = desc.getBr();
		UniKeyValueObject outUvo = toSendFile(getBusiBusConf(), br, inUvo);
		return outUvo;
	}
	
	/**
	 * 将结果转换成记录集并发送
	 * @param br
	 * @param inUvo
	 * @return
	 */
	public UniKeyValueObject toSendFile(UniKeyValueObject paramUvo,
			BusinessReturn br, UniKeyValueObject inUvo) throws Exception{
		
		//取得交易码值,并对一些必要的JDBC对象进行初始化
		ResultSet rs = null;
		Statement stmt = null;
		Map ds = new Hashtable();
		UniKeyValueObject outUvo = null;
		String jym = inUvo.getValue("JYM");
		String jgm = inUvo.getValue("JGM");
		jgm = jgm == null ? "" : jgm;
		String fullFilePath = paramUvo.getValue("filePath") + jgm
				+ "sendFile.txt";
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
				if ("FILE".equals(key.substring(0, 4).toUpperCase())) {
					FileUtil.rsToFile(rs,fullFilePath, "|");
				} else {
					rs.next();
				}
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
							.getFunc(), ParamParser.parseVariable(desc
							.getParam(), inUvo, null)));
				}
			}
			
			//判断是以何种方式传送,并根据传送方式将其交给相应的传送类进行传送
			String acceptMode = inUvo.getValue("mode");
			int mode = acceptMode == null || "".equals(acceptMode) ? FileBusiMode
					.getMode(paramUvo.getValue("mode"))
					: FileBusiMode.getMode(inUvo.getValue("mode"));
			IFileSend fileSend = null;
			switch(mode){
				case FileBusiMode.DIRECT:			//直接传送
					fileSend = new DirectSend();
					break;
				case FileBusiMode.CHANGE_TRAINS:	//中转传送
					try {
						fileSend = (IFileSend) Class.forName(
							paramUvo.getValue("fileSendClass")).newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					break;
				default:							//缺省使用直接传送
					fileSend = new DirectSend();
					break;
			}
			String ret = fileSend.toSend(outUvo, fullFilePath);
			if(IFileSend.SUCCESSFAL.equals(ret)){
				outUvo = PackUtil.backSuccessfulPack(jym);
			} else {
				outUvo = PackUtil.backErrorPack(jym, ret);
			}
		} catch (Exception e) {
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
}
