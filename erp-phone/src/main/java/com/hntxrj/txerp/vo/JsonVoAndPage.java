package com.hntxrj.txerp.vo;

import com.hntxrj.txerp.entity.PageBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能: 带分页信息的JsonVo
 *
 * @Auther 李帅
 * @Data 2017-08-08.下午 3:18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class JsonVoAndPage<T> extends JsonVo {

    /**
     * 分页信息
     */
    private PageBean pageBean;

    /**
     * 多个分页信息
     */
    private List<PageBean> listPageBean = new ArrayList<>();

}
