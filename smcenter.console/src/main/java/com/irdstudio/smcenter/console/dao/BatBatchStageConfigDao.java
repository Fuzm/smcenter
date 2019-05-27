package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.BatBatchStageConfig;
import com.irdstudio.smcenter.console.service.vo.BatBatchStageConfigVO;
/**
 * <p>DAO interface:批次任务阶段				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface BatBatchStageConfigDao {
	
	public int insertBatBatchStageConfig(BatBatchStageConfig batBatchStageConfig);
	
	public int deleteByPk(BatBatchStageConfig batBatchStageConfig);
	
	public int updateByPk(BatBatchStageConfig batBatchStageConfig);
	
	public BatBatchStageConfig queryByPk(BatBatchStageConfig batBatchStageConfig);
	
	public List<BatBatchStageConfig> queryAllOwnerByPage(BatBatchStageConfigVO batBatchStageConfig);
	
	public List<BatBatchStageConfig> queryAllCurrOrgByPage(BatBatchStageConfigVO batBatchStageConfig);
	
	public List<BatBatchStageConfig> queryAllCurrDownOrgByPage(BatBatchStageConfigVO batBatchStageConfig);

}
