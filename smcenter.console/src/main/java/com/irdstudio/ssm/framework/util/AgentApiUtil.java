package com.irdstudio.ssm.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import com.irdstudio.ssm.framework.vo.ResponseVO;
import com.irdstudio.ssm.framework.web.http.HttpClient;
import com.irdstudio.ssm.framework.web.http.impl.DefRestfulHttpClient;

public class AgentApiUtil {
	
	protected static Logger logger = LoggerFactory.getLogger(AgentApiUtil.class);

	/**
	 * 激活Agent
	 * @param agentUrl
	 * @param agentId
	 * @return
	 */
	public static ResponseVO activeAgent(String agentUrl, String agentId) {
		ResponseVO response = null;
		try {
			HttpClient<Object, ResponseVO> defaultClient = new DefRestfulHttpClient<>();
			response = defaultClient.httpCall(agentUrl + "/active?agentId={agentId}", HttpMethod.GET, null, ResponseVO.class, agentId);
		} catch (Exception e) {
			response = new ResponseVO();
			response.setFlag(ResponseVO.FAIL);
			response.setMsg("无法调用远程地址：" + agentUrl);
			
			logger.error("无法调用远程地址：" + agentUrl, e);
		}
		
		return response;
	}
	
	/**
	 * 停止Agent
	 * @param agentUrl
	 * @param agentId
	 * @return
	 */
	public static ResponseVO stopAgent(String agentUrl, String agentId) {
		ResponseVO response = null;
		try {
			HttpClient<Object, ResponseVO> defaultClient = new DefRestfulHttpClient<>();
			response = defaultClient.httpCall(agentUrl + "/stop?agentId={agentId}", HttpMethod.GET, null, ResponseVO.class, agentId);
		} catch (Exception e) {
			response = new ResponseVO();
			response.setFlag(ResponseVO.FAIL);
			response.setMsg("无法调用远程地址：" + agentUrl);
			
			logger.error("无法调用远程地址：" + agentUrl, e);
		}
		
		return response;
	}
	
	/**
	 * 启动批次
	 * @param agentUrl
	 * @param batchId
	 * @param batchAction
	 * @return
	 */
	public static ResponseVO actionBatch(String agentUrl, String batchId, String batchAction) {
		ResponseVO response = null;
		try {
			HttpClient<Object, ResponseVO> defaultClient = new DefRestfulHttpClient<>();
			response = defaultClient.httpCall(agentUrl + "/batch/start?batchId={batchId}&batchAction={batchAction}", HttpMethod.GET, null, ResponseVO.class, batchId, batchAction);
		} catch (Exception e) {
			response = new ResponseVO();
			response.setFlag(ResponseVO.FAIL);
			response.setMsg("无法调用远程地址：" + agentUrl);
			
			logger.error("无法调用远程地址：" + agentUrl, e);
		}
		
		return response;
	}
}
