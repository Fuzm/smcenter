package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.BatBatchInfoConfig;
import com.irdstudio.smcenter.console.service.vo.BatBatchInfoConfigVO;
/**
 * <p>DAO interface:批次信息配置				<p>
 * @author ligm
 * @date 2018-06-14
 */
public interface BatBatchInfoConfigDao {
	
	public int insertBatBatchInfoConfig(BatBatchInfoConfig batBatchInfoConfig);
	
	public int deleteByPk(BatBatchInfoConfig batBatchInfoConfig);
	
	public int updateByPk(BatBatchInfoConfig batBatchInfoConfig);
	
	public BatBatchInfoConfig queryByPk(BatBatchInfoConfig batBatchInfoConfig);
	
	public List<BatBatchInfoConfig> queryAllOwnerByPage(BatBatchInfoConfigVO batBatchInfoConfig);
	
	public List<BatBatchInfoConfig> queryAllCurrOrgByPage(BatBatchInfoConfigVO batBatchInfoConfig);
	
	public List<BatBatchInfoConfig> queryAllCurrDownOrgByPage(BatBatchInfoConfigVO batBatchInfoConfig);

}
