package com.irdstudio.smcenter.core.assembly.jxp.loader;
/**
 * @docRoot
 * Define段的装载者
 * @author 李广明
 * @version 1.0
 * @date 2006-11-13
 *
 */
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.irdstudio.smcenter.core.assembly.jxp.conf.DefineSection;
public class DefineLoader implements IEcLoader{

	/**
	 * 实现装载者接口
	 */
	public Object load(Object obj) {
		
		DefineSection define = new DefineSection();
		Element element = (Element) obj;
		List<?> childs = element.getChildren();
		
		for (Iterator<?> it = childs.iterator(); it.hasNext();) {
			Element tmpElement = (Element) it.next();
			if ("PARAM".equals(tmpElement.getName().toUpperCase())) {
				Map<String, String> tmpMap = new Hashtable<String, String>();
				String[] values = 
					tmpElement.getAttributeValue("value").split(",");
				for (int i = 0; i < values.length; i = i + 2) {
					tmpMap.put(values[i], values[i + 1]);
				}
				define.putDefine(tmpElement.getAttributeValue("name"), tmpMap);
			}
		}
		return define;
	}
}
