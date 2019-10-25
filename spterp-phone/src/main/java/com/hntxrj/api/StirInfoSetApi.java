package com.hntxrj.api;

import com.hntxrj.server.StirInfoSetService;
import com.hntxrj.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qyb
 *  BuildingNumberApi
 * 楼号
 *  19-6-5 下午7:01
 **/
@RestController
@RequestMapping("/api/stirInfoSet")
public class StirInfoSetApi {

    private final StirInfoSetService stirInfoSetService;

    @Autowired
    public StirInfoSetApi(StirInfoSetService stirInfoSetService) {
        this.stirInfoSetService = stirInfoSetService;
    }

    /**
     * 楼号
     * @param compid  企业id
     */
    @PostMapping("/getStirInfoSet")
    public ResultVO getStirInfoSet(String compid) {

        return ResultVO.create(stirInfoSetService.getStirInfoSet(compid));
    }
}
