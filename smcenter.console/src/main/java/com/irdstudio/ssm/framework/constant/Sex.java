package com.irdstudio.ssm.framework.constant;

/**
 * 性别枚举类
 * @author chenqiming
 *
 */
public enum Sex {
    MALE("1", "男", "先生"),
    FEMALE("2", "女", "女士"),
    ;
    private String enname;//字典码
    private String cnname;//字典描述
    private String appellation;//称谓：先生/女士
    
    private Sex(String enname, String cnname, String appellation) {
        this.enname = enname;
        this.cnname = cnname;
        this.appellation = appellation;
    }

    public String getEnname() {
        return enname;
    }

    public String getCnname() {
        return cnname;
    }

    public String getAppellation() {
        return appellation;
    }
}
