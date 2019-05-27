package com.irdstudio.ssm.framework.web;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.apache.commons.beanutils.BeanUtils;

import com.irdstudio.ssm.framework.constant.ApplicationConstance;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.vo.BaseInfo;

/**
 * 通用控制器接口，提供默认的三个方法为了控制层做转换
 * @author Cytus_
 * @since 2018-04-30 19:13
 * @version 1.0
 */
public interface IController {
	
	
	/**
	 * 通过传入前台传入方式组装查询信息
	 * @param baseInfo vo对象
	 * @param page 页数
	 * @param size 大小
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default <T> T getQueryObject(BaseInfo baseInfo, int page, int size) {
		baseInfo.setPage(page);
		baseInfo.setSize(size);
		return (T) baseInfo;
	}
	
	/**
	 * 返回对象组装
	 * @param outputVo
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@SuppressWarnings("rawtypes")
	default <T> ResponseData<T> getResponseData(T outputVo) {
		ResponseData<T> responseData = new ResponseData<T>();
		responseData.setRows(outputVo);
		try {
			if (Objects.nonNull(outputVo)) {
				if (outputVo instanceof Number) {
					Integer rtnNum = new BigDecimal(String.valueOf(outputVo)).intValue();
					if (rtnNum > -1) {
						responseData.setMessage(ApplicationConstance.SUCCESS_MESSAGE);
						responseData.setCode(ApplicationConstance.SUCCESS);
						responseData.setSuccess(true);
					} else {
						responseData.setMessage(ApplicationConstance.FAILURE_MESSAGE);
						responseData.setCode(ApplicationConstance.FAILURE);
						responseData.setSuccess(false);
					}
				} else if (outputVo instanceof List) {
					List list = (List) outputVo;
					if (!list.isEmpty()) {
						String total = BeanUtils.getProperty(list.get(0), "total");
						BeanUtils.setProperty(responseData, "total", total);
					}
					responseData.setMessage(ApplicationConstance.SUCCESS_MESSAGE);
					responseData.setCode(ApplicationConstance.SUCCESS);
					responseData.setSuccess(true);
				} else if (outputVo instanceof Boolean) {
					Boolean value = (Boolean) outputVo;
					if(value.booleanValue()) {
						responseData.setMessage(ApplicationConstance.SUCCESS_MESSAGE);
						responseData.setCode(ApplicationConstance.SUCCESS);
						responseData.setSuccess(true);
					} else {
						responseData.setMessage(ApplicationConstance.FAILURE_MESSAGE);
						responseData.setCode(ApplicationConstance.FAILURE);
						responseData.setSuccess(false);
					}
				} else {
					responseData.setMessage(ApplicationConstance.SUCCESS_MESSAGE);
					responseData.setCode(ApplicationConstance.SUCCESS);
					responseData.setSuccess(true);
				}
			} else {
				responseData.setMessage(ApplicationConstance.FAILURE_MESSAGE);
				responseData.setCode(ApplicationConstance.FAILURE);
				responseData.setSuccess(false);
			}
		} catch (Exception e) {
			responseData.setMessage(ApplicationConstance.FAILURE_MESSAGE);
			responseData.setCode(ApplicationConstance.FAILURE);
			responseData.setSuccess(false);
		}
		return responseData;
	}
	
	
}
