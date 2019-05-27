package com.irdstudio.ssm.admin.service.api;

import java.util.List;

import com.irdstudio.ssm.admin.service.vo.SDicVO;

/**
 * <p>Description:通用字典				<p>
 * @author ligm
 * @date 2018-06-04
 */
public interface SDicService {
	
	public List<SDicVO> queryAllOwner(SDicVO sDicVo);
	
	public List<SDicVO> queryAllCurrOrg(SDicVO sDicVo);
	
	public List<SDicVO> queryAllCurrDownOrg(SDicVO sDicVo);
	
	public int insertSDic(SDicVO inSDicVo);
	
	public int deleteByPk(SDicVO sDicVo);
	
	public int updateByPk(SDicVO sDicVo);
	
	public SDicVO queryByPk(SDicVO sDicVo);
	
	public List<SDicVO> queryAllDict();
	
	public List<SDicVO> queryDictOption(String opttype);
	
	

}
