package com.irdstudio.smcenter.core.assembly.socket.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.irdstudio.smcenter.core.assembly.socket.element.Business;
import com.irdstudio.smcenter.core.assembly.socket.element.BusinessAccept;
import com.irdstudio.smcenter.core.assembly.socket.element.BusinessConf;
import com.irdstudio.smcenter.core.assembly.socket.element.BusinessDeal;
import com.irdstudio.smcenter.core.assembly.socket.element.BusinessDesc;
import com.irdstudio.smcenter.core.assembly.socket.element.BusinessReturn;
import com.irdstudio.smcenter.core.assembly.socket.element.NodeType;
import com.irdstudio.smcenter.core.assembly.socket.element.ResultDesc;
import com.irdstudio.smcenter.core.util.pub.Convert;

/**
 * 将指定配置文件中的配置装载到BusinessConf
 * @author 李广明
 * @version 1.0
 * @date 2008-05-12
 *
 */
public class XmlLoadUtil {

	private static Logger logger  =  Logger.getLogger("[交易接口]:[配置装载]");

	/**
	 * 从指定配置文件中读取配置信息到BusinessConf类中
	 */
	public static boolean loadFromXml(String xmlCfgFile){
		
		if(BusinessConf.isHandLoad) return true;
		
		logger.info("开始装载...");

		SAXBuilder sb = new SAXBuilder();
		Document doc = null;
		//打开文件,读出配置
	    try {
	    	
	    	doc = sb.build(new FileInputStream(xmlCfgFile));
	    	
	    	//取得根元素
			Element root = doc.getRootElement();

			//取得所有第一级元素
			List childs = root.getChildren();
			
			//循环取得第一级元素的内容
			for (Iterator it = childs.iterator(); it.hasNext(); ) {	
				Element element = (Element)it.next();
				if("CONFIG".equals(element.getName().toUpperCase())){
					loadConfigLabel(element);
				}
				if("BUSI_DESC".equals(element.getName().toUpperCase())){
					loadBusiDescLabel(element);
				}
				if("ATOM_BUSI".equals(element.getName().toUpperCase())){
					loadAtom(element);
				}				
			}			
		} catch (FileNotFoundException e) {
			logger.info("文件" + xmlCfgFile + "没有找到!");
			return false;
		} catch (JDOMException e) {
			logger.info("读" + xmlCfgFile + "文件异常,请检查XML文件!");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e.getMessage());
			return false;
		} finally {
			doc = null;
		}
		return true;
	}
	
	/**
	 * 用于读取CONFIG标签段的配置信息
	 * @param element
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private static void loadConfigLabel(Element element)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		List childs = element.getChildren();
		for (Iterator it = childs.iterator(); it.hasNext();) {
			
			Element tmpElement = (Element) it.next();
			
			//取得核心系统中的交易接口服务程序的IP及端口
			if ("CREDIT_TO_CORE".equals(tmpElement.getName().toUpperCase())) {
				BusinessConf.coreServerIp = tmpElement
						.getAttributeValue("serverIp");
				BusinessConf.coreServerPort = tmpElement
						.getAttributeValue("serverPort");
				logger.debug(BusinessConf.coreServerIp);
				logger.debug(BusinessConf.coreServerPort);
			}
			
			if ("CREDIT_TO_CREDIT".equals(tmpElement.getName().toUpperCase())) {
				BusinessConf.creditServerIp = tmpElement
						.getAttributeValue("serverIp");
				BusinessConf.creditServerPort = tmpElement
						.getAttributeValue("serverPort");
				logger.debug(BusinessConf.creditServerIp);
				logger.debug(BusinessConf.creditServerPort);
			}

			//取得数据包键值分隔符及数据分隔符
			if ("PACK".equals(tmpElement.getName().toUpperCase())) {
				int ch1 = Convert.StrToInt(tmpElement
						.getAttributeValue("keyValueSplitChar"), 32);
				BusinessConf.keyValueSplit = String.valueOf((char) ch1);
				int ch2 = Convert.StrToInt(tmpElement
						.getAttributeValue("memberSplitChar"), 13);
				BusinessConf.memberSplit = String.valueOf((char) ch2);
			}
			
			//取得各种交易类的处理程序及交易码
			if ("MIDDLE_BUSI".equals(tmpElement.getName().toUpperCase())){
				loadMiddleBusiUtil(tmpElement);
			}
		}
	}
	
	/**
	 * 装载中间业务处理类的主配置信息
	 * @param tmpElement
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	private static void loadMiddleBusiUtil(Element tmpElement)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		
		String jyms = tmpElement.getAttributeValue("jyms");
		String[] jymArray = jyms.split(",");
		
		Business busi = new Business();
		busi.setId(tmpElement.getAttributeValue("ID"));
		busi.setJyms(tmpElement.getAttributeValue("jyms"));
		busi.setDesc(tmpElement.getAttributeValue("desc"));
		busi.setDealClass(tmpElement.getAttributeValue("dealClass"));
		
		//如果存在下级环境变量配置,则进行装载
		if (tmpElement.getChildren().size() > 0) {
			List childs = tmpElement.getChildren();
			for (Iterator it = childs.iterator(); it.hasNext();) {
				Element envElement = (Element) it.next();
				logger.debug(envElement.getAttributeValue("name") + ":"
						+ envElement.getAttributeValue("value"));
				busi.addEnv(envElement.getAttributeValue("name"), envElement
						.getAttributeValue("value"));
			}
		}

		for (int i = 0; i < jymArray.length; i++) {
			BusinessConf.busiTypeMap.put(jymArray[i].trim(), busi);
		}
		logger.debug(busi.getDesc() + ":" + busi.getJyms());
		logger.debug(busi.getDealClass());
	}
	
	/**
	 * 用于读取BUSI_DESC标签段的配置信息
	 * @param element
	 */
	private static void loadBusiDescLabel(Element element){
		
		List childs = element.getChildren();
		for (Iterator it = childs.iterator(); it.hasNext();) {
			
			logger.debug("..............................................");
			Element tmpElement = (Element) it.next();
			BusinessDesc desc = new BusinessDesc();
			desc.setJym(tmpElement.getAttributeValue("jym").trim());
			desc.setDesc(tmpElement.getAttributeValue("desc"));
			desc.setDealClass(tmpElement.getAttributeValue("dealClass"));
			logger.debug(desc.getJym() + " " + desc.getDesc() + " "
					+ desc.getDealClass());
			
			List subChilds = tmpElement.getChildren();
			for (Iterator subIt = subChilds.iterator(); subIt.hasNext();) {
				Element subElement = (Element) subIt.next();
				logger.debug(subElement.getName());
				
				if("ACCEPT".equals(subElement.getName().toUpperCase())){
					List acceptChilds = subElement.getChildren();
					for (Iterator acceptIt = acceptChilds.iterator(); acceptIt
							.hasNext();) {
						Element acceptElement = (Element) acceptIt.next();
						BusinessAccept accept = new BusinessAccept();
						accept.setParamName(acceptElement
								.getAttributeValue("name"));
						accept.setParamDesc(acceptElement
								.getAttributeValue("desc"));
						accept.setParamVerify(acceptElement
								.getAttributeValue("restrict"));
						logger.debug("  " + accept.getParamName() + " "
								+ accept.getParamDesc() + " "
								+ accept.getParamVerify());
						desc.addAcceptParam(accept);
					}
				}
				
				if("DEAL".equals(subElement.getName().toUpperCase())){
					List dealChilds = subElement.getChildren();
					desc.setBd(getNextNode((Element) dealChilds.get(0)));
				}
				
				if("RETURN".equals(subElement.getName().toUpperCase())){
					BusinessReturn br = new BusinessReturn();
					List returnChilds = subElement.getChildren();
					for (Iterator returnIt = returnChilds.iterator(); returnIt
							.hasNext();) {
						Element returnElement = (Element) returnIt.next();
						if("DS".equals(returnElement.getName().toUpperCase())){
							br.addDs(returnElement.getAttributeValue("name"),
									returnElement.getAttributeValue("sql"));
							logger.debug("  " + returnElement.getName() + ":"
									+ returnElement.getAttributeValue("name")
									+ " "
									+ returnElement.getAttributeValue("sql"));
						}
						if("RESULT".equals(returnElement.getName().toUpperCase())){
							ResultDesc rd = new ResultDesc();
							rd.setDs(returnElement.getAttributeValue("ds"));
							rd.setKey(returnElement.getAttributeValue("key"));
							rd.setValue(returnElement.getAttributeValue("value"));
							rd.setFunc(returnElement.getAttributeValue("func"));
							rd.setParam(returnElement.getAttributeValue("param"));
							br.addResult(rd);
							logger.debug("  " + returnElement.getName() + ":"
									+ rd.getDs() + " " + rd.getKey() + " "
									+ rd.getValue() + " " + rd.getFunc() + " "
									+ rd.getParam());
						}
					}
					desc.setBr(br);
				}
			}
			BusinessConf.busiMap.put(desc.getJym(), desc);
		}
	}
	
	/**
	 * 递归方法,用于加载DEAL标签内的NODE子标签
	 * @param element
	 * @return
	 */
	private static BusinessDeal getNextNode(Element element){
		
		//由于通常都是返回BusinessDeal,所以定义为BusinessDeal
		BusinessDeal nd = new BusinessDeal();

		int type = NodeType.getNodeType(element.getAttributeValue("type"));
		nd.setSql(element.getAttributeValue("sql"));
		nd.setNodeType(type);

		logger.debug("  " + element.getAttributeValue("type") + " "
				+ nd.getSql());
		
		// 如果是NODE,则可能存在下级节点，进行递归装载
		if(element.getChildren().size() > 0){
			List childs = element.getChildren();
			for (Iterator it = childs.iterator(); it.hasNext();) {
				Element tmpElement = (Element) it.next();
				nd.addNext(getNextNode(tmpElement));
			}
		}

		return nd;
	}
	
	/**
	 * 装载原子交易配置信息
	 * @param element
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	private static void loadAtom(Element element)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		logger.debug("装载原子交易配置信息...");
		List childs = element.getChildren();
		for (Iterator it = childs.iterator(); it.hasNext();) {
			Element tmpElement = (Element) it.next();
			BusinessConf.atomMap.put(tmpElement.getAttributeValue("jym"),
					tmpElement.getAttributeValue("class"));
			logger.debug("原子交易:" + tmpElement.getAttributeValue("jym") + " 实现:"
					+ tmpElement.getAttributeValue("class"));
		}
	}
	
	
	public static void main(String[] args){
		XmlLoadUtil.loadFromXml("middlebusi.cfg.xml");
	}
}
