package com.irdstudio.smcenter.console.service.api;

import java.util.List;

import com.irdstudio.smcenter.console.service.vo.BatInstTaskTree;
import com.irdstudio.smcenter.console.service.vo.BatInstTaskVO;

/**
 * <p>Description:批次任务实例				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface BatInstTaskService {
	
	public List<BatInstTaskVO> queryAllOwner(BatInstTaskVO batInstTaskVo);
	
	public List<BatInstTaskVO> queryAllCurrOrg(BatInstTaskVO batInstTaskVo);
	
	public List<BatInstTaskVO> queryAllCurrDownOrg(BatInstTaskVO batInstTaskVo);
	
	public int insertBatInstTask(BatInstTaskVO inBatInstTaskVo);
	
	public int deleteByPk(BatInstTaskVO batInstTaskVo);
	
	public int updateByPk(BatInstTaskVO batInstTaskVo);
	
	public BatInstTaskVO queryByPk(BatInstTaskVO batInstTaskVo);
	
	/**
	 * 查询批次任务实例树
	 * @param batInstTaskVo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<BatInstTaskTree> queryBatInstTaskTree(BatInstTaskVO batInstTaskVo);
	
	/**
	 * 更新人工干预状态
	 * @param batInstTaskVo
	 * @return
	 */
	public int updateTaskInterveneState(BatInstTaskVO batInstTaskVo);
}
