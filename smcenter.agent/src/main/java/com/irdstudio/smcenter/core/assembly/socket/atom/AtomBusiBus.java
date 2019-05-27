package com.irdstudio.smcenter.core.assembly.socket.atom;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.irdstudio.smcenter.core.assembly.socket.element.BusinessConf;
import com.irdstudio.smcenter.core.assembly.socket.util.PackUtil;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;
import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;

/**
 * 原子交易代理类(主控类)
 * @author 李广民
 * @version 1.0
 * @date 2008-08-27
 */
public class AtomBusiBus {
	
	private List atoms = new ArrayList();	//用于存放原子交易集
	private Connection conn = null;
	private Map results = new Hashtable();	//用于存放结果集
	private IAtom refs[] = null;
	private StringBuffer sb = new StringBuffer();
	
	private static Logger logger = Logger.getLogger("[交易接口]:[原子交易]");
	
	/**
	 * 向主控程序加入要调用的原子交易要素
	 * @param atomJym
	 * @param inUvo
	 */
	public void addAtom(String atomJym,UniKeyValueObject inUvo){
		sb.append(atomJym).append(",");
		atoms.add(new AtomElement(atomJym,inUvo,""));
	}
	
	/**
	 * 向主控程序加入要调用的原子交易要素,可指定依赖的某一个原子交易的结果集
	 * @param atomJym
	 * @param inUvo
	 * @param defendAtom
	 */
	public void addAtom(String atomJym, UniKeyValueObject inUvo,
			String defendAtom) {
		sb.append(atomJym).append(",");
		atoms.add(new AtomElement(atomJym,inUvo,defendAtom));
	}

	/**
	 * 执行原子交易
	 */
	public UniKeyValueObject doExecute(){		

		logger.info("原子交易执行序列[" + sb.deleteCharAt(sb.length() - 1) + "]");
		
		int i = 0;
		refs = new IAtom[atoms.size()];
		UniKeyValueObject backUvo = null;		
		try {
			conn = TConnPool.getDefaultPool().getConnection();
			conn.setAutoCommit(false);
			boolean isAllSuccessful = true;
			for (;i < atoms.size(); i++) {
				
				AtomElement element = (AtomElement) atoms.get(i);
				IAtom atom = (IAtom) Class.forName(element.getAtomClass())
						.newInstance();
				refs[i] = atom;
				atom.setConnection(conn);
				
				//如果有依赖的原子交易,取得该原子交易的结果集
				if ("".equals(element.getDependAtom())) {
					atom.setParam(element.getParam());
				} else {
					UniKeyValueObject dependUvo = (UniKeyValueObject) results
							.get(element.getDependAtom());
					atom.setParam(dependUvo);
				}
				
				logger.info("执行原子交易[" + element.getAtomJym() + "]...");
				
				if (atom.execute()) {
					results.put(element.getAtomJym(), atom.getResultPack());
				} else {
					// 如果失败,进行回滚,并不再做其它操作
					logger.debug(PackUtil.parsePack(atom.getResultPack()));
					backUvo = atom.getResultPack();
					rollback();
					isAllSuccessful = false;
					break;
				}
			}
			//如果全部成功,则进行提交
			if (isAllSuccessful) {
				commit();
			}			
			logger.info("原子交易序列执行完成");
		} catch(SQLException e){
			e.printStackTrace();
			rollback();
			return PackUtil.backErrorPack("00000", e.getMessage());			
		} catch (InstantiationException e) {
			e.printStackTrace();
			rollback();
			return PackUtil.backErrorPack("00000", e.getMessage());
		} catch (IllegalAccessException e) {
			rollback();
			e.printStackTrace();
			return PackUtil.backErrorPack("00000", e.getMessage());
		} catch (ClassNotFoundException e) {
			rollback();
			e.printStackTrace();
			return PackUtil.backErrorPack("00000", ((AtomElement) atoms.get(i))
					.getAtomJym()
					+ "原子交易执行类没有找到");
		} catch(Exception e){
			rollback();
			e.printStackTrace();
			return PackUtil.backErrorPack("00000", e.getMessage());			
		}finally {
			TConnPool.getDefaultPool().releaseConnection(conn);
		}
		
		//如果执行没有错误,则自行组装包,如果有错误,返回错误信息包
		if (backUvo == null)
			return PackUtil.backSuccessfulPack("00000");
		else
			return backUvo;
	}
	
	/**
	 * 取得其中某一个交易执行的结果集
	 * @param atomJym
	 * @return
	 */
	public UniKeyValueObject getResultPack(String atomJym){
		return (UniKeyValueObject) results.get(atomJym);
	}
	
	/**
	 * 释放必须的资料
	 * @throws Exception
	 */
	private void free() throws SQLException{
		if(conn != null) {
			conn.close();
		}
	}	
	
	/**
	 * 用于执行失败时的回滚
	 */
	private void rollback(){
		try {
			logger.debug("执行失败,开始数据回滚...");
			conn.rollback();
			conn.setAutoCommit(true);
			closeAllJdbcResource();
			logger.debug("回滚完成");
			this.free();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 用于执行成功后的提交
	 */
	private void commit(){
		try {
			logger.debug("执行成功,开始提交...");
			conn.commit();
			conn.setAutoCommit(true);
			closeAllJdbcResource();
			logger.debug("提交成功");
			this.free();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void closeAllJdbcResource(){
		if (refs != null) {
			for (int i = 0; i < refs.length; i++) {
				if (refs[i] != null)
					((IAtom) refs[i]).closeResource();
			}
		}
	}
	
	class AtomElement{
		
		private String atomJym = "";
		private String atomClass = "";
		private String dependAtom = "";
		private UniKeyValueObject param = null;
		
		public AtomElement(String atomJym, UniKeyValueObject inUvo,
				String dependAtom) {
			this.atomJym = atomJym;
			this.param = inUvo;
			Object theClass = BusinessConf.atomMap.get(atomJym);
			if (theClass == null) {
				this.atomClass = "";
			} else {
				this.atomClass = theClass.toString();
			}
			this.dependAtom = dependAtom;
		}
		
		public String getAtomJym() {
			return atomJym;
		}
		public void setAtomJym(String atomJym) {
			this.atomJym = atomJym;
		}
		public String getAtomClass() {
			return atomClass;
		}
		public void setAtomClass(String atomClass) {
			this.atomClass = atomClass;
		}
		public UniKeyValueObject getParam() {
			return param;
		}
		public void setParam(UniKeyValueObject param) {
			this.param = param;
		}

		public String getDependAtom() {
			return dependAtom;
		}

		public void setDependAtom(String dependAtom) {
			this.dependAtom = dependAtom;
		}
	}
}
