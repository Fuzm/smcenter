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

import com.irdstudio.smcenter.console.service.api.SSrvsCronHisService;
import com.irdstudio.smcenter.console.service.vo.SSrvsCronHisVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class SSrvsCronHisController extends AbstractController  {
	
	@Autowired
	@Qualifier("sSrvsCronHisService")
	private SSrvsCronHisService sSrvsCronHisService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/s/srvs/cron/hiss", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<SSrvsCronHisVO>> querySSrvsCronHisAll(
			SSrvsCronHisVO vo) {		
		List<SSrvsCronHisVO> outputVo = sSrvsCronHisService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/s/srvs/cron/his/{recordId}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<SSrvsCronHisVO> queryByPk(@PathVariable("recordId") String recordId) {		
		SSrvsCronHisVO inVo = new SSrvsCronHisVO();
				inVo.setRecordId(recordId);
		SSrvsCronHisVO outputVo = sSrvsCronHisService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param sSrvsCronHis
	 * @return
	 */
	@RequestMapping(value="/s/srvs/cron/his", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody SSrvsCronHisVO inSSrvsCronHisVo) {		
		int outputVo = sSrvsCronHisService.deleteByPk(inSSrvsCronHisVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inSSrvsCronHisVo
	 * @return
	 */
	@RequestMapping(value="/s/srvs/cron/his", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody SSrvsCronHisVO inSSrvsCronHisVo) {		
		int outputVo = sSrvsCronHisService.updateByPk(inSSrvsCronHisVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inSSrvsCronHisVo
	 * @return
	 */
	@RequestMapping(value="/s/srvs/cron/his", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertSSrvsCronHis(@RequestBody SSrvsCronHisVO inSSrvsCronHisVo) {
		int outputVo = sSrvsCronHisService.insertSSrvsCronHis(inSSrvsCronHisVo);
		return getResponseData(outputVo);
		
	}
}
