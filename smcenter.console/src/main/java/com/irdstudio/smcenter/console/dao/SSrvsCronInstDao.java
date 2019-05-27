package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.SSrvsCronInst;
import com.irdstudio.smcenter.console.service.vo.SSrvsCronInstVO;
/**
 * <p>DAO interface:定时调度实例				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface SSrvsCronInstDao {
	
	public int insertSSrvsCronInst(SSrvsCronInst sSrvsCronInst);
	
	public int deleteByPk(SSrvsCronInst sSrvsCronInst);
	
	public int updateByPk(SSrvsCronInst sSrvsCronInst);
	
	public SSrvsCronInst queryByPk(SSrvsCronInst sSrvsCronInst);
	
	public List<SSrvsCronInst> queryAllOwnerByPage(SSrvsCronInstVO sSrvsCronInst);
	
	public List<SSrvsCronInst> queryAllCurrOrgByPage(SSrvsCronInstVO sSrvsCronInst);
	
	public List<SSrvsCronInst> queryAllCurrDownOrgByPage(SSrvsCronInstVO sSrvsCronInst);

}
