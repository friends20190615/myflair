package com.myflair.common.constant;


public enum UserRoleEnum {
    ADMIN("超级管理员",0),
    NEWS_PUBLISHER("普通管理员",2);
   

    private Integer type;
    private String typeDesc;

    private UserRoleEnum(String typeDesc, Integer type) {
        this.type = type;
        this.typeDesc = typeDesc;
    }

    public static String getTypeDesc(Integer type) {
        for (UserRoleEnum v : UserRoleEnum.values()) {
            if (type.equals(v.getType())) {
                return v.getTypeDesc();
            }
        }
        return null;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }
}