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

import com.irdstudio.smcenter.console.service.api.SSubsInfoService;
import com.irdstudio.smcenter.console.service.vo.SSubsInfoVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class SSubsInfoController extends AbstractController  {
	
	@Autowired
	@Qualifier("sSubsInfoService")
	private SSubsInfoService sSubsInfoService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/s/subs/infos", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<SSubsInfoVO>> querySSubsInfoAll(
			SSubsInfoVO vo) {		
		List<SSubsInfoVO> outputVo = sSubsInfoService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/s/subs/info/{subsCode}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<SSubsInfoVO> queryByPk(@PathVariable("subsCode") String subsCode) {		
		SSubsInfoVO inVo = new SSubsInfoVO();
				inVo.setSubsCode(subsCode);
		SSubsInfoVO outputVo = sSubsInfoService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param sSubsInfo
	 * @return
	 */
	@RequestMapping(value="/s/subs/info", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody SSubsInfoVO inSSubsInfoVo) {		
		int outputVo = sSubsInfoService.deleteByPk(inSSubsInfoVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inSSubsInfoVo
	 * @return
	 */
	@RequestMapping(value="/s/subs/info", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody SSubsInfoVO inSSubsInfoVo) {		
		int outputVo = sSubsInfoService.updateByPk(inSSubsInfoVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inSSubsInfoVo
	 * @return
	 */
	@RequestMapping(value="/s/subs/info", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertSSubsInfo(@RequestBody SSubsInfoVO inSSubsInfoVo) {
		int outputVo = sSubsInfoService.insertSSubsInfo(inSSubsInfoVo);
		return getResponseData(outputVo);
		
	}
}
