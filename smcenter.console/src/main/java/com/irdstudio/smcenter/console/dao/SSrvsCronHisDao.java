package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.SSrvsCronHis;
import com.irdstudio.smcenter.console.service.vo.SSrvsCronHisVO;
/**
 * <p>DAO interface:定时调度历史				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface SSrvsCronHisDao {
	
	public int insertSSrvsCronHis(SSrvsCronHis sSrvsCronHis);
	
	public int deleteByPk(SSrvsCronHis sSrvsCronHis);
	
	public int updateByPk(SSrvsCronHis sSrvsCronHis);
	
	public SSrvsCronHis queryByPk(SSrvsCronHis sSrvsCronHis);
	
	public List<SSrvsCronHis> queryAllOwnerByPage(SSrvsCronHisVO sSrvsCronHis);
	
	public List<SSrvsCronHis> queryAllCurrOrgByPage(SSrvsCronHisVO sSrvsCronHis);
	
	public List<SSrvsCronHis> queryAllCurrDownOrgByPage(SSrvsCronHisVO sSrvsCronHis);

}
