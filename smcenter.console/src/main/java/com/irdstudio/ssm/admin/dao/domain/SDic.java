package com.irdstudio.ssm.admin.dao.domain;

import java.math.BigDecimal;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 通用字典			
 * @author ligm
 * @date 2018-06-04
 */
public class SDic extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 选项值 */
	private String enname;
	/** 选项名称 */
	private String cnname;
	/** 选项类别 */
	private String opttype;
	/** 选项描述 */
	private String memo;
	/** 标志 */
	private String flag;
	/** 级别 */
	private String levels;
	/** 排序字段 */
	private BigDecimal orderid;
	/** 修改日期 */
	private String modifyDate;
	

	public void setEnname(String enname){
		this.enname = enname;
	}
	public String getEnname(){
		return this.enname;
	}		
	public void setCnname(String cnname){
		this.cnname = cnname;
	}
	public String getCnname(){
		return this.cnname;
	}		
	public void setOpttype(String opttype){
		this.opttype = opttype;
	}
	public String getOpttype(){
		return this.opttype;
	}		
	public void setMemo(String memo){
		this.memo = memo;
	}
	public String getMemo(){
		return this.memo;
	}		
	public void setFlag(String flag){
		this.flag = flag;
	}
	public String getFlag(){
		return this.flag;
	}		
	public void setLevels(String levels){
		this.levels = levels;
	}
	public String getLevels(){
		return this.levels;
	}		
	public void setOrderid(BigDecimal orderid){
		this.orderid = orderid;
	}
	public BigDecimal getOrderid(){
		return this.orderid;
	}		
	public void setModifyDate(String modifyDate){
		this.modifyDate = modifyDate;
	}
	public String getModifyDate(){
		return this.modifyDate;
	}		

}
