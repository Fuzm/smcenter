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

import com.irdstudio.smcenter.console.service.api.BatInstBatchHService;
import com.irdstudio.smcenter.console.service.vo.BatInstBatchHVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class BatInstBatchHController extends AbstractController  {
	
	@Autowired
	@Qualifier("instBatchHService")
	private BatInstBatchHService batInstBatchHService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/bat/inst/batch/hs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<BatInstBatchHVO>> queryBatInstBatchHAll(
			BatInstBatchHVO vo) {		
		List<BatInstBatchHVO> outputVo = batInstBatchHService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/bat/inst/batch/h/{batchSerialNo}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<BatInstBatchHVO> queryByPk(@PathVariable("batchSerialNo") String batchSerialNo) {		
		BatInstBatchHVO inVo = new BatInstBatchHVO();
				inVo.setBatchSerialNo(batchSerialNo);
		BatInstBatchHVO outputVo = batInstBatchHService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param batInstBatchH
	 * @return
	 */
	@RequestMapping(value="/bat/inst/batch/h", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody BatInstBatchHVO inBatInstBatchHVo) {		
		int outputVo = batInstBatchHService.deleteByPk(inBatInstBatchHVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inBatInstBatchHVo
	 * @return
	 */
	@RequestMapping(value="/bat/inst/batch/h", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody BatInstBatchHVO inBatInstBatchHVo) {		
		int outputVo = batInstBatchHService.updateByPk(inBatInstBatchHVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inBatInstBatchHVo
	 * @return
	 */
	@RequestMapping(value="/bat/inst/batch/h", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertBatInstBatchH(@RequestBody BatInstBatchHVO inBatInstBatchHVo) {
		int outputVo = batInstBatchHService.insertBatInstBatchH(inBatInstBatchHVo);
		return getResponseData(outputVo);
		
	}
}
