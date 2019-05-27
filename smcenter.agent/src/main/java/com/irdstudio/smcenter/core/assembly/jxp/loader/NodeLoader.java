package com.irdstudio.smcenter.core.assembly.jxp.loader;
/**
 * @docRoot
 * Node段的装载者
 * @author 李广明
 * @version 1.0
 * @date 2006-11-13
 *
 */
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import com.irdstudio.smcenter.core.assembly.jxp.conf.NodeAttrSection;
import com.irdstudio.smcenter.core.assembly.jxp.conf.NodeSection;
import com.irdstudio.smcenter.core.assembly.jxp.util.LogUtil;
import com.irdstudio.smcenter.core.assembly.jxp.util.NodeUtil;
import com.irdstudio.smcenter.core.util.pub.Convert;
public class NodeLoader implements IEcLoader{

	/**
	 * 实现装载者接口
	 */
	public Object load(Object obj) {
		Element element = (Element) obj;
		return getNextNode(element);
	}
	
	/**
	 * 递归取得下级节点描述及输出描述
	 * @param element
	 * @return
	 */
	private Object getNextNode(Element element){
		
		//由于通常都是返回NodeDesc,所以定义为NodeDesc
		NodeSection nd = new NodeSection();
		

		//装载节点描述数据
		int type = NodeUtil.getNodeType(element.getAttributeValue("type"));
		nd.setLabelName(element.getAttributeValue("labelName"));
		LogUtil.log("加载节点[" + nd.getLabelName() + "]");
		nd.setType(type);
		nd.setField(element.getAttributeValue("field"));
		nd.setFunc(element.getAttributeValue("func"));
		nd.setMaxLen(Convert.StrToInt(element.getAttributeValue("maxLen"), 0));
		nd.setParam(element.getAttributeValue("param"));
		nd.setDataSrc(element.getAttributeValue("dataSrc"));
		
		//判断是否仍属性结点
		if(element.getChildren().size() > 0){
			List<?> childs = element.getChildren();
			for (Iterator<?> it = childs.iterator(); it.hasNext();) {
				Element tmpElement = (Element) it.next();
				if ("NODE".equalsIgnoreCase(tmpElement.getName())) {
					nd.addNext(getNextNode(tmpElement));
				} else if("ATTRS".equalsIgnoreCase(tmpElement.getName())){
					List<?> attrChilds = tmpElement.getChildren();
					for (Iterator<?> itAttrs = attrChilds.iterator(); itAttrs
							.hasNext();) {
						Element attrElement = (Element) itAttrs.next();
						NodeAttrSection attr = new NodeAttrSection();
						attr.setName(attrElement.getAttributeValue("name"));
						attr.setValue(attrElement.getAttributeValue("value"));
						nd.addAttr(attr);
					}
				}
			}
		}
		
		return nd;
	}
}
