package com.hntxrj.api;

import com.hntxrj.server.BuilderService;
import com.hntxrj.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/builder")
public class BuilderApi {
    private final BuilderService builderService;
    private ResultVO resultVO;

    @Autowired
    public BuilderApi(BuilderService builderService) {
        resultVO = new ResultVO();
        resultVO.setMsg("ok");
        resultVO.setCode(0);
        resultVO.setData(null);
        this.builderService = builderService;

    }


    /**
     * 获取施工单位下拉
     *
     * @param builderName 施工单位名称
     * @param compid      企业id
     * @param page        页码
     * @param pageSize    每页数量
     * @return 施工单位下拉对象
     */
    @PostMapping("/getDropDown")
    public ResultVO getDropDown(String builderName, String compid,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        resultVO.setData(builderService.getBuilderDropDown(compid, builderName, page, pageSize));
        return resultVO;


    }

}
