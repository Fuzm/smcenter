package com.irdstudio.ssm.framework.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 根据模板生成xml文件,原代码提供chenqm
 * @author Cytus_
 * @version 1.0
 * @since 2018-04-27 08:43:53
 */
public class Bean2TemplateXmlUtility {
	
	private static final String XML_TEMPLATE_FILES_ROOT_PATH = "xmlTempalate/";
	private static final String TEMPLATE_PROPERTIES_PATH = "ibeetl.properties";
	
	private static GroupTemplate groupTemplate;
    static {
        ResourceLoader resourceLoader = new ClasspathResourceLoader(XML_TEMPLATE_FILES_ROOT_PATH);
        Configuration cfg = null;
        try {
            cfg = Configuration.defaultConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        groupTemplate = new GroupTemplate(resourceLoader, cfg);

        Properties properties;
        try {
            properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(TEMPLATE_PROPERTIES_PATH));
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        // 设置全局变量，模板里可以共用这些配置项
        groupTemplate.setSharedVars(new HashMap(properties));
    }
    
    
    public static String getTemplate(String xmlTemplatePath, Map<String, Object> paramMap) {
        if (Objects.isNull(xmlTemplatePath)) {
            throw new NullPointerException("MsgTemplatePathConstant为空！");
        }

        Template template = groupTemplate.getTemplate(xmlTemplatePath);
        template.binding(paramMap);
        paramMap.keySet().stream().forEach(s -> {if (paramMap.get(s) instanceof List) template.binding(s, paramMap.get(s));});
        return template.render();
    }
    
}
