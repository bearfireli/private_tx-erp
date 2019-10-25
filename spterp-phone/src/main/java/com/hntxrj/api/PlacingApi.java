package com.hntxrj.api;

import com.hntxrj.server.PlacingService;
import com.hntxrj.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/placing")
public class PlacingApi {
    private final PlacingService placingService;
    private ResultVO resultVO;

    @Autowired
    public PlacingApi(PlacingService placingService) {
        resultVO = new ResultVO();
        resultVO.setMsg("ok");
        resultVO.setCode(0);
        resultVO.setData(null);
        this.placingService = placingService;
    }


    /**
     * 获取浇灌部位下拉
     * @param placing 浇灌部位
     * @param compid      企业id
     * @param page        页码
     * @param pageSize    每页数量
     * @return 浇灌部位下拉对象
     */

    @PostMapping("/getDropDown")
    public ResultVO getDropDown(String placing, String compid,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        resultVO.setData(placingService.getPlacingDropDown(compid, placing, page, pageSize));
        return resultVO;
    }

}