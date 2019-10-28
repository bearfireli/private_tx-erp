package com.hntxrj.txerp.util.jdbc.sql;

import java.util.Collections;
import java.util.List;

/**
 * 分页数据
 * @author lhr
 * @create 2018/1/30
 */
public class Page<T> {

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 当前页数
     */
    private int currentPage;

    /**
     * 每个数量
     */
    private int pageSize;

    /**
     * 总条数
     */
    private int total;

    private List<T> content;


    @Override
    public String toString() {
        return "Page{" +
                "totalPages=" + totalPage +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", content=" + content +
                '}';
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void countTotalPage(){
        this.totalPage = this.total / this.pageSize;
        if (this.totalPage * this.pageSize < this.total){
            this.totalPage += 1;
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
