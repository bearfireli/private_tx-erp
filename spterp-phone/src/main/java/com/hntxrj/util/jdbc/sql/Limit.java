package com.hntxrj.util.jdbc.sql;

/**
 * 分页对象
 * @author lhr
 * @create 2018/1/11
 */
public class Limit {

    private int pageNumber;

    private int pageSize;

    private Field orderByField;


    public Limit(int pageNumber, int pageSize, Field orderByField) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.orderByField = orderByField;
    }

    @Override
    public String toString() {
        return pageNumber + "," + pageSize*pageNumber;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Field getOrderByField() {
        return orderByField;
    }

    public void setOrderByField(Field orderByField) {
        this.orderByField = orderByField;
    }
}
