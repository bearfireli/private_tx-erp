package com.hntxrj.entity;

import lombok.Data;

/**
 * 功能:  分页实体类
 *
 * @Auther 李帅
 * @Data 2017-08-08.上午 9:59
 */
@Data
public class PageBean implements java.io.Serializable{
    private Integer pageCount  ;    //总页数
    private Integer pageSize  ;     //每页显示多少条数据
    private Integer pageCurr ;     //当前页
    private Integer recordCount ; //数据总数

    /**
     *  构造PageBean对象
     * @param pageSize 每页显示多少条数据
     * @param pageCurr //当前页
     */
    public PageBean(Integer pageSize, Integer pageCurr) {
        this.pageSize = pageSize;
        this.pageCurr = pageCurr;
    }


    /**
     * 获取总页数
     * @return
     */
    public Integer getPageCount() {
        if (recordCount ==null ){
            return 0;
        }else {
            return recordCount%pageSize==0?recordCount/pageSize:recordCount/pageSize+1;
        }
    }

}
