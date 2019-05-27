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

import com.irdstudio.smcenter.console.service.api.BatTaskLocaleConfigService;
import com.irdstudio.smcenter.console.service.vo.BatTaskLocaleConfigVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class BatTaskLocaleConfigController extends AbstractController  {
	
	@Autowired
	@Qualifier("localeConfigService")
	private BatTaskLocaleConfigService batTaskLocaleConfigService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/bat/task/locale/configs", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<BatTaskLocaleConfigVO>> queryBatTaskLocaleConfigAll(
			BatTaskLocaleConfigVO vo) {		
		List<BatTaskLocaleConfigVO> outputVo = batTaskLocaleConfigService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/bat/task/locale/config/{localeId}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<BatTaskLocaleConfigVO> queryByPk(@PathVariable("localeId") String localeId) {		
		BatTaskLocaleConfigVO inVo = new BatTaskLocaleConfigVO();
				inVo.setLocaleId(localeId);
		BatTaskLocaleConfigVO outputVo = batTaskLocaleConfigService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param batTaskLocaleConfig
	 * @return
	 */
	@RequestMapping(value="/bat/task/locale/config", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody BatTaskLocaleConfigVO inBatTaskLocaleConfigVo) {		
		int outputVo = batTaskLocaleConfigService.deleteByPk(inBatTaskLocaleConfigVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inBatTaskLocaleConfigVo
	 * @return
	 */
	@RequestMapping(value="/bat/task/locale/config", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody BatTaskLocaleConfigVO inBatTaskLocaleConfigVo) {		
		int outputVo = batTaskLocaleConfigService.updateByPk(inBatTaskLocaleConfigVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inBatTaskLocaleConfigVo
	 * @return
	 */
	@RequestMapping(value="/bat/task/locale/config", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertBatTaskLocaleConfig(@RequestBody BatTaskLocaleConfigVO inBatTaskLocaleConfigVo) {
		int outputVo = batTaskLocaleConfigService.insertBatTaskLocaleConfig(inBatTaskLocaleConfigVo);
		return getResponseData(outputVo);
		
	}
}
