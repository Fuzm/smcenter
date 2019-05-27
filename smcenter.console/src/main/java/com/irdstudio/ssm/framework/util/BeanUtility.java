package com.irdstudio.ssm.framework.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;

import com.irdstudio.ssm.framework.annotation.BeanMapKey;

/**
 * java bean的一些操作工具类
 * @author Cytus_
 * @since 2018-08-28 10:12:06
 * @version 1.0
 */
public class BeanUtility {

	/**
	 * 将javabean转换成Map对象，使用cglib的BeanMap进行转换
	 * @param bean
	 * @return
	 */
    public static Map<String, Object> bean2MapByBeanMap(Object bean) {
        if (bean == null) {
            return Collections.emptyMap();
        }
        try {
        	Map<String, Object> rtnMap = new HashMap<String, Object>();
        	BeanMap beanMap = BeanMap.create(bean);
        	for (Object key : beanMap.keySet()) {
        		Object value = beanMap.get(key);
        		if (Objects.nonNull(value)) {
        			if (value instanceof String || value instanceof Number) {
        				rtnMap.put(key +"", value);
        			} else if (value instanceof List) {
        				rtnMap.put(key +"", listBean2ListByBeanMap(value));
        			} else {
        				rtnMap.put(key +"", bean2MapByBeanMap(value));
        			}
        		}
        	}
        	return rtnMap;
        } catch (Exception e) {
            throw new IllegalArgumentException("bean2Map fail: " + e.getMessage(), e);
        }
    }
    
    /**
     * 将list bean对象转换成List<Map>对象 与{@link bean2MapByBeanMap}方法配合使用，使用cglib的BeanMap进行转换
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
	public static List<Map<String, Object>> listBean2ListByBeanMap(Object obj) {
    	List<Object> list = (List<Object>) obj;
    	List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
    	for (Object listObj : list) {
    		Map<String, Object> beanMap = bean2MapByBeanMap(listObj);
    		listMap.add(beanMap);
    	}
    	return listMap;
    }
    

    /**
     * list对象转成list bean
     * @param list
     * @param clazz
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T list2BeanList(List<Map<String, Object>> list, Class<?> clazz) throws Exception {
    	List<?> bean = new ArrayList();
    	for (Map<String, Object> dataMap : list) {
    		Object object = clazz.newInstance();
    		bean.add(map2Bean(dataMap, object));
    	}
    	return (T) bean;
    }
    
    @SuppressWarnings("unchecked")
	public static <T> T map2BeanByJSON(Map<String, Object> dataMap, Object bean) {
    	String gson = GsonUtils.toJson(dataMap);
    	return (T) GsonUtils.getJson(gson, bean.getClass());
    }
    
    /**
     * map转bean   目前List暂无处理
     * @param dataMap
     * @param bean
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T map2Bean(Map<String, Object> dataMap, Object bean) {
    	if (Objects.isNull(bean)) {
    		throw new NullPointerException("exchange object must not null!");
    	}
    	
    	if (Objects.isNull(dataMap) || dataMap.isEmpty()) {
    		return (T) bean;
    	}
    	
    	try {
    		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (dataMap.containsKey(key) && !"class".equals(key)) {
                    Object value = dataMap.get(key);
                    if (value instanceof String || value instanceof Number) {
                    	property.getWriteMethod().invoke(bean, value);
                    } else if (value instanceof List) {
                    	property.getWriteMethod().invoke(bean, listBean2List(value));
                    } else {
                    	Class clazz = property.getPropertyType();      
                    	Object obj = clazz.newInstance();
                    	Map<String, Object> beanMap = (Map<String, Object>) value;
                    	obj = map2Bean(beanMap, obj);
                    	property.getWriteMethod().invoke(bean, obj);
                    }
                }
            }
            return (T) bean;
        } catch (Exception e) {
            throw new IllegalArgumentException("map2Bean fail: " + e.getMessage(), e);
        }
    }
    
    /**
     * 将list bean对象转换成List< Map >对象 与{@link bean2Map}方法配合使用，使用jdk自带的的BeanInfo进行转换
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
	public static List<Map<String, Object>> listBean2List(Object obj) {
    	List<Object> list = (List<Object>) obj;
    	List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
    	for (Object listObj : list) {
    		Map<String, Object> beanMap = bean2Map(listObj);
    		listMap.add(beanMap);
    	}
    	return listMap;
    }
    
    /**
	 * 将javabean转换成Map对象，使用jdk自带的的BeanInfo进行转换
	 * @param bean
	 * @return
	 */
    public static Map<String, Object> bean2Map(Object bean) {
        if (bean == null) {
            return Collections.emptyMap();
        }
        
        try {
            Map<String, Object> beanMap = new HashMap<String, Object>();
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                
                if (!key.equals("class")) {
                	Object value = property.getReadMethod().invoke(bean);
                	
                	if (property.getPropertyType().isAssignableFrom(String.class) || property.getPropertyType().isAssignableFrom(Number.class)) {
                		beanMap.put(key, value);
                	} else if (value instanceof List) {
                		beanMap.put(key, listBean2List(value));
                	} else {
                		beanMap.put(key, bean2Map(value));
                	}
                }
                
            }
            return beanMap;
            
        } catch (Exception e) {
            throw new IllegalArgumentException("bean2Map fail: " + e.getMessage(), e);
        }
    }
    /**
	 * 将javabean转换成Map对象，使用jdk自带的的BeanInfo进行转换，并将属性首字母转大写
	 * @param bean
	 * @return
	 */
    public static Map<String, Object> bean2MapAndUpperCase(Object bean) {
        if (bean == null) {
            return Collections.emptyMap();
        }
        try {
            Map<String, Object> beanMap = new HashMap<String, Object>();
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (!key.equals("class")) {
                	Object value = property.getReadMethod().invoke(bean);
                	
                	if (property.getPropertyType().isAssignableFrom(String.class) || property.getPropertyType().isAssignableFrom(Number.class)) {
                		key = upperCase(key);
                		beanMap.put(key, value);
                	} else if (value instanceof List) {
                		beanMap.put(key, listBean2List(value));
                	} else {
                		key = upperCase(key);
                		beanMap.put(key, bean2MapAndUpperCase(value));
                	}
                }
                
            }
            return beanMap;
            
        } catch (Exception e) {
            throw new IllegalArgumentException("bean2Map fail: " + e.getMessage(), e);
        }
    }
    /**
	 * 首字母转大写
	 * @param str
	 * @return
	 */
    public static String upperCase(String str) {  
        return str.substring(0, 1).toUpperCase() + str.substring(1);  
    }
    
    /**
     * bean copy
     * @param srcBean
     * @param targetBean
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T beanCopy(Object srcBean, Object targetBean) {
		if (Objects.nonNull(targetBean) && Objects.nonNull(srcBean)) {
			BeanCopier beanCopier = BeanCopier.create(srcBean.getClass(), targetBean.getClass(), false);
			beanCopier.copy(srcBean, targetBean, null);
			return (T) targetBean;
		}
		return null;
	}
    
    /**
     * 通过注解将bean转成Map
     * @param bean
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public static Map<String, Object> bean2MapByAnno(Object bean) throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	if (bean instanceof Map) {
    		return (Map<String, Object>)bean;
    	}
    	if (Objects.nonNull(bean)) {
    		List<Field> fields = new ArrayList<Field>(Arrays.asList(bean.getClass().getDeclaredFields()));
    		fields.addAll(Arrays.asList(bean.getClass().getSuperclass().getDeclaredFields()));
    		for (Field field : fields) {
    			BeanMapKey beanMap = field.getAnnotation(BeanMapKey.class);
    			if (Objects.nonNull(beanMap)) {
    				field.setAccessible(true);
    				Object obj = field.get(bean);
    				if (obj instanceof String || obj instanceof Number || obj instanceof Boolean) {
    					
    				} else if (obj instanceof List) {
    					obj = listBean2MapByAnno((List<Object>) obj);
    				} else {
    					obj = bean2MapByAnno(obj);
    				}
    				map.put(beanMap.value(), obj);
    			}
    		}
    	}
    	
		return map;
    }
    
    /**
     * 配合{@link bean2MapByAnno}使用
     * @param list
     * @return
     * @throws Exception
     */
    public static List<Object> listBean2MapByAnno(List<Object> list) throws Exception {
    	List<Object> listMap = new ArrayList<Object>();
    	if (Objects.nonNull(list) && !list.isEmpty()) {
    		Object o = list.get(0);
    		if (o instanceof String || o instanceof Number || o instanceof Boolean)
    			return list;
    		for (Object obj : list) {
    			listMap.add(bean2MapByAnno(obj));
    		}
    	}
    	return listMap;
    }  
    
    
    /**
     * map 转bean 通过注解
     * @param dataMap
     * @param clazz
     * @return
     * @throws Exception
     */
	public static <T> T map2BeanByAnno(Map<String, Object> dataMap, Class<T> clazz) throws Exception {
    	T t = clazz.newInstance();
    	return map2BeanByAnno(dataMap, t);
    }
    
    /**
     * map 转bean 通过注解
     * @param dataMap
     * @param clazz
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public static <T> T map2BeanByAnno(Map<String, Object> dataMap, T object) throws Exception {
    	if (Objects.nonNull(dataMap) && !dataMap.isEmpty() && Objects.nonNull(object)) {
    		Field[] fields = object.getClass().getDeclaredFields();
    		for (Field field : fields) {
    			BeanMapKey beanMap = field.getAnnotation(BeanMapKey.class);
    			if (Objects.nonNull(beanMap)) {
    				field.setAccessible(true);
    				Object valObj = dataMap.get(beanMap.value());
    				if (Objects.isNull(valObj) || StringUtil.isEmpty(valObj.toString())) continue;
    				if (valObj instanceof List) {
    					ParameterizedType type = (ParameterizedType) field.getGenericType();
        				Class<?> clz = (Class<?>) type.getActualTypeArguments()[0];
        				valObj = list2BeanByAnno((List<Object>) valObj, clz);
    				} else if (valObj instanceof Map) {
    					Object generClass = field.get(object);
    					Class<?> clz = null;
    					/*
    					 * 在传入的class中优先获得定义的对象属性的值，该修改是为了成员变量如果为泛型或者
    					 * 直接定义为Object对象或者为接口对象时，在传入的实例化的对象时优先实例化了该变量，
    					 * 便可获得实际改变量的class类型，如果从Field中获取这可能获取不准确，因而导致了
    					 * 对应的值不能设置到匹配的属性中
    					 * modify by Cytus_ at 2018-05-14
    					 */
    					if (Objects.isNull(generClass)) {
    						clz = Class.forName(field.getGenericType().getTypeName());
    					} else {
    						clz = generClass.getClass();
    					}
    					valObj = map2BeanByAnno((Map<String, Object>) valObj, clz);
    				}
    				field.set(object, valObj);
    				
    			}
    		}
    		
    	}
    	
    	return object;
    }
    
    /**
     * 配合{@link map2BeanByAnno}使用
     * @param list
     * @param clazz
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public static List<Object> list2BeanByAnno(List<Object> list, Class<?> clazz) throws Exception {
    	
    	List<Object> dataList = new ArrayList<Object>();
    	if (Objects.nonNull(list) && !list.isEmpty() && Objects.nonNull(clazz)) {
    		if (list.get(0) instanceof Map) {
    			for (Object obj : list) {
	    			Map<String, Object> dataMap = (Map<String, Object>) obj;
	    			dataList.add(map2BeanByAnno(dataMap, clazz));
    			}
    		} else {
    			dataList.add(list);
    		}
    		
    	}
    	return dataList;
    }
    
    public static Object getProptery(Object object, String name) throws Exception {
    	String clazz = object.getClass().getName();
    	if (clazz.matches(".+Map$")) {
    		return object.getClass().getMethod("get", Object.class).invoke(object, name);
    	} else if (object instanceof Collection) {
    		return null;
    	} else if (object instanceof String || object instanceof Number) {
    		return object;
    	} else {
    		StringBuffer sb = new StringBuffer(name);
    		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
    		return object.getClass().getMethod("get"+ sb.toString()).invoke(object);
    	}
    	
    }
    
    
}
