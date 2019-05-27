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

import com.irdstudio.smcenter.console.service.api.BatBatchStageConfigService;
import com.irdstudio.smcenter.console.service.vo.BatBatchStageConfigVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class BatBatchStageConfigController extends AbstractController  {
	
	@Autowired
	@Qualifier("stageConfigService")
	private BatBatchStageConfigService batBatchStageConfigService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/bat/batch/stage/configs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<BatBatchStageConfigVO>> queryBatBatchStageConfigAll(
			BatBatchStageConfigVO vo) {		
		List<BatBatchStageConfigVO> outputVo = batBatchStageConfigService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/bat/batch/stage/config/{stageId}/{batchId}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<BatBatchStageConfigVO> queryByPk(@PathVariable("stageId") String stageId,@PathVariable("batchId") String batchId) {		
		BatBatchStageConfigVO inVo = new BatBatchStageConfigVO();
				inVo.setStageId(stageId);
				inVo.setBatchId(batchId);
		BatBatchStageConfigVO outputVo = batBatchStageConfigService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param batBatchStageConfig
	 * @return
	 */
	@RequestMapping(value="/bat/batch/stage/config", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody BatBatchStageConfigVO inBatBatchStageConfigVo) {		
		int outputVo = batBatchStageConfigService.deleteByPk(inBatBatchStageConfigVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inBatBatchStageConfigVo
	 * @return
	 */
	@RequestMapping(value="/bat/batch/stage/config", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody BatBatchStageConfigVO inBatBatchStageConfigVo) {		
		int outputVo = batBatchStageConfigService.updateByPk(inBatBatchStageConfigVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inBatBatchStageConfigVo
	 * @return
	 */
	@RequestMapping(value="/bat/batch/stage/config", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertBatBatchStageConfig(@RequestBody BatBatchStageConfigVO inBatBatchStageConfigVo) {
		int outputVo = batBatchStageConfigService.insertBatBatchStageConfig(inBatBatchStageConfigVo);
		return getResponseData(outputVo);
		
	}
}
