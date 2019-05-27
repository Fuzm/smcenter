package com.irdstudio.ssm.admin.dao.domain;

import java.math.BigDecimal;

import com.irdstudio.ssm.framework.vo.BaseInfo;
/**
 * Description: 用户管理			
 * @author fuzm
 * @date 2018-08-08
 */
public class SUser extends BaseInfo{

	private static final long serialVersionUID = 1L;	
	
	/** 用户码 */
	private String actorno;
	/** 选项值 */
	private String enname;
	/** 用户名称 */
	private String actorname;
	/** 昵称 */
	private String nickname;
	/** 状态 */
	private String state;
	/** 密码 */
	private String password;
	/** 启用日期 */
	private String startdate;
	/** 密码 */
	private String passwvalda;
	/** 解雇日期 */
	private String firedate;
	/** 生日 */
	private String birthday;
	/** 联系电话 */
	private String telnum;
	/** 身份证号码 */
	private String idcardno;
	/** 允许操作的系统 */
	private String allowopersys;
	/** 最后登陆日期 */
	private String lastlogdat;
	/** 创建人 */
	private String creater;
	/** 创建时间 */
	private String creattime;
	/** 邮箱 */
	private String usermail;
	/** 密码输入错误次数 */
	private BigDecimal wrongpinnum;
	/** 是否管理员 */
	private String isadmin;
	/** 备注 */
	private String memo;
	/** 用户IP掩码 */
	private String ipmask;
	/** 排序字段 */
	private BigDecimal orderno;
	/** 用户防伪问题 */
	private String question;
	/** 用户防伪答案 */
	private String answer;
	/** 组织号 */
	private String orgid;
	/** 部门编号 */
	private String depno;
	/** 当前登录会话ID */
	private String sessionId;
	/** 职级 */
	private String rank;
	/** 主账号用户码 */
	private String parentactorno;
	/** 未知 */
	private String deviceNo;
	/** 未知 */
	private String menuConfig;
	/** 是否优先显示 */
	private String isPriorityShow;
	/** 是否合并待办 */
	private String ifTogetherWf;
	/** 操作模式 */
	private String opModel;
	/** 手机号码 */
	private String mobNum;
	

	public void setActorno(String actorno){
		this.actorno = actorno;
	}
	public String getActorno(){
		return this.actorno;
	}		
	public void setEnname(String enname){
		this.enname = enname;
	}
	public String getEnname(){
		return this.enname;
	}		
	public void setActorname(String actorname){
		this.actorname = actorname;
	}
	public String getActorname(){
		return this.actorname;
	}		
	public void setNickname(String nickname){
		this.nickname = nickname;
	}
	public String getNickname(){
		return this.nickname;
	}		
	public void setState(String state){
		this.state = state;
	}
	public String getState(){
		return this.state;
	}		
	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return this.password;
	}		
	public void setStartdate(String startdate){
		this.startdate = startdate;
	}
	public String getStartdate(){
		return this.startdate;
	}		
	public void setPasswvalda(String passwvalda){
		this.passwvalda = passwvalda;
	}
	public String getPasswvalda(){
		return this.passwvalda;
	}		
	public void setFiredate(String firedate){
		this.firedate = firedate;
	}
	public String getFiredate(){
		return this.firedate;
	}		
	public void setBirthday(String birthday){
		this.birthday = birthday;
	}
	public String getBirthday(){
		return this.birthday;
	}		
	public void setTelnum(String telnum){
		this.telnum = telnum;
	}
	public String getTelnum(){
		return this.telnum;
	}		
	public void setIdcardno(String idcardno){
		this.idcardno = idcardno;
	}
	public String getIdcardno(){
		return this.idcardno;
	}		
	public void setAllowopersys(String allowopersys){
		this.allowopersys = allowopersys;
	}
	public String getAllowopersys(){
		return this.allowopersys;
	}		
	public void setLastlogdat(String lastlogdat){
		this.lastlogdat = lastlogdat;
	}
	public String getLastlogdat(){
		return this.lastlogdat;
	}		
	public void setCreater(String creater){
		this.creater = creater;
	}
	public String getCreater(){
		return this.creater;
	}		
	public void setCreattime(String creattime){
		this.creattime = creattime;
	}
	public String getCreattime(){
		return this.creattime;
	}		
	public void setUsermail(String usermail){
		this.usermail = usermail;
	}
	public String getUsermail(){
		return this.usermail;
	}		
	public void setWrongpinnum(BigDecimal wrongpinnum){
		this.wrongpinnum = wrongpinnum;
	}
	public BigDecimal getWrongpinnum(){
		return this.wrongpinnum;
	}		
	public void setIsadmin(String isadmin){
		this.isadmin = isadmin;
	}
	public String getIsadmin(){
		return this.isadmin;
	}		
	public void setMemo(String memo){
		this.memo = memo;
	}
	public String getMemo(){
		return this.memo;
	}		
	public void setIpmask(String ipmask){
		this.ipmask = ipmask;
	}
	public String getIpmask(){
		return this.ipmask;
	}		
	public void setOrderno(BigDecimal orderno){
		this.orderno = orderno;
	}
	public BigDecimal getOrderno(){
		return this.orderno;
	}		
	public void setQuestion(String question){
		this.question = question;
	}
	public String getQuestion(){
		return this.question;
	}		
	public void setAnswer(String answer){
		this.answer = answer;
	}
	public String getAnswer(){
		return this.answer;
	}		
	public void setOrgid(String orgid){
		this.orgid = orgid;
	}
	public String getOrgid(){
		return this.orgid;
	}		
	public void setDepno(String depno){
		this.depno = depno;
	}
	public String getDepno(){
		return this.depno;
	}		
	public void setSessionId(String sessionId){
		this.sessionId = sessionId;
	}
	public String getSessionId(){
		return this.sessionId;
	}		
	public void setRank(String rank){
		this.rank = rank;
	}
	public String getRank(){
		return this.rank;
	}		
	public void setParentactorno(String parentactorno){
		this.parentactorno = parentactorno;
	}
	public String getParentactorno(){
		return this.parentactorno;
	}		
	public void setDeviceNo(String deviceNo){
		this.deviceNo = deviceNo;
	}
	public String getDeviceNo(){
		return this.deviceNo;
	}		
	public void setMenuConfig(String menuConfig){
		this.menuConfig = menuConfig;
	}
	public String getMenuConfig(){
		return this.menuConfig;
	}		
	public void setIsPriorityShow(String isPriorityShow){
		this.isPriorityShow = isPriorityShow;
	}
	public String getIsPriorityShow(){
		return this.isPriorityShow;
	}		
	public void setIfTogetherWf(String ifTogetherWf){
		this.ifTogetherWf = ifTogetherWf;
	}
	public String getIfTogetherWf(){
		return this.ifTogetherWf;
	}		
	public void setOpModel(String opModel){
		this.opModel = opModel;
	}
	public String getOpModel(){
		return this.opModel;
	}		
	public void setMobNum(String mobNum){
		this.mobNum = mobNum;
	}
	public String getMobNum(){
		return this.mobNum;
	}		

}
