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

import com.irdstudio.smcenter.console.service.api.SSubsDatasourceService;
import com.irdstudio.smcenter.console.service.vo.SSubsDatasourceVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class SSubsDatasourceController extends AbstractController  {
	
	@Autowired
	@Qualifier("sSubsDatasourceService")
	private SSubsDatasourceService sSubsDatasourceService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/s/subs/datasources", method=RequestMethod.POST)
	public @ResponseBody ResponseData<List<SSubsDatasourceVO>> querySSubsDatasourceAll(
			SSubsDatasourceVO vo) {		
		List<SSubsDatasourceVO> outputVo = sSubsDatasourceService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/s/subs/datasource/{subsCode}/{subsDsCode}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<SSubsDatasourceVO> queryByPk(@PathVariable("subsCode") String subsCode,@PathVariable("subsDsCode") String subsDsCode) {		
		SSubsDatasourceVO inVo = new SSubsDatasourceVO();
				inVo.setSubsCode(subsCode);
				inVo.setSubsDsCode(subsDsCode);
		SSubsDatasourceVO outputVo = sSubsDatasourceService.queryByPk(inVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键删除信息
	 * @param sSubsDatasource
	 * @return
	 */
	@RequestMapping(value="/s/subs/datasource", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody SSubsDatasourceVO inSSubsDatasourceVo) {		
		int outputVo = sSubsDatasourceService.deleteByPk(inSSubsDatasourceVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inSSubsDatasourceVo
	 * @return
	 */
	@RequestMapping(value="/s/subs/datasource", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody SSubsDatasourceVO inSSubsDatasourceVo) {		
		int outputVo = sSubsDatasourceService.updateByPk(inSSubsDatasourceVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inSSubsDatasourceVo
	 * @return
	 */
	@RequestMapping(value="/s/subs/datasource", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertSSubsDatasource(@RequestBody SSubsDatasourceVO inSSubsDatasourceVo) {
		int outputVo = sSubsDatasourceService.insertSSubsDatasource(inSSubsDatasourceVo);
		return getResponseData(outputVo);
		
	}
}
