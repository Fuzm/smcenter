package com.irdstudio.ssm.framework.constant;

/**
 * 批次文件生成文件配置类
 * @author lyp
 */
public enum TemplatePathConstant {
	LMT_ACCOUNT_INFO("lmtaccount.template"), //授信台账信息
	PERSON_CUS_INFO("personcus.template"), //对私客户信息
	CONTRACT_INFO("loancontract.template"), //合同信息 
	LOAN_ACCOUNT_INFO("loanaccount.template"), //贷款信息
    ;
    private String path;

    private TemplatePathConstant(String path) {
        this.path = path;
    }
    
    /**
     * 校验文件是否存在
     * @param path
     * @return
     */
    public static TemplatePathConstant parse(String path){
        for(TemplatePathConstant constant : TemplatePathConstant.values()){
            if(constant.path.equals(path)){
                return constant;
            }
        }
        throw new IllegalArgumentException("文件名无效：" + path);
    }

    public String getPath() {
        return path;
    }
}
