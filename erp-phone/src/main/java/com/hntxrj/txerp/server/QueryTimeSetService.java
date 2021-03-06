package com.hntxrj.txerp.server;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.QueryTimeSetVO;

import java.util.List;
import java.util.Map;

public interface QueryTimeSetService {

    /**
     * 查询时间设置列表
     *
     * @param compid   站别代号
     * @param page     当前页
     * @param pageSize 每页显示多少条
     * @return 返回json
     */
    PageVO<QueryTimeSetVO> getQueryTimeSetList(String compid, Integer page, Integer pageSize);

    /**
     * 编辑默认时间
     * @param list 传递的json参数
     */
    void upDateQueryTime( List<QueryTimeSetVO> list);

}
