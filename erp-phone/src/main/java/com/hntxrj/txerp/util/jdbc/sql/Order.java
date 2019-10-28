package com.hntxrj.txerp.util.jdbc.sql;

/**
 * 排序对象
 * @author lhr
 * @create 2018/1/11
 */
public class Order {

    static final int TYPE_ASC = 0;


    static final int TYPE_DESC = 1;

    private String fields;

    private int type;

    Order(String fields, int type){
        this.fields = fields;
        this.type = type;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
