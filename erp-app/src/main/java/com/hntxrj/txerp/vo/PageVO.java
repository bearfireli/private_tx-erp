package com.hntxrj.txerp.vo;

import com.github.pagehelper.PageInfo;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.Data;

import java.util.List;

/**
 * 分页对象
 * <p>内含转换方法</p>
 *
 * @param <T>
 * @author zzlhr
 */
@Data
public class PageVO<T> {

    // 总页数
    private long totalPage;
    // 每页数量
    private long pageSize = 10;
    // 当前页码
    private long currentPage;
    // 总条数
    private long totalCount;
    // 本页数量
    private long currentNumber;

    private List<T> arr;


    /**
     * querydsl 的分页对象转换为统一分页内容方法。
     *
     * @param queryResults querydsl分页对象
     * @param page         页码
     * @param pageSize     每页数量
     * @return PageVO对象
     */
    public PageVO<T> format(QueryResults<T> queryResults, long page, long pageSize) {
        this.setPageSize(pageSize);
        this.setArr(queryResults.getResults());
        this.setCurrentPage(page);
        this.setCurrentNumber(queryResults.getResults().size());
        this.setTotalCount(queryResults.getTotal());
        this.setTotalPage(this.getTotalCount() / pageSize);
        if (this.getTotalCount() % pageSize > 0) {
            this.setTotalPage(this.getTotalPage() + 1);
        }
        return this;
    }


    public PageVO<T> format(PageInfo<T> pageInfo) {
        this.setPageSize(pageInfo.getPageSize());
        this.setArr(pageInfo.getList());
        this.setCurrentPage(pageInfo.getPageNum());
        this.setCurrentNumber(pageInfo.getSize());
        this.setTotalCount(pageInfo.getTotal());
        this.setTotalPage(pageInfo.getPages());
        return this;
    }


    public void init(long totalCount, long currentPage, List<T> arr) {
        this.currentPage = currentPage;
        this.arr = arr;
        this.totalCount = totalCount;
        this.totalPage = totalCount / pageSize;
        this.currentNumber = arr.size();
        if (totalCount % pageSize > 0) {
            this.totalPage += 1;
        }
    }

    public void init(JPAQuery<T> jpaQuery, long pageSize) {
        this.arr = jpaQuery.fetch();
        this.totalCount = jpaQuery.fetchCount();
        this.totalPage = totalCount / pageSize;
        this.currentNumber = arr.size();
        if (totalCount % pageSize > 0) {
            this.totalPage += 1;
        }
    }
}
