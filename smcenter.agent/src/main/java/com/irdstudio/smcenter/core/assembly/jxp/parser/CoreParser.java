package com.irdstudio.smcenter.core.assembly.jxp.parser;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.irdstudio.smcenter.core.assembly.jxp.conf.ConfigureEntry;
import com.irdstudio.smcenter.core.assembly.jxp.conf.NodeAttrSection;
import com.irdstudio.smcenter.core.assembly.jxp.conf.NodeSection;
import com.irdstudio.smcenter.core.assembly.jxp.func.JxpFunc;
import com.irdstudio.smcenter.core.assembly.jxp.util.LogUtil;
import com.irdstudio.smcenter.core.assembly.jxp.util.NodeUtil;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.TConnPool;
import com.irdstudio.smcenter.core.util.vo.VariableValue;
/**
 * @docRoot
 * 核心解释者,负责解释XML文件
 * 主要依赖XML描述类跟变量类
 * 在该核心解释者类中实现主要解释算法
 * @author 李广明
 * @version 1.2
 * @date 2006-11-13
 * @modify 2009-01-04
 * 	完善解释器,支持XML属性解析
 * 	@modify 2013-10-13
 * 将输出对象从字节输出流变更为字符输出流
 * 以方便生成指定格式的文件 
 */
public class CoreParser {

	/* 解释器必需参数：XML配置信息类 */
	private ConfigureEntry cxd  = null;
	/* 解释器必需参数：变量类,解释CoreXmlDesc中用到的变量的值 */
	private VariableValue vv = null;
	/* 解释器必需参数：内置函数解释器 */
	private JxpFunc func 	 = null;	
	/* 解释器必需参数：文件输出对象(用于生成数据文件) */
	private OutputStreamWriter file = null;
	/* 常量符号：大于符号(写XML文件时使用) */
	private final static char SIGN_GT = '>';
	/* 常量符号：小于符号(写XML文件时使用) */
	private final static char SIGN_LT = '<';
	
	/**
	 * 传递XML描述类与变量类给构造函数
	 * 以供解释都使用
	 * @param cxd
	 * @param vv
	 */
	public CoreParser(ConfigureEntry cxd,VariableValue vv){
		this.cxd = cxd;
		this.vv = vv;
		this.func = new JxpFunc(cxd.getDefineSection());
	}
	
	/**
	 * 构造函数2
	 * 可传入用于写入文件内容的对象
	 * @param cxd
	 * @param vv
	 * @param bos
	 */
	public CoreParser(ConfigureEntry cxd, VariableValue vv,
			OutputStreamWriter osw) {
		this.cxd = cxd;
		this.vv = vv;
		this.file = osw;
		this.func = new JxpFunc(cxd.getDefineSection());
	}

	/**
	 * 默认的构造函数
	 */
	public CoreParser(){
	}
	
	/**
	 * 挂载核心XML描述对象(解释语言)
	 * @param cxd
	 */
	public void linkConfigureEntry(ConfigureEntry cxd){
		this.cxd = cxd;
		if (func == null)
			this.func = new JxpFunc(cxd.getDefineSection());		
	}
	
	/**
	 * 挂载变量值对象
	 * @param vv
	 */
	public void linkVariableValue(VariableValue vv){
		this.vv = vv;
	}
	
	/**
	 * 挂载写文件内容的对象
	 * @param osw
	 */
	public void linkFileObject(OutputStreamWriter osw) {
		this.file = osw;
	}
	
	/**
	 * 核心解释类的入口方法
	 * @return
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public boolean run(){
		
		//判断所需要的对象是否都已经备齐		
		if(cxd == null){
			LogUtil.out("未挂载xml描述对象!");
			return false;
		}
		
		if(vv == null){
			LogUtil.out("未挂载变量值对象");
			return false;
		}
		
		if(file == null){
			LogUtil.out("未挂载写文件对象");
			return false;
		}
		
		//执行SQL区中的SQL语句
//		if(!flag){
//			if (cxd.getSqlDesc().isRun()) {
//				SqlParser.getInstance(
//						cxd.getSqlDesc(), vv).run();
//				flag = true;
//			}
//		}

		//递归解释节点,并将内容写入文件中
		try{			
			generateXml(cxd.getNodeSection(),null);			
			//将缓冲区还没写入文件的内容写入文件
			file.flush();
			LogUtil.out("");
		}
		catch(IOException e){
			e.printStackTrace();
			LogUtil.out("写入文件时发生错误!");
			LogUtil.writeError(e);
			return false;
		}
		catch(SQLException e){
			e.printStackTrace();
			LogUtil.out("解释节点时遇到SQL错误!");
			LogUtil.writeError(e);
			return false;
		}
		
		return true;
	}

	/**
	 * 递归函数，用于递归解释节点并生成XML串
	 * @param nd
	 * @return
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private void generateXml(NodeSection nd,ResultSet rs) throws SQLException, IOException{

		//定义记录集，用于将数据源传递给子节点
		ResultSet curRs = null;
		
		//定义其它JDBC对象
		Connection conn = null;
		Statement st = null;
		
		//处理单节点的流程
		if(nd.getType() == NodeUtil.SINGLE){
			//先对节点本身进行解释
			if (!"".equals(nd.getLabelName())) {
				file.write(SIGN_LT);
				file.write(nd.getLabelName());
				//如果存在属性结点,则生成该结点的属性字段
				if(nd.getAttrs().size() >0){
					generateAttrs(nd.getAttrs(),rs);
				}				
				file.write(SIGN_GT);
			}
			outFormatLog(nd.getLabelName());
			//单节点标签,只有有数据源就进行解释并传给下级节点
			if(!"".equals(nd.getDataSrc())){
				//从数据源中取得值
				conn = TConnPool.getDefaultPool().getConnection();
				st = conn.createStatement();
				//分析数据源中的变量,如果有字段变量,都应当从父数据源来
				String sql = ParamParser.parseVariable(
						nd.getDataSrc(),
						vv,
						rs);
				curRs = st.executeQuery(sql);
				//如果单数据源中没值,将记录集置为空
				if(!curRs.next()){
					curRs.close();
					curRs = null;
				}
			}
			//如果指定了字段或使用了内置函数,则尝试根据数据源取得其值
			if(!"".equals(nd.getField()) ||
					!"".equals(nd.getFunc())){
				if(!"".equals(nd.getDataSrc())){
					if (curRs != null)
						file.write(getValue(nd, curRs));
				}
				else{
					//如有父数据源则从父数据源中取
					if (rs != null) {
						file.write(getValue(nd, rs));
					}
				}
			}
			//判断是否有下级节点述并进行递归解释
			if(nd.getNext().size() > 0){
				List<?> nextList = nd.getNext();
				for (int i = 0; i < nextList.size(); i++) {				
					//如果有下级节点是NODE则进行递归
					NodeSection subNode = (NodeSection)nextList.get(i);
					//xml.append(generateXml(subNode,curRs));
					generateXml(subNode,curRs);
				}
			}
			//节点解释结束
			if (!"".equals(nd.getLabelName())) {
				file.write(SIGN_LT);
				file.write('/');
				file.write(nd.getLabelName());
				file.write(SIGN_GT);
				file.write('\n');
			}
		}
		
		//处理循环节点的流程
		else if(nd.getType() == NodeUtil.REPEAT){
			conn = TConnPool.getDefaultPool().getConnection();
			st = conn.createStatement();
			//分析数据源中的变量,如果有字段变量,都应当从父数据源来
			String sql = ParamParser.parseVariable(
					nd.getDataSrc(),
					vv,
					rs);
			System.err.println(sql);
			curRs = st.executeQuery(sql);
			while(curRs.next()){
				//开始本身标签的输出
				if (!"".equals(nd.getLabelName())) {
					file.write(SIGN_LT);
					file.write(nd.getLabelName());
					//如果存在属性结点,则生成该结点的属性字段
					if(nd.getAttrs().size() >0){
						generateAttrs(nd.getAttrs(),curRs);
					}
					file.write(SIGN_GT);
				}
				outFormatLog(nd.getLabelName());
				//如果指定了字段,则取得该字段的值赋予该标签
				if (!"".equals(nd.getField()))
					file.write(getValue(nd,curRs));
				//判断是否有下级节点述并进行递归解释
				if(nd.getNext().size() > 0){
					List<?> nextList = nd.getNext();
					for (int i = 0; i < nextList.size(); i++) {
						//如果有下级节点是NODE则进行递归
						NodeSection subNode = (NodeSection)nextList.get(i);
						//xml.append(generateXml(subNode,curRs));
						generateXml(subNode,curRs);
					}
				}
				//结束本身标签的输出
				if (!"".equals(nd.getLabelName())) {
					file.write(SIGN_LT);
					file.write('/');
					file.write(nd.getLabelName());
					file.write(SIGN_GT);
					file.write('\n');
				}
			}
		}
		
		//关闭JDBC相关对象
		if(curRs != null) curRs.close();
		if(st != null) st.close();
		TConnPool.getDefaultPool().releaseConnection(conn);

		//返回生成的XML数据
		return;
	}
	
	/**
	 * 按规则获取记录集的内容
	 * 并进行相应的转码操作
	 * @param nd
	 * @param rs
	 * @return
	 * @throws SQLException  
	 */
	private String getValue(NodeSection nd ,ResultSet rs) throws SQLException {
		
		String value = "";
		
		//判断一,如果有使用函数,则交给解释器内置函数处理器进行处理
		if (!"".equals(nd.getFunc())) {
			// 如果有参数,先分析完参数
			String param = "";
			if (!"".equals(nd.getParam()))
				param = ParamParser.parseVariable(
						nd.getParam(),
						this.vv, rs);			
			value = func.callJxpFunc(nd.getFunc(), param);
		} else {
			value = rs.getString(nd.getField());
			//进行转码操作
//			try {
//				if (value != null)
//					value = new String(value.getBytes("ISO8859-1"));
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
		}
		
		if(value == null) value = "";
		
		// 判断二,如果有限制了值的长度，则进行截取
		if (nd.getMaxLen() > 0) {
			if (value.length() < nd.getMaxLen())
				value = value.substring(0, nd.getMaxLen());
			else
				value = value.substring(0);
		}
		
		return value;
	}

	/**
	 * 根据配置生成XML标签的属性
	 * @param attrs 
	 * @throws IOException
	 */
	private void generateAttrs(List<?> attrs , ResultSet rs) throws IOException {
		for (int x = 0; x < attrs.size(); x++) {
			NodeAttrSection attr = (NodeAttrSection)attrs.get(x);
			file.write((" " + attr.getName() + "=\""));
			file.write((ParamParser.parseVariable(attr.getValue(), this.vv, rs) + "\""));
		}
	}	
	
	/**
	 * 输出格式化的日志信息
	 * @param lableName
	 */
	private void outFormatLog(String lableName){
		StringBuffer sb = new StringBuffer();
		sb.append("生成<");
		sb.append(lableName);
		sb.append(">标签...");
		LogUtil.out(sb.toString());
	}
}