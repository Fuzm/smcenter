package com.irdstudio.ssm.admin.service.vo;

import java.util.List;

import com.irdstudio.ssm.framework.vo.BaseInfo;

/**
 * <p>Description: 系统字典项表				<p>
 * @author fuzm
 * @date 2018-05-03
 */
public class SDicListVO extends BaseInfo {

	private static final long serialVersionUID = 1L;	

	private List<SDicVO> sDicVOs;

	/** 字典项类型 */
	private String opttype;


	public List<SDicVO> getsDicVOs() {
		return sDicVOs;
	}

	public void setsDicVOs(List<SDicVO> sDicVOs) {
		this.sDicVOs = sDicVOs;
	}

	public String getOpttype() {
		return opttype;
	}

	public void setOpttype(String opttype) {
		this.opttype = opttype;
	}
}
