package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.BatInstBatchH;
import com.irdstudio.smcenter.console.service.vo.BatInstBatchHVO;
/**
 * <p>DAO interface:批次历史信息				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface BatInstBatchHDao {
	
	public int insertBatInstBatchH(BatInstBatchH batInstBatchH);
	
	public int deleteByPk(BatInstBatchH batInstBatchH);
	
	public int updateByPk(BatInstBatchH batInstBatchH);
	
	public BatInstBatchH queryByPk(BatInstBatchH batInstBatchH);
	
	public List<BatInstBatchH> queryAllOwnerByPage(BatInstBatchHVO batInstBatchH);
	
	public List<BatInstBatchH> queryAllCurrOrgByPage(BatInstBatchHVO batInstBatchH);
	
	public List<BatInstBatchH> queryAllCurrDownOrgByPage(BatInstBatchHVO batInstBatchH);

}
