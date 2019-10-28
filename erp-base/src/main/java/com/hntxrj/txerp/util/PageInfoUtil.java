package com.hntxrj.txerp.util;

import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.vo.PageVO;


public class PageInfoUtil<T> {

    @Deprecated
    public PageVO<T> init(PageInfo<T> pageInfo) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.init(pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getList());
        return pageVO;
    }


}
