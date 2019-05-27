package com.irdstudio.smcenter.console.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.irdstudio.smcenter.console.service.api.BatBatchInfoConfigService;
import com.irdstudio.smcenter.console.service.api.SAgentInfoService;
import com.irdstudio.smcenter.console.service.api.SSrvsCronConfService;
import com.irdstudio.smcenter.console.service.vo.BatBatchInfoConfigVO;
import com.irdstudio.smcenter.console.service.vo.SAgentInfoVO;
import com.irdstudio.smcenter.console.service.vo.SSrvsCronConfVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.util.AgentApiUtil;
import com.irdstudio.ssm.framework.util.StringUtil;
import com.irdstudio.ssm.framework.vo.ResponseVO;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class BatBatchInfoConfigController extends AbstractController  {
	
	@Autowired
	@Qualifier("batchConfigService")
	private BatBatchInfoConfigService batBatchInfoConfigService;
	
	@Autowired
	@Qualifier("sSrvsCronConfService")
	private SSrvsCronConfService sSrvsCronConfService;
	
	@Autowired
	@Qualifier("sAgentInfoService")
	private SAgentInfoService sAgentInfoService;

	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/bat/batch/info/configs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<BatBatchInfoConfigVO>> queryBatBatchInfoConfigAll(
			BatBatchInfoConfigVO vo) {		
		List<BatBatchInfoConfigVO> outputVo = batBatchInfoConfigService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/bat/batch/info/config/{batchId}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<BatBatchInfoConfigVO> queryByPk(@PathVariable("batchId") String batchId) {		
		BatBatchInfoConfigVO inVo = new BatBatchInfoConfigVO();
				inVo.setBatchId(batchId);
		BatBatchInfoConfigVO outputVo = batBatchInfoConfigService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param batBatchInfoConfig
	 * @return
	 */
	@RequestMapping(value="/bat/batch/info/config", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody BatBatchInfoConfigVO inBatBatchInfoConfigVo) {		
		int outputVo = batBatchInfoConfigService.deleteByPk(inBatBatchInfoConfigVo);
		
		//若刪除批次成功，则在定时调度配置表中刪除一条数据
		if(outputVo == 1) {
			SSrvsCronConfVO paramVO = new SSrvsCronConfVO();
			paramVO.setJobCode(inBatBatchInfoConfigVo.getBatchId());
			sSrvsCronConfService.deleteByPk(paramVO);
		}
		
		return getResponseData(outputVo);
	}
	
	/**
	 * 根据主键更新信息
	 * @param inBatBatchInfoConfigVo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/bat/batch/info/config", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody BatBatchInfoConfigVO inBatBatchInfoConfigVo) throws Exception {		
		int outputVo = batBatchInfoConfigService.updateByPk(inBatBatchInfoConfigVo);
		
		if(outputVo == 1) {
			//不管发起前的发起方式是否“定时调度”，都在修改成功后先删除定时调度配置信息，然后在判断，若修改后
			//的发起方式是“定时调度”，则新增一条记录，若不是，则不再信息
			//定时作业编号
			SSrvsCronConfVO paramVO = new SSrvsCronConfVO();
			paramVO.setJobCode(inBatBatchInfoConfigVo.getBatchId());
			//先查询是否已经存在
			SSrvsCronConfVO confVO = sSrvsCronConfService.queryByPk(paramVO);
			
			int count = 1;
			//若存在，则先删除
			if(confVO != null) {
				count = sSrvsCronConfService.deleteByPk(paramVO);
				
				if(count != 1){
					ResponseData<Integer> data = getResponseData(count);
					data.setMessage("删除定时调度配置数据失败！操作影响了"+count+"条记录");
					return data;
				}
			}
			
			//若修改批次成功，且修改后发起方式为“定时调度”，则在定时调度配置表中新增一条数据
			if(count == 1 && inBatBatchInfoConfigVo.getLaunchType() != null && "1".equals(inBatBatchInfoConfigVo.getLaunchType())){
				//若删除成功，则再新增一条定时调度配置信息
				SSrvsCronConfVO addConfVO = new SSrvsCronConfVO();
				addConfVO.setJobCode(inBatBatchInfoConfigVo.getBatchId());
				addConfVO.setJobDesc(inBatBatchInfoConfigVo.getBatchName());
				addConfVO.setCronExpression(inBatBatchInfoConfigVo.getBatchCronValue());
				addConfVO.setAgentId(inBatBatchInfoConfigVo.getAgentId());
				addConfVO.setAgainTime(3);
				addConfVO.setRetrySecond(60);
				
				if(confVO != null) {
					addConfVO.setJobClassType(confVO.getJobClassType());
					addConfVO.setJobClass(confVO.getJobClass());
					addConfVO.setJobMethod(confVO.getJobMethod());
					addConfVO.setJobDesc(confVO.getJobDesc());
				}
				
				sSrvsCronConfService.insertSSrvsCronConf(addConfVO);
			}
		}
		
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inBatBatchInfoConfigVo
	 * @return
	 */
	@RequestMapping(value="/bat/batch/info/config", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertBatBatchInfoConfig(@RequestBody BatBatchInfoConfigVO inBatBatchInfoConfigVo) {
		int outputVo = batBatchInfoConfigService.insertBatBatchInfoConfig(inBatBatchInfoConfigVo);
		
		//若新增批次成功，且发起方式为“定时调度”，则在定时调度配置表中新增一条数据
		if(outputVo == 1 && inBatBatchInfoConfigVo.getLaunchType() != null && "1".equals(inBatBatchInfoConfigVo.getLaunchType())){
			//若删除成功，则再新增一条定时调度配置信息
			SSrvsCronConfVO addConfVO = new SSrvsCronConfVO();
			addConfVO.setJobCode(inBatBatchInfoConfigVo.getBatchId());
			addConfVO.setJobDesc(inBatBatchInfoConfigVo.getBatchName());
			addConfVO.setCronExpression(inBatBatchInfoConfigVo.getBatchCronValue());
			addConfVO.setAgentId(inBatBatchInfoConfigVo.getAgentId());
			addConfVO.setAgainTime(3);
			addConfVO.setRetrySecond(60);
			
			sSrvsCronConfService.insertSSrvsCronConf(addConfVO);
		}
		
		return getResponseData(outputVo);
	}
	
	/**
	 * 批次执行操作
	 * 
	 * @param agentId
	 * @return
	 */
	@RequestMapping(value = "/bat/batch/info/action/", method = RequestMethod.GET)
	public @ResponseBody ResponseData<Boolean> startBatch(@RequestParam("batchId") String batchId, @RequestParam("batchAction") String batchAction) {
		//批次查询
		BatBatchInfoConfigVO params = new BatBatchInfoConfigVO();
		params.setBatchId(batchId);
		BatBatchInfoConfigVO batchConf = batBatchInfoConfigService.queryByPk(params);
		
		//查询agent配置
		SAgentInfoVO inVo = new SAgentInfoVO();
		inVo.setAgentId(batchConf.getAgentId());
		SAgentInfoVO agentInfo = sAgentInfoService.queryByPk(inVo);
		
		//执行远程API调用
		ResponseData<Boolean> data = null;
		if (agentInfo != null && StringUtil.isNotEmpty(agentInfo.getAgentUrl())) {
			
			try {
				ResponseVO response = AgentApiUtil.actionBatch(agentInfo.getAgentUrl(), batchId, batchAction);
				if (response != null && StringUtil.isNotEmpty(response.getFlag())
						&& ResponseVO.SUCCESS.equals(response.getFlag())) {
					
					data = getResponseData(true);
					if (response != null && StringUtil.isNotEmpty(response.getMsg())) {
						data.setMessage(response.getMsg());
						logger.info(response.getMsg());
					} else {
						data.setMessage("批次执行成功！");
						logger.info("批次执行成功！agentId:" + agentInfo.getAgentId() + ", agent地址：" + agentInfo.getAgentUrl()
								+ ", 批次号：" + batchId + ", 批次动作：" + batchAction);
					}
				} else {
					data = getResponseData(false);
					if (response != null && StringUtil.isNotEmpty(response.getMsg())) {
						data.setMessage(response.getMsg());
						logger.info(response.getMsg());
					} else {
						data.setMessage("批次执行失败！");
						logger.info("批次执行失败！");
					}
				}

			} catch (Exception e) {
				logger.error("无法调用远程地址：" + agentInfo.getAgentUrl(), e);

				data = getResponseData(false);
				data.setMessage("无法调用远程地址：" + agentInfo.getAgentUrl());
			}

		} else {
			logger.error("没有找到配置信息");
			data = getResponseData(false);
			data.setMessage("没有找到配置信息！");
		}
			
		return data;
	}
	
}
