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

import com.irdstudio.smcenter.console.service.api.SParamInfoService;
import com.irdstudio.smcenter.console.service.vo.SParamInfoVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class SParamInfoController extends AbstractController  {
	
	@Autowired
	@Qualifier("sParamInfoService")
	private SParamInfoService sParamInfoService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/s/param/infos", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<SParamInfoVO>> querySParamInfoAll(
			SParamInfoVO vo) {		
		List<SParamInfoVO> outputVo = sParamInfoService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/s/param/info/{paramCode}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<SParamInfoVO> queryByPk(@PathVariable("paramCode") String paramCode) {		
		SParamInfoVO inVo = new SParamInfoVO();
				inVo.setParamCode(paramCode);
		SParamInfoVO outputVo = sParamInfoService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param sParamInfo
	 * @return
	 */
	@RequestMapping(value="/s/param/info", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody SParamInfoVO inSParamInfoVo) {		
		int outputVo = sParamInfoService.deleteByPk(inSParamInfoVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inSParamInfoVo
	 * @return
	 */
	@RequestMapping(value="/s/param/info", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody SParamInfoVO inSParamInfoVo) {		
		int outputVo = sParamInfoService.updateByPk(inSParamInfoVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inSParamInfoVo
	 * @return
	 */
	@RequestMapping(value="/s/param/info", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertSParamInfo(@RequestBody SParamInfoVO inSParamInfoVo) {
		int outputVo = sParamInfoService.insertSParamInfo(inSParamInfoVo);
		return getResponseData(outputVo);
		
	}
}
