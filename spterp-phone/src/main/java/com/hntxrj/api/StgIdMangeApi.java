package com.hntxrj.api;

import com.hntxrj.server.ProducetionStatisticsService;
import com.hntxrj.server.StgIdMangeService;
import com.hntxrj.vo.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/*砼标号管理*/
@RestController
@RequestMapping("/api/stgIdMange")
public class StgIdMangeApi {
    private final StgIdMangeService stgIdMangeService;

    public StgIdMangeApi(StgIdMangeService stgIdMangeService) {
        this.stgIdMangeService = stgIdMangeService;
    }

    /**
     * 砼标号管理
     *
     * @param compid    企业id
     * @param stgId  砼标号
     * @param grade  强度等级
     * @return  砼标号管理
     */
    @PostMapping("/getStgIdManage")
    public ResultVO getStgIdManage(String compid, String stgId,String grade,
                                   @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(stgIdMangeService.getStgidManage(compid,stgId,grade,page,pageSize));
    }


}
