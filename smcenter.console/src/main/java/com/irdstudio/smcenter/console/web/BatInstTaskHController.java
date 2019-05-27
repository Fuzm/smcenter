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

import com.irdstudio.smcenter.console.service.api.BatInstTaskHService;
import com.irdstudio.smcenter.console.service.vo.BatInstTaskHVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class BatInstTaskHController extends AbstractController  {
	
	@Autowired
	@Qualifier("instTaskHService")
	private BatInstTaskHService batInstTaskHService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/bat/inst/task/hs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<BatInstTaskHVO>> queryBatInstTaskHAll(
			BatInstTaskHVO vo) {		
		List<BatInstTaskHVO> outputVo = batInstTaskHService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/bat/inst/task/h/{taskId}/{batchSerialNo}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<BatInstTaskHVO> queryByPk(@PathVariable("taskId") String taskId,@PathVariable("batchSerialNo") String batchSerialNo) {		
		BatInstTaskHVO inVo = new BatInstTaskHVO();
				inVo.setTaskId(taskId);
				inVo.setBatchSerialNo(batchSerialNo);
		BatInstTaskHVO outputVo = batInstTaskHService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param batInstTaskH
	 * @return
	 */
	@RequestMapping(value="/bat/inst/task/h", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody BatInstTaskHVO inBatInstTaskHVo) {		
		int outputVo = batInstTaskHService.deleteByPk(inBatInstTaskHVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inBatInstTaskHVo
	 * @return
	 */
	@RequestMapping(value="/bat/inst/task/h", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody BatInstTaskHVO inBatInstTaskHVo) {		
		int outputVo = batInstTaskHService.updateByPk(inBatInstTaskHVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inBatInstTaskHVo
	 * @return
	 */
	@RequestMapping(value="/bat/inst/task/h", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertBatInstTaskH(@RequestBody BatInstTaskHVO inBatInstTaskHVo) {
		int outputVo = batInstTaskHService.insertBatInstTaskH(inBatInstTaskHVo);
		return getResponseData(outputVo);
		
	}
}
