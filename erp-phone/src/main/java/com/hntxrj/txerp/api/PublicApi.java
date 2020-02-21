package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.DropDownService;
import com.hntxrj.txerp.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/public")
@RestController
public class PublicApi {

    private final DropDownService dropDownService;


    @Autowired
    public PublicApi(DropDownService dropDownService) {
        this.dropDownService = dropDownService;
    }

    /**
     * 获取公共下拉
     *
     * @param classId 查询类型
     * @param compid  企业id
     */
    @PostMapping("/getDropDown")
    public ResultVO getDropDown(Integer classId, String compid) {
        return ResultVO.create(dropDownService.getDropDown(classId, compid));
    }


    /**
     * 获取供应商下拉集合
     *
     * @param compid 企业id
     */
    @PostMapping("/getSupplierList")
    public ResultVO getSupplierList(String compid,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(dropDownService.getSupplierList(compid, page, pageSize));
    }

    /**
     * 获取库位下垃集合
     *
     * @param compid 企业id
     */
    @PostMapping("/getStockList")
    public ResultVO getStockList(String compid,
                                 @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(dropDownService.getStockList(compid, page, pageSize));
    }


}
