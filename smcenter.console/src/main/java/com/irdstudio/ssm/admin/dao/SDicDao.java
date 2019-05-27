package com.irdstudio.ssm.admin.dao;

import java.util.List;

import com.irdstudio.ssm.admin.dao.domain.SDic;
import com.irdstudio.ssm.admin.service.vo.SDicVO;
/**
 * <p>DAO interface:通用字典				<p>
 * @author ligm
 * @date 2018-06-04
 */
public interface SDicDao {
	
	public int insertSDic(SDic sDic);
	
	public int deleteByPk(SDic sDic);
	
	public int updateByPk(SDic sDic);
	
	public SDic queryByPk(SDic sDic);
	
	public List<SDic> queryAllOwnerByPage(SDicVO sDic);
	
	public List<SDic> queryAllCurrOrgByPage(SDicVO sDic);
	
	public List<SDic> queryAllCurrDownOrgByPage(SDicVO sDic);

	public List<SDic> queryAllDict();

	public List<SDic> queryDictOption(String opttype);

}
