package com.irdstudio.ssm.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.resource.ClasspathResourceLoader;

import com.irdstudio.ssm.framework.constant.TemplatePathConstant;


/**
 * 外部接口请求报文模板处理工具，参考文档：http://ibeetl.com/guide/
 *
 * @author 
 * @version 2017-07-31
 */
public final class TemplateUtils {

    private TemplateUtils() {}

    private static GroupTemplate groupTemplate;
    private static Properties properties = getProperties("templateCfg.properties");
    
    static {
        ResourceLoader resourceLoader = new ClasspathResourceLoader(properties.getProperty("templateFilePath"));
        Configuration cfg;
        try {
            cfg = Configuration.defaultConfiguration();
            // 默认出异常时只是打印日志在控制台而不处理异常，此处设置要往上抛异常
        } catch (IOException e) {
            throw new BeetlException(e.getMessage(), e);
        }
        groupTemplate = new GroupTemplate(resourceLoader, cfg);
        
        // 设置全局变量，模板里可以共用这些配置项
        groupTemplate.setSharedVars(new HashMap(properties));
    }

    /**
     * 获取替换参数后的模板字符串
     * @param pathConstant 模板常量
     * @param bean 入参对象，所有属性必须是基本数据类型（即：属性不是一个bean，不会属性里嵌套其他多个属性）
     * @return 模板字符串
     */
    public static String getTemplate(TemplatePathConstant pathConstant, Object bean) {
        Map<String, Object> paramMap = null;
        if (bean != null) {
            paramMap = BeanUtility.bean2Map(bean);
        }

        return getTemplate(pathConstant, paramMap);
    }
    
    public static void getTemplate(TemplatePathConstant pathConstant, Object bean, Writer writer) {
        Map<String, Object> paramMap = null;
        if (bean != null) {
            paramMap = BeanUtility.bean2Map(bean);
        }
        getTemplate(pathConstant, paramMap, writer);
    }
    
    public static String getTemplateAll(TemplatePathConstant pathConstant, Object bean) {
        Map<String, Object> paramMap = null;
        if (bean != null) {
            paramMap = BeanUtility.bean2Map(bean);
        }
        
        return getTemplate(pathConstant, paramMap);
    }

    /**
     * 获取替换参数后的模板字符串
     * @param pathConstant 模板常量
     * @param paramMap 入参Map
     * @return 模板字符串
     */
    public static String getTemplate(TemplatePathConstant pathConstant, Map<String, Object> paramMap) {
        if (pathConstant == null) {
            throw new NullPointerException("MsgTemplatePathConstant为空！");
        }

        long beginTime = System.currentTimeMillis();
        Template template = groupTemplate.getTemplate(pathConstant.getPath());
        template.binding(paramMap);
        //自动识别list
        List<String> listMapKeys = getMapKeys(paramMap);
        for(String key : listMapKeys){
            if(paramMap.get(key) instanceof List){
                template.binding(key, paramMap.get(key));
            }
        }
        String result = template.render();
        long endTime = System.currentTimeMillis();
        System.err.println("替换模板["+pathConstant.getPath()+"]耗时：" + (endTime-beginTime) + "毫秒！");
        return result;
    }
    
    public static void getTemplate(TemplatePathConstant pathConstant, Map<String, Object> paramMap, Writer writer) {
        if (pathConstant == null) {
            throw new NullPointerException("MsgTemplatePathConstant为空！");
        }

        long beginTime = System.currentTimeMillis();
        Template template = groupTemplate.getTemplate(pathConstant.getPath());
        //Template template = groupTemplate.getTemplate("classpath:template/"+pathConstant.getPath());
        template.binding(paramMap);
        //自动识别list
        List<String> listMapKeys = getMapKeys(paramMap);
        for(String key : listMapKeys){
            if(paramMap.get(key) instanceof List){
                template.binding(key, paramMap.get(key));
            }
        }
        template.renderTo(writer);
        long endTime = System.currentTimeMillis();
        System.err.println("替换模板["+pathConstant.getPath()+"]耗时：" + (endTime-beginTime) + "毫秒！");
    }
    
    /**
     * 获取Map中的key
     * 
     * @param map
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<String> getMapKeys(Map<?, ?> map) {
        List<String> list = new ArrayList<String>();
        Iterator it = map.entrySet().iterator();
        Map.Entry entry = null;
        while (it.hasNext()) {
            entry = (Map.Entry) it.next();
            list.add((String) entry.getKey());
        }
        return list;
    }
    /**
     * 获取properties文件
     * @param fileName
     * @return
     */
    public static Properties getProperties(String fileName) {
    	Properties prop=new Properties(); 
    	InputStream is =TemplateUtils.class.getClassLoader().getResourceAsStream("templateCfg.properties");
    	try {
			prop.load(is);
			return prop;
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
}
