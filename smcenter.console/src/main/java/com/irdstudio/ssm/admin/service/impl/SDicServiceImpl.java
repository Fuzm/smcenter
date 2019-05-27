package com.irdstudio.ssm.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irdstudio.ssm.admin.dao.SDicDao;
import com.irdstudio.ssm.admin.dao.domain.SDic;
import com.irdstudio.ssm.admin.service.api.SDicService;
import com.irdstudio.ssm.admin.service.vo.SDicVO;
import com.irdstudio.ssm.framework.service.FrameworkService;


/**
 * <p>ServiceImpl: 通用字典				<p>
 * @author ligm
 * @date 2018-06-04
 */
@Service("sDicService")
public class SDicServiceImpl implements SDicService, FrameworkService {
	
	private static Logger logger = LoggerFactory.getLogger(SDicServiceImpl.class);

	@Autowired
	private SDicDao sDicDao;
	
	/**
	 * 新增操作
	 */
	public int insertSDic(SDicVO inSDicVo) {
		logger.debug("当前新增数据为:"+ inSDicVo.toString());
		int num = 0;
		try {
			SDic sDic = new SDic();
			beanCopy(inSDicVo, sDic);
			num = sDicDao.insertSDic(sDic);
		} catch (Exception e) {
			logger.error("新增数据发生异常!", e);
			num = -1;
		}
		logger.debug("当前新增数据条数为:"+ num);
		return num;
	}

	/**
	 * 删除操作
	 */
	public int deleteByPk(SDicVO inSDicVo) {
		logger.debug("当前删除数据条件为:"+ inSDicVo);
		int num = 0;
		try {
			SDic sDic = new SDic();
			beanCopy(inSDicVo, sDic);
			num = sDicDao.deleteByPk(sDic);
		} catch (Exception e) {
			logger.error("删除数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inSDicVo +"删除的数据条数为"+ num);
		return num;
	}


	/**
	 * 更新操作
	 */
	public int updateByPk(SDicVO inSDicVo) {
		logger.debug("当前修改数据为:"+ inSDicVo.toString());
		int num = 0;
		try {
			SDic sDic = new SDic();
			beanCopy(inSDicVo, sDic);
			num = sDicDao.updateByPk(sDic);
		} catch (Exception e) {
			logger.error("修改数据发生异常!", e);
			num = -1;
		}
		logger.debug("根据条件:"+ inSDicVo +"修改的数据条数为"+ num);
		return num;
	}
	
	/**
	 * 查询操作
	 */
	public SDicVO queryByPk(SDicVO inSDicVo) {
		
		logger.debug("当前查询参数信息为:"+ inSDicVo);
		try {
			SDic querySDic = new SDic();
			beanCopy(inSDicVo, querySDic);
			SDic queryRslSDic = sDicDao.queryByPk(querySDic);
			if (Objects.nonNull(queryRslSDic)) {
				SDicVO outSDicVo = beanCopy(queryRslSDic, new SDicVO());
				logger.debug("当前查询结果为:"+ outSDicVo.toString());
				return outSDicVo;
			} else {
				logger.debug("当前查询结果为空!");
			}
		} catch (Exception e) {
			logger.error("查询数据发生异常!", e);
		}
		return null;
	}


	/**
	 * 查询用户权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<SDicVO> queryAllOwner(SDicVO sDicVo) {

		logger.debug("当前查询本人所属数据信息的参数信息为:");
		List<SDicVO> list = null;
		try {
			List<SDic> sDics = sDicDao.queryAllOwnerByPage(sDicVo);
			logger.debug("当前查询本人所属数据信息的结果集数量为:"+ sDics.size());
			pageSet(sDics, sDicVo);
			list = (List<SDicVO>) beansCopy(sDics, SDicVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<SDicVO> queryAllCurrOrg(SDicVO sDicVo) {

		logger.debug("当前查询本人所属机构数据信息的参数信息为:");
		List<SDic> sDics = sDicDao.queryAllCurrOrgByPage(sDicVo);
		logger.debug("当前查询本人所属机构数据信息的结果集数量为:"+sDics.size());
		List<SDicVO> list = null;
		try {
			pageSet(sDics, sDicVo);
			list = (List<SDicVO>) beansCopy(sDics, SDicVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}


	/**
	 * 查询当前机构及下属机构权限数据
	 */
	@SuppressWarnings("unchecked")
	public List<SDicVO> queryAllCurrDownOrg(SDicVO sDicVo) {

		logger.debug("当前查询本人所属机构及以下数据信息的参数信息为:");
		List<SDic> sDics = sDicDao.queryAllCurrDownOrgByPage(sDicVo);
		logger.debug("当前查询本人所属机构及以下数据信息的结果集数量为:"+ sDics.size());
		List<SDicVO> list = null;
		try {
			pageSet(sDics, sDicVo);
			list = (List<SDicVO>) beansCopy(sDics, SDicVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}
		
		return list;
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SDicVO> queryAllDict() {
		logger.debug("查询所有的字典信息:");
		List<SDic> sDics = sDicDao.queryAllDict();
		logger.debug("字典总数为:"+ sDics.size());
		List<SDicVO> list = new ArrayList<SDicVO>();
		try {
			list = (List<SDicVO>) beansCopy(sDics, SDicVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}	
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SDicVO> queryDictOption(String opttype) {
		List<SDic> sDics = sDicDao.queryDictOption(opttype);
		logger.debug("字典选项数为:"+ sDics.size());
		List<SDicVO> list = new ArrayList<SDicVO>();
		try {
			list = (List<SDicVO>) beansCopy(sDics, SDicVO.class);
		} catch (Exception e) {
			logger.error("数据转换出现异常!", e);
		}	
		return list;
	}
}
