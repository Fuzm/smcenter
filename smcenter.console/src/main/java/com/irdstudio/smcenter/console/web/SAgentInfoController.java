package com.irdstudio.smcenter.console.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.irdstudio.smcenter.console.service.api.SAgentInfoService;
import com.irdstudio.smcenter.console.service.vo.SAgentInfoVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.util.AgentApiUtil;
import com.irdstudio.ssm.framework.util.StringUtil;
import com.irdstudio.ssm.framework.vo.ResponseVO;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class SAgentInfoController extends AbstractController {

	@Autowired
	@Qualifier("sAgentInfoService")
	private SAgentInfoService sAgentInfoService;

	/**
	 * 列表数据查询
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/s/agent/infos", method = RequestMethod.POST)
	public @ResponseBody ResponseData<List<SAgentInfoVO>> querySAgentInfoAll(SAgentInfoVO vo) {
		List<SAgentInfoVO> outputVo = sAgentInfoService.queryAllOwner(vo);
		return getResponseData(outputVo);

	}

	/**
	 * 根据主键查询详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "/s/agent/info/{agentId}", method = RequestMethod.GET)
	public @ResponseBody ResponseData<SAgentInfoVO> queryByPk(@PathVariable("agentId") String agentId) {
		SAgentInfoVO inVo = new SAgentInfoVO();
		inVo.setAgentId(agentId);
		SAgentInfoVO outputVo = sAgentInfoService.queryByPk(inVo);
		return getResponseData(outputVo);

	}

	/**
	 * 根据主键删除信息
	 * 
	 * @param sAgentInfo
	 * @return
	 */
	@RequestMapping(value = "/s/agent/info", method = RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody SAgentInfoVO inSAgentInfoVo) {
		int outputVo = sAgentInfoService.deleteByPk(inSAgentInfoVo);
		return getResponseData(outputVo);

	}

	/**
	 * 根据主键更新信息
	 * 
	 * @param inSAgentInfoVo
	 * @return
	 */
	@RequestMapping(value = "/s/agent/info", method = RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody SAgentInfoVO inSAgentInfoVo) {
		int outputVo = sAgentInfoService.updateByPk(inSAgentInfoVo);
		return getResponseData(outputVo);

	}

	/**
	 * 新增数据
	 * 
	 * @param inSAgentInfoVo
	 * @return
	 */
	@RequestMapping(value = "/s/agent/info", method = RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertSAgentInfo(@RequestBody SAgentInfoVO inSAgentInfoVo) {
		int outputVo = sAgentInfoService.insertSAgentInfo(inSAgentInfoVo);
		return getResponseData(outputVo);

	}

	/**
	 * 激活Agent操作
	 * 
	 * @param agentId
	 * @return
	 */
	@RequestMapping(value = "/s/srvs/cron/active/{agentId}", method = RequestMethod.GET)
	public @ResponseBody ResponseData<Boolean> activeSSrvsCronConf(@PathVariable("agentId") String agentId) {
		SAgentInfoVO inVo = new SAgentInfoVO();
		inVo.setAgentId(agentId);
		SAgentInfoVO outputVo = sAgentInfoService.queryByPk(inVo);

		//执行远程API调用
		ResponseData<Boolean> data = null;
		if (outputVo != null && StringUtil.isNotEmpty(outputVo.getAgentUrl())) {
			
			try {
				ResponseVO response = AgentApiUtil.activeAgent(outputVo.getAgentUrl(), agentId);
				if (response != null && StringUtil.isNotEmpty(response.getFlag())
						&& ResponseVO.SUCCESS.equals(response.getFlag())) {
					data = getResponseData(true);
					if (response != null && StringUtil.isNotEmpty(response.getMsg())) {
						data.setMessage(response.getMsg());
						logger.info(response.getMsg());
					} else {
						data.setMessage("激活成功！");
						logger.info("激活成功！agentId:" + agentId + ", agent地址：" + outputVo.getAgentUrl());
					}
				} else {
					data = getResponseData(false);
					if (response != null && StringUtil.isNotEmpty(response.getMsg())) {
						data.setMessage(response.getMsg());
						logger.info(response.getMsg());
					} else {
						data.setMessage("激活成功！");
						logger.info("激活成功！");
					}
				}

			} catch (Exception e) {
				logger.error("激活无法调用远程地址：" + outputVo.getAgentUrl(), e);

				data = getResponseData(false);
				data.setMessage("激活无法调用远程地址：" + outputVo.getAgentUrl());
			}

		} else {
			logger.error("没有找到配置信息");
			data = getResponseData(false);
			data.setMessage("没有找到配置信息！");
		}

		return data;
	}
	
	/**
	 * 停止Agent操作
	 * 
	 * @param agentId
	 * @return
	 */
	@RequestMapping(value = "/s/srvs/cron/stop/{agentId}", method = RequestMethod.GET)
	public @ResponseBody ResponseData<Boolean> stopSSrvsCronConf(@PathVariable("agentId") String agentId) {
		SAgentInfoVO inVo = new SAgentInfoVO();
		inVo.setAgentId(agentId);
		SAgentInfoVO outputVo = sAgentInfoService.queryByPk(inVo);

		//执行远程API调用
		ResponseData<Boolean> data = null;
		if (outputVo != null && StringUtil.isNotEmpty(outputVo.getAgentUrl())) {
			
			try {
				ResponseVO response = AgentApiUtil.stopAgent(outputVo.getAgentUrl(), agentId);
				if (response != null && StringUtil.isNotEmpty(response.getFlag())
						&& ResponseVO.SUCCESS.equals(response.getFlag())) {
					
					data = getResponseData(true);
					if (response != null && StringUtil.isNotEmpty(response.getMsg())) {
						data.setMessage(response.getMsg());
						logger.info(response.getMsg());
					} else {
						data.setMessage("停止成功！");
						logger.info("停止成功！agentId:" + agentId + ", agent地址：" + outputVo.getAgentUrl());
					}
				} else {
					data = getResponseData(false);
					if (response != null && StringUtil.isNotEmpty(response.getMsg())) {
						data.setMessage(response.getMsg());
						logger.info(response.getMsg());
					} else {
						data.setMessage("停止失败！");
						logger.info("停止失败！");
					}
				}

			} catch (Exception e) {
				logger.error("无法调用远程地址：" + outputVo.getAgentUrl(), e);

				data = getResponseData(false);
				data.setMessage("无法调用远程地址：" + outputVo.getAgentUrl());
			}

		} else {
			logger.error("没有找到配置信息");
			data = getResponseData(false);
			data.setMessage("没有找到配置信息！");
		}

		return data;
	}
}
