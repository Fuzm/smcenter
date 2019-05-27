package com.irdstudio.smcenter.core.assembly.socket.component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.irdstudio.smcenter.core.assembly.socket.core.AbstractBusinessBus;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;
import com.irdstudio.smcenter.core.util.date.DateUtil;
import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;
/**
 * 授权类交易的处理类
 * @author 李广明
 * @version 1.0
 * @date 2008-05-15
 */
public class AccreditBusiBus extends AbstractBusinessBus{
	
	//：01:未发出  02:已发出  03:已出帐  04:已撤销 05:未出帐（已发出，业务系统处理失败）
	private static String CZ_YCZ = "03";	//已出帐
	private static String CZ_YCX = "04";	//已撤消
	
	private static String selectSql = "SELECT * FROM CRD_CZ_SQ WHERE ";
	private static String updateSql = "UPDATE CRD_CZ_SQ SET ZT='02' WHERE PK1=?";

	public UniKeyValueObject doExecute(UniKeyValueObject inUvo) throws Exception{
		
		UniKeyValueObject outUvo = new UniKeyValueObject();

		//取出交易码及借据编号信息,用于向CRD_CZ_SQ表进行查询
		String jym  = inUvo.getValue("JYM").trim();
		String jjbh = inUvo.getValue("JJBH").trim();
		String jgm  = inUvo.getValue("JGM").trim();
		
		if(jym.equals("")){
			outUvo.addVariable("YDM", "10");
			outUvo.addVariable("YDXX", "参数不合法,交易码[JYM]的值为空!");
			return outUvo;
		}
		
		if(jjbh.equals("")){
			outUvo.addVariable("YDM", "11");
			outUvo.addVariable("YDXX", "参数不合法,借据编号[JJBH]的值为空!");
			return outUvo;
		}
		
		if(jgm.equals("")){
			outUvo.addVariable("YDM", "12");
			outUvo.addVariable("YDXX", "参数不合法,机构码[JGM]的值为空!");
			return outUvo;
		}
		
		logger.info("JYM:" + jym + " JJBH:" + jjbh);
		
		//向授权表(CRD_CZ_SQ)查询数据,并组包进行返回
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = TConnPool.getDefaultPool().getConnection();
		String fieldName=null;
		String value=null;
		StringBuffer sql = new StringBuffer(selectSql).append("JYM='");
		sql.append(jym).append("' and JJBH='");
		sql.append(jjbh).append("'");
		if (!"".equals(jgm)) {
			sql.append(" and JGM='");
			sql.append(jgm).append("'");
		}
		sql.append(" ORDER BY ZT");
		logger.debug(sql);
		try {
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			outUvo.addVariable("JYM",jym);
			if(rs.next()){
				String zt = rs.getString("ZT");
				if (CZ_YCZ.equals(zt)) {
					outUvo.addVariable("YDM", "03");
					outUvo.addVariable("YDXX", "该笔数据已经出帐");
				} else if (CZ_YCX.equals(zt)) {
					outUvo.addVariable("YDM", "04");
					outUvo.addVariable("YDXX", "该笔数据在信贷系统中已经撤消");
				} else {
					outUvo.addVariable("YDM", "00");
					outUvo.addVariable("YDXX", "成功");
					outUvo.addVariable("JJBH", jjbh);
					outUvo.addVariable("HTBH", rs.getString("htbh"));
					outUvo.addVariable("KHM", rs.getString("khm"));
					outUvo.addVariable("KHMC", rs.getString("khmc"));
					outUvo.addVariable("ZWHTBH", rs.getString("zwhtbh"));
					outUvo.addVariable("GYH", rs.getString("xdy"));
					outUvo.addVariable("JGM", rs.getString("jgm"));
					outUvo.addVariable("JYRQ", DateUtil.convertToShort(rs.getString("jyrq")));
					String pk1 = rs.getString("pk1");
					for (int i = 1; i <= 25; i++) {
						fieldName = "FLDVALUE"
								+ ("00" + i).substring(("00" + i).length() - 2);
						value = rs.getString(fieldName);
						if (value != null && !"".equals(value.trim())) {
							String[] arr = value.split("@");
							outUvo.addVariable(arr[1], arr[2].trim());
						}
					}
					ps.close();
					// 更新授权表的状态,标记为"已出帐但未核实"
					ps = conn.prepareStatement(updateSql);
					ps.setString(1, pk1);
					ps.executeUpdate();
				}
			} else{
				outUvo.addVariable("YDM", "01");
				outUvo.addVariable("YDXX", "没有相应的数据记录");
			}
			rs.close();
		}catch(ArrayIndexOutOfBoundsException ae){
			logger.error(ae.getMessage(),ae);
			ae.printStackTrace();
			outUvo.clear();
			outUvo.addVariable("JYM", jym);
			outUvo.addVariable("YDM", "99");
			outUvo.addVariable("YDXX", "交易失败,授权表的字段["+fieldName+"]的值["+value.trim()+"]不合法.");			
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
			outUvo.clear();
			outUvo.addVariable("JYM", jym);
			outUvo.addVariable("YDM", "01");
			outUvo.addVariable("YDXX", "交易失败");
		} finally{
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			TConnPool.getDefaultPool().releaseConnection(conn);
		}
		return outUvo;
	}
}
