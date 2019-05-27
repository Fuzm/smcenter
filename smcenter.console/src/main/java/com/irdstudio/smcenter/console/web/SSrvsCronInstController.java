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

import com.irdstudio.smcenter.console.service.api.SSrvsCronInstService;
import com.irdstudio.smcenter.console.service.vo.SSrvsCronInstVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class SSrvsCronInstController extends AbstractController  {
	
	@Autowired
	@Qualifier("sSrvsCronInstService")
	private SSrvsCronInstService sSrvsCronInstService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/s/srvs/cron/insts", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<SSrvsCronInstVO>> querySSrvsCronInstAll(
			SSrvsCronInstVO vo) {		
		List<SSrvsCronInstVO> outputVo = sSrvsCronInstService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/s/srvs/cron/inst/{jobCode}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<SSrvsCronInstVO> queryByPk(@PathVariable("jobCode") String jobCode) {		
		SSrvsCronInstVO inVo = new SSrvsCronInstVO();
				inVo.setJobCode(jobCode);
		SSrvsCronInstVO outputVo = sSrvsCronInstService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param sSrvsCronInst
	 * @return
	 */
	@RequestMapping(value="/s/srvs/cron/inst", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody SSrvsCronInstVO inSSrvsCronInstVo) {		
		int outputVo = sSrvsCronInstService.deleteByPk(inSSrvsCronInstVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inSSrvsCronInstVo
	 * @return
	 */
	@RequestMapping(value="/s/srvs/cron/inst", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody SSrvsCronInstVO inSSrvsCronInstVo) {		
		int outputVo = sSrvsCronInstService.updateByPk(inSSrvsCronInstVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inSSrvsCronInstVo
	 * @return
	 */
	@RequestMapping(value="/s/srvs/cron/inst", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertSSrvsCronInst(@RequestBody SSrvsCronInstVO inSSrvsCronInstVo) {
		int outputVo = sSrvsCronInstService.insertSSrvsCronInst(inSSrvsCronInstVo);
		return getResponseData(outputVo);
		
	}
}
