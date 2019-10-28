package com.hntxrj.txerp.vo;

/**
 * @author lhr
 * @create 2018/1/31
 */
public class PublicInfoVO {

    private Integer value;

    private String typeName;


    @Override
    public String toString() {
        return "PublicInfoVO{" +
                "value=" + value +
                ", typeName='" + typeName + '\'' +
                '}';
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
