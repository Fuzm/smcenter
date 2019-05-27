package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.BatInstTask;
import com.irdstudio.smcenter.console.service.vo.BatInstTaskVO;
/**
 * <p>DAO interface:批次任务实例				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface BatInstTaskDao {
	
	public int insertBatInstTask(BatInstTask batInstTask);
	
	public int deleteByPk(BatInstTask batInstTask);
	
	public int updateByPk(BatInstTask batInstTask);
	
	public BatInstTask queryByPk(BatInstTask batInstTask);
	
	public List<BatInstTask> queryAllOwnerByPage(BatInstTaskVO batInstTask);
	
	public List<BatInstTask> queryAllCurrOrgByPage(BatInstTaskVO batInstTask);
	
	public List<BatInstTask> queryAllCurrDownOrgByPage(BatInstTaskVO batInstTask);

	public int updateTaskInterveneState(BatInstTask batInstTask);
	
	public List<BatInstTask> queryAllInstTask(BatInstTaskVO batInstTask);
}
