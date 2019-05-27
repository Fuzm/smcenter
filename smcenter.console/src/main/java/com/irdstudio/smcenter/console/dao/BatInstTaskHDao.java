package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.BatInstTaskH;
import com.irdstudio.smcenter.console.service.vo.BatInstTaskHVO;
/**
 * <p>DAO interface:批次任务历史				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface BatInstTaskHDao {
	
	public int insertBatInstTaskH(BatInstTaskH batInstTaskH);
	
	public int deleteByPk(BatInstTaskH batInstTaskH);
	
	public int updateByPk(BatInstTaskH batInstTaskH);
	
	public BatInstTaskH queryByPk(BatInstTaskH batInstTaskH);
	
	public List<BatInstTaskH> queryAllOwnerByPage(BatInstTaskHVO batInstTaskH);
	
	public List<BatInstTaskH> queryAllCurrOrgByPage(BatInstTaskHVO batInstTaskH);
	
	public List<BatInstTaskH> queryAllCurrDownOrgByPage(BatInstTaskHVO batInstTaskH);

}
