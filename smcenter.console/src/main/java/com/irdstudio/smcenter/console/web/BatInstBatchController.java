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

import com.irdstudio.smcenter.console.service.api.BatInstBatchService;
import com.irdstudio.smcenter.console.service.vo.BatInstBatchVO;
import com.irdstudio.ssm.framework.constant.BaseConstance;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class BatInstBatchController extends AbstractController  {
	
	@Autowired
	@Qualifier("instBatchService")
	private BatInstBatchService batInstBatchService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/bat/inst/batchs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<BatInstBatchVO>> queryBatInstBatchAll(
			BatInstBatchVO vo) {		
		List<BatInstBatchVO> outputVo = batInstBatchService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/bat/inst/batch/{batchId}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<BatInstBatchVO> queryByPk(@PathVariable("batchId") String batchId) {		
		BatInstBatchVO inVo = new BatInstBatchVO();
				inVo.setBatchId(batchId);
		BatInstBatchVO outputVo = batInstBatchService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param batInstBatch
	 * @return
	 */
	@RequestMapping(value="/bat/inst/batch", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody BatInstBatchVO inBatInstBatchVo) {		
		int outputVo = batInstBatchService.deleteByPk(inBatInstBatchVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inBatInstBatchVo
	 * @return
	 */
	@RequestMapping(value="/bat/inst/batch", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody BatInstBatchVO inBatInstBatchVo) {		
		int outputVo = batInstBatchService.updateByPk(inBatInstBatchVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inBatInstBatchVo
	 * @return
	 */
	@RequestMapping(value="/bat/inst/batch", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertBatInstBatch(@RequestBody BatInstBatchVO inBatInstBatchVo) {
		int outputVo = batInstBatchService.insertBatInstBatch(inBatInstBatchVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 暂停批次
	 * @param inBatInstBatchVo
	 * @return
	 */
	@RequestMapping(value="/bat/inst/stop/{batchId}", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> stopBatInstBatch(@PathVariable("batchId") String batchId) {
		BatInstBatchVO batInstBatchVo = new BatInstBatchVO();
		batInstBatchVo.setBatchId(batchId);
		batInstBatchVo.setBatchInterveneState(BaseConstance.BATCH_INT_STATE_STOP);
		int outputVo = batInstBatchService.deleteByPk(batInstBatchVo);
		return getResponseData(outputVo);
	}
	
	/**
	 * 续跑批次
	 * @param inBatInstBatchVo
	 * @return
	 */
	@RequestMapping(value="/bat/inst/continue/{batchId}", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> continueBatInstBatch(@PathVariable("batchId") String batchId) {
		BatInstBatchVO batInstBatchVo = new BatInstBatchVO();
		batInstBatchVo.setBatchId(batchId);
		batInstBatchVo.setBatchInterveneState(BaseConstance.BATCH_INT_STATE_RUN);
		int outputVo = batInstBatchService.deleteByPk(batInstBatchVo);
		return getResponseData(outputVo);
	}
	
	/**
	 * 中断批次
	 * @param inBatInstBatchVo
	 * @return
	 */
	@RequestMapping(value="/bat/inst/end/{batchId}", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> endBatInstBatch(@PathVariable("batchId") String batchId) {
		BatInstBatchVO batInstBatchVo = new BatInstBatchVO();
		batInstBatchVo.setBatchId(batchId);
		batInstBatchVo.setBatchInterveneState(BaseConstance.BATCH_INT_STATE_END);
		int outputVo = batInstBatchService.deleteByPk(batInstBatchVo);
		return getResponseData(outputVo);
	}
	
}
