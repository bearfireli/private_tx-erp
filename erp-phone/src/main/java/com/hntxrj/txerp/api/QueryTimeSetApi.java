package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.QueryTimeSetService;
import com.hntxrj.txerp.vo.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author qyb
 **/
@RequestMapping("/api/querytimeset")
@RestController
public class QueryTimeSetApi {

    private  final QueryTimeSetService queryTimeSetService;

    public QueryTimeSetApi(QueryTimeSetService queryTimeSetService) {
        this.queryTimeSetService = queryTimeSetService;
    }

    /**
     * 查询时间设置列表
     * @param compid  站别代号
     * @param page     当前页
     * @param pageSize  每页显示多少条
     * @return 返回json
     */
    @PostMapping("/getQueryTimeSetList")
    public ResultVO getQueryTimeSetList(String compid, @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "30") Integer pageSize){
        return ResultVO.create(queryTimeSetService.getQueryTimeSetList(compid, page, pageSize));
    }
}
