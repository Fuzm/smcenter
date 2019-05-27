package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.SSubsDatasourceVO;

/**
 * <p>Description:子系统数据源信息表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface SSubsDatasourceService {
	
	public List<SSubsDatasourceVO> queryAllOwner(SSubsDatasourceVO sSubsDatasourceVo);
	
	public List<SSubsDatasourceVO> queryAllCurrOrg(SSubsDatasourceVO sSubsDatasourceVo);
	
	public List<SSubsDatasourceVO> queryAllCurrDownOrg(SSubsDatasourceVO sSubsDatasourceVo);
	
	public int insertSSubsDatasource(SSubsDatasourceVO inSSubsDatasourceVo);
	
	public int deleteByPk(SSubsDatasourceVO sSubsDatasourceVo);
	
	public int updateByPk(SSubsDatasourceVO sSubsDatasourceVo);
	
	public SSubsDatasourceVO queryByPk(SSubsDatasourceVO sSubsDatasourceVo);

}
