package com.hntxrj.txerp.api;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.server.QueryTimeSetService;
import com.hntxrj.txerp.vo.QueryTimeSetVO;
import com.hntxrj.txerp.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    /**
     * 编辑默认时间
     * @param list 传递的json参数
     */
    @PostMapping("/upDateQueryTime")
    public ResultVO upDateQueryTime(@RequestBody List<QueryTimeSetVO> list) {
        queryTimeSetService.upDateQueryTime(list);
        return ResultVO.create();
    }
}
