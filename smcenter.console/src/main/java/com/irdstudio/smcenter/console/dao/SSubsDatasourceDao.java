package com.irdstudio.smcenter.console.dao;

import java.util.List;

import com.irdstudio.smcenter.console.dao.domain.SSubsDatasource;
import com.irdstudio.smcenter.console.service.vo.SSubsDatasourceVO;
/**
 * <p>DAO interface:子系统数据源信息表				<p>
 * @author ligm
 * @date 2018-06-13
 */
public interface SSubsDatasourceDao {
	
	public int insertSSubsDatasource(SSubsDatasource sSubsDatasource);
	
	public int deleteByPk(SSubsDatasource sSubsDatasource);
	
	public int updateByPk(SSubsDatasource sSubsDatasource);
	
	public SSubsDatasource queryByPk(SSubsDatasource sSubsDatasource);
	
	public List<SSubsDatasource> queryAllOwnerByPage(SSubsDatasourceVO sSubsDatasource);
	
	public List<SSubsDatasource> queryAllCurrOrgByPage(SSubsDatasourceVO sSubsDatasource);
	
	public List<SSubsDatasource> queryAllCurrDownOrgByPage(SSubsDatasourceVO sSubsDatasource);

}
