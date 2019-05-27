package com.irdstudio.smcenter.console.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.irdstudio.smcenter.console.service.api.SAgentInfoService;
import com.irdstudio.smcenter.console.service.vo.BatInstLoansMonitorVO;
import com.irdstudio.smcenter.console.service.vo.BatInstLoansTaskVO;
import com.irdstudio.smcenter.console.service.vo.SAgentInfoVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class BatInstLoansMonitor extends AbstractController {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	@Qualifier("sAgentInfoService")
	private SAgentInfoService sAgentInfoService;

	/**
	 * 核算日终任务列表
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/bat/loans/monitor", method = RequestMethod.POST)
	public @ResponseBody ResponseData<List<BatInstLoansMonitorVO>> queryBatInstBatchAll(BatInstLoansMonitorVO vo) {
		List<BatInstLoansMonitorVO> outputVo = new ArrayList<BatInstLoansMonitorVO>();

		SAgentInfoVO sAgentInfoVO = new SAgentInfoVO();
		sAgentInfoVO.setAgentId("ICF_LOCAL_AGENT");
		List<SAgentInfoVO> list = sAgentInfoService.queryAllOwner(sAgentInfoVO);
		if (list != null && list.size() > 0) {
			String agentUrl = list.get(0).getAgentUrl();
			JSONObject result = restTemplate.getForObject(agentUrl + "/ycloans/monitor?prcsDt=" + vo.getPRCS_DT(), JSONObject.class);
			if (result != null&&"00000".equals(result.get("errorCode"))) {
				JSONArray array = result.getJSONArray("DeProcLogList");
				for (Object object : array) {
					if (object == null)
						continue;
					outputVo.add(JSONObject.parseObject(((JSONObject) object).toJSONString(), BatInstLoansMonitorVO.class));
				}
			}
		}
		return getResponseData(outputVo);
	}
	
	/**
	 * 核算自动任务
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/bat/loans/getTasks", method = RequestMethod.POST)
	public @ResponseBody ResponseData<List<BatInstLoansTaskVO>> getTasks(BatInstLoansMonitorVO vo) {
		List<BatInstLoansTaskVO> outputVo = new ArrayList<BatInstLoansTaskVO>();

		SAgentInfoVO sAgentInfoVO = new SAgentInfoVO();
		sAgentInfoVO.setAgentId("ICF_LOCAL_AGENT");
		List<SAgentInfoVO> list = sAgentInfoService.queryAllOwner(sAgentInfoVO);
		if (list != null && list.size() > 0) {
			String agentUrl = list.get(0).getAgentUrl();
			JSONObject result = restTemplate.getForObject(agentUrl + "/ycloans/getTasks", JSONObject.class);
			if (result != null&&"00000".equals(result.get("errorCode"))) {
				JSONArray array = result.getJSONArray("AcctInfoTimedTaskList");
				for (Object object : array) {
					if (object == null)
						continue;
					outputVo.add(JSONObject.parseObject(((JSONObject) object).toJSONString(), BatInstLoansTaskVO.class));
				}
			}
		}
		return getResponseData(outputVo);
	}
	
	/**
	 * 任务重跑
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/bat/loans/runTask", method = RequestMethod.POST)
	public @ResponseBody ResponseData<JSONObject> runTask(@RequestBody BatInstLoansTaskVO vo) {
		JSONObject result = new JSONObject();
		SAgentInfoVO sAgentInfoVO = new SAgentInfoVO();
		sAgentInfoVO.setAgentId("ICF_LOCAL_AGENT");
		List<SAgentInfoVO> list = sAgentInfoService.queryAllOwner(sAgentInfoVO);
		if (list != null && list.size() > 0) {
			String agentUrl = list.get(0).getAgentUrl();
			result= restTemplate.getForObject(agentUrl + "/ycloans/runTask?seqNo=" + vo.getSeqNo(), JSONObject.class);
			if (result != null&&"00000".equals(result.get("errorCode"))) {
				return getResponseData(result);
			}
		}
		return getResponseData(null);
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
