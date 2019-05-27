package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.SSubsInfo;
import com.irdstudio.smcenter.console.service.vo.SSubsInfoVO;
/**
 * <p>DAO interface:子系统基础信息表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface SSubsInfoDao {
	
	public int insertSSubsInfo(SSubsInfo sSubsInfo);
	
	public int deleteByPk(SSubsInfo sSubsInfo);
	
	public int updateByPk(SSubsInfo sSubsInfo);
	
	public SSubsInfo queryByPk(SSubsInfo sSubsInfo);
	
	public List<SSubsInfo> queryAllOwnerByPage(SSubsInfoVO sSubsInfo);
	
	public List<SSubsInfo> queryAllCurrOrgByPage(SSubsInfoVO sSubsInfo);
	
	public List<SSubsInfo> queryAllCurrDownOrgByPage(SSubsInfoVO sSubsInfo);

}
