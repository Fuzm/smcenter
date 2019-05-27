package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.SSrvsCronConf;
import com.irdstudio.smcenter.console.service.vo.SSrvsCronConfVO;
/**
 * <p>DAO interface:定时调度配置				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface SSrvsCronConfDao {
	
	public int insertSSrvsCronConf(SSrvsCronConf sSrvsCronConf);
	
	public int deleteByPk(SSrvsCronConf sSrvsCronConf);
	
	public int updateByPk(SSrvsCronConf sSrvsCronConf);
	
	public SSrvsCronConf queryByPk(SSrvsCronConf sSrvsCronConf);
	
	public List<SSrvsCronConf> queryAllOwnerByPage(SSrvsCronConfVO sSrvsCronConf);
	
	public List<SSrvsCronConf> queryAllCurrOrgByPage(SSrvsCronConfVO sSrvsCronConf);
	
	public List<SSrvsCronConf> queryAllCurrDownOrgByPage(SSrvsCronConfVO sSrvsCronConf);

}
