package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.BatTaskUnitConfig;
import com.irdstudio.smcenter.console.service.vo.BatTaskUnitConfigVO;
/**
 * <p>DAO interface:批次任务配置				<p>
 * @author ligm
 * @date 2018-06-14
 */
public interface BatTaskUnitConfigDao {
	
	public int insertBatTaskUnitConfig(BatTaskUnitConfig batTaskUnitConfig);
	
	public int deleteByPk(BatTaskUnitConfig batTaskUnitConfig);
	
	public int updateByPk(BatTaskUnitConfig batTaskUnitConfig);
	
	public BatTaskUnitConfig queryByPk(BatTaskUnitConfig batTaskUnitConfig);
	
	public List<BatTaskUnitConfig> queryAllOwnerByPage(BatTaskUnitConfigVO batTaskUnitConfig);
	
	public List<BatTaskUnitConfig> queryAllCurrOrgByPage(BatTaskUnitConfigVO batTaskUnitConfig);
	
	public List<BatTaskUnitConfig> queryAllCurrDownOrgByPage(BatTaskUnitConfigVO batTaskUnitConfig);

}
