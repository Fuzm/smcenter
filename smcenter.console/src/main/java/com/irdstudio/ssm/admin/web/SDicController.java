package com.irdstudio.ssm.admin.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.irdstudio.ssm.admin.service.api.SDicService;
import com.irdstudio.ssm.admin.service.vo.SDicVO;
import com.irdstudio.ssm.framework.constant.ResponseData;
import com.irdstudio.ssm.framework.web.AbstractController;

@RestController
@RequestMapping("/api")
public class SDicController extends AbstractController  {
	
	@Autowired
	@Qualifier("sDicService")
	private SDicService sDicService;

	
	/**
	 * 列表数据查询
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/s/dics", method = RequestMethod.POST)
	public @ResponseBody ResponseData<List<SDicVO>> querySDicAll(
			SDicVO vo) {		
		List<SDicVO> outputVo = sDicService.queryAllOwner(vo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键查询详情
	 * @return
	 */
	@RequestMapping(value="/s/dic/{enname}/{opttype}", method=RequestMethod.GET)
	public @ResponseBody ResponseData<SDicVO> queryByPk(@PathVariable("enname") String enname,@PathVariable("opttype") String opttype) {		
		SDicVO inVo = new SDicVO();
				inVo.setEnname(enname);
				inVo.setOpttype(opttype);
		SDicVO outputVo = sDicService.queryByPk(inVo);
		return getResponseData(outputVo);
	}
	
	/**
	 * 根据主键删除信息
	 * @param sDic
	 * @return
	 */
	@RequestMapping(value="/s/dic", method=RequestMethod.DELETE)
	public @ResponseBody ResponseData<Integer> deleteByPk(@RequestBody SDicVO inSDicVo) {		
		int outputVo = sDicService.deleteByPk(inSDicVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 根据主键更新信息
	 * @param inSDicVo
	 * @return
	 */
	@RequestMapping(value="/s/dic", method=RequestMethod.PUT)
	public @ResponseBody ResponseData<Integer> updateByPk(@RequestBody SDicVO inSDicVo) {		
		int outputVo = sDicService.updateByPk(inSDicVo);
		return getResponseData(outputVo);
		
	}
	
	/**
	 * 新增数据
	 * @param inSDicVo
	 * @return
	 */
	@RequestMapping(value="/s/dic", method=RequestMethod.POST)
	public @ResponseBody ResponseData<Integer> insertSDic(@RequestBody SDicVO inSDicVo) {
		int outputVo = sDicService.insertSDic(inSDicVo);
		return getResponseData(outputVo);		
	}
}
