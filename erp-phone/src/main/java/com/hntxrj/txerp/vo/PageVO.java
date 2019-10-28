package com.hntxrj.txerp.vo;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 * <p>内含转换方法</p>
 *
 * @param <T>
 * @author zzlhr
 */
@Data
public class PageVO<T> implements Serializable {

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

}
