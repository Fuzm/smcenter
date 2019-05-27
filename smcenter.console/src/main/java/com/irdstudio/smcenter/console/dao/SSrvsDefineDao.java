package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.SSrvsDefine;
import com.irdstudio.smcenter.console.service.vo.SSrvsDefineVO;
/**
 * <p>DAO interface:01.服务组件信息				<p>
 * @author ligm
 * @date 2018-06-09
 */
public interface SSrvsDefineDao {
	
	public int insertSSrvsDefine(SSrvsDefine sSrvsDefine);
	
	public int deleteByPk(SSrvsDefine sSrvsDefine);
	
	public int updateByPk(SSrvsDefine sSrvsDefine);
	
	public SSrvsDefine queryByPk(SSrvsDefine sSrvsDefine);
	
	public List<SSrvsDefine> queryAllOwnerByPage(SSrvsDefineVO sSrvsDefine);
	
	public List<SSrvsDefine> queryAllCurrOrgByPage(SSrvsDefineVO sSrvsDefine);
	
	public List<SSrvsDefine> queryAllCurrDownOrgByPage(SSrvsDefineVO sSrvsDefine);

}
