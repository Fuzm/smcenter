package com.irdstudio.smcenter.core.assembly.jxp.loader;
/**
 * @docRoot
 * XML业务规则文件主装载器
 * @author 李广明
 * @version 1.0
 * @date 2006-11.13
 * @modify 2009-01-04
 * 	修改装载程序,使其能够装载Attr
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.irdstudio.smcenter.core.assembly.jxp.conf.ConfigureEntry;
import com.irdstudio.smcenter.core.assembly.jxp.conf.DefineSection;
import com.irdstudio.smcenter.core.assembly.jxp.conf.NodeSection;
import com.irdstudio.smcenter.core.assembly.jxp.conf.SqlSection;
import com.irdstudio.smcenter.core.assembly.jxp.util.LogUtil;
public class MainLoader {
	
	/**
	 * 装载业务规则描述文件
	 * @param fullFilePath
	 * @return
	 */
	public final static ConfigureEntry loadCoreXml(String fullFilePath) {
		
		ConfigureEntry cxd = null;

		// 检查文件是否存在
		if (fullFilePath != null && !"".equals(fullFilePath)) {

			SAXBuilder sb = new SAXBuilder();
			Document doc = null;

			// 打开文件,读出配置
			try {

				doc = sb.build(new FileInputStream(fullFilePath));

				// 取得根元素
				Element root = doc.getRootElement();

				// 取得所有第一级元素
				List<?> childs = root.getChildren();

				// 定义装载者接口对象
				IEcLoader loader = null;

				cxd = new ConfigureEntry();

				// 循环取得第一级元素的内容
				for (Iterator<?> it = childs.iterator(); it.hasNext();) {
					Element element = (Element) it.next();

					// 装载SQL标签中的描述到SqlDesc中
					if ("SQL".equals(element.getName().toUpperCase())) {
						LogUtil.out("加载预执行SQL");
						loader = new SqlLoader();
						cxd.setSqlSection((SqlSection) loader.load(element));
					}

					// 装载define标签中的描述到DefineDesc中
					if ("DEFINE".equals(element.getName().toUpperCase())) {
						LogUtil.out("加载定义");
						loader = new DefineLoader();
						cxd.setDefineSection((DefineSection) loader.load(element));
					}

					// 装载node标签中的描述到NodeDesc中
					if ("NODE".equals(element.getName().toUpperCase())) {
						LogUtil.out("加载节点");
						loader = new NodeLoader();
						cxd.setNodeSection((NodeSection) loader.load(element));
					}
				}
			} catch (FileNotFoundException e) {
				System.err.println("文件" + fullFilePath + "没有找到!");
				e.printStackTrace();
				return null;
			} catch (JDOMException e) {
				System.err.println("读XML文件异常,请检查XML文件!");
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				System.err.println("读XML文件异常,请检查XML文件!");
				e.printStackTrace();
			} finally {
				doc = null;
			}
		}
		return cxd;
	}

}
