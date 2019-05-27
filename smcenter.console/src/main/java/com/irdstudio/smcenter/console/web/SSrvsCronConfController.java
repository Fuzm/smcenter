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

import com.irdstudio.smcenter.console.service.api.SSrvsCronConfService;
import com.irdstudio.smcenter.console.service.vo.SSrvsCronConfVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class SSrvsCronConfController extends AbstractController  {
	
	@Autowired
	@Qualifier("sSrvsCronConfService")
	private SSrvsCronConfService sSrvsCronConfService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/s/srvs/cron/confs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<SSrvsCronConfVO>> querySSrvsCronConfAll(
			SSrvsCronConfVO vo) {		
		List<SSrvsCronConfVO> outputVo = sSrvsCronConfService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/s/srvs/cron/conf/{jobCode}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<SSrvsCronConfVO> queryByPk(@PathVariable("jobCode") String jobCode) {		
		SSrvsCronConfVO inVo = new SSrvsCronConfVO();
				inVo.setJobCode(jobCode);
		SSrvsCronConfVO outputVo = sSrvsCronConfService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param sSrvsCronConf
	 * @return
	 */
	@RequestMapping(value="/s/srvs/cron/conf", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody SSrvsCronConfVO inSSrvsCronConfVo) {		
		int outputVo = sSrvsCronConfService.deleteByPk(inSSrvsCronConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inSSrvsCronConfVo
	 * @return
	 */
	@RequestMapping(value="/s/srvs/cron/conf", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody SSrvsCronConfVO inSSrvsCronConfVo) {		
		int outputVo = sSrvsCronConfService.updateByPk(inSSrvsCronConfVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inSSrvsCronConfVo
	 * @return
	 */
	@RequestMapping(value="/s/srvs/cron/conf", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertSSrvsCronConf(@RequestBody SSrvsCronConfVO inSSrvsCronConfVo) {
		int outputVo = sSrvsCronConfService.insertSSrvsCronConf(inSSrvsCronConfVo);
		return getResponseData(outputVo);
		
	}
}
