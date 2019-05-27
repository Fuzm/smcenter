package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.BatInstBatch;
import com.irdstudio.smcenter.console.service.vo.BatInstBatchVO;
/**
 * <p>DAO interface:批次实例信息				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface BatInstBatchDao {
	
	public int insertBatInstBatch(BatInstBatch batInstBatch);
	
	public int deleteByPk(BatInstBatch batInstBatch);
	
	public int updateByPk(BatInstBatch batInstBatch);
	
	public BatInstBatch queryByPk(BatInstBatch batInstBatch);
	
	public List<BatInstBatch> queryAllOwnerByPage(BatInstBatchVO batInstBatch);
	
	public List<BatInstBatch> queryAllCurrOrgByPage(BatInstBatchVO batInstBatch);
	
	public List<BatInstBatch> queryAllCurrDownOrgByPage(BatInstBatchVO batInstBatch);
	
	public int updateBatInstBatchStatus(BatInstBatch batInstBatch);

}
