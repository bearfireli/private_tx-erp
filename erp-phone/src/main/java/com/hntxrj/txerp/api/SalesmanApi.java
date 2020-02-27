package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.SalesmanService;
import com.hntxrj.txerp.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/salesman")
public class SalesmanApi {

    private final SalesmanService salesmanService;

    private ResultVO resultVO;

    @Autowired
    public SalesmanApi(SalesmanService salesmanService) {
        resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("ok");
        resultVO.setData(null);
        this.salesmanService = salesmanService;
    }

    /**
     * 获取销售员下拉
     *
     * @param salesName 销售员名称
     * @param compid    企业id
     * @param page      分页
     * @param pageSize  每页数量
     * @return 销售员下拉对象
     */
    @PostMapping("/getDropDown")
    public ResultVO getDropDown(String salesName, String compid,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        resultVO.setData(salesmanService.getSalesmanDropDown(salesName, compid, page, pageSize));
        return resultVO;
    }

    /**
     * 获取业务员的分组
     *
     * @param compid 企业id
     * @param page      分页
     * @param pageSize  每页数量
     */
    @PostMapping("/getSaleGroup")
    public ResultVO getSaleGroup(String compid,
                                 @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(salesmanService.getSaleGroup(compid,page,pageSize));
    }

    /**
     * 添加销售员
     *
     * @param compid   企业id
     * @param saleName 销售员姓名
     * @param department 销售员部门分组
     * */
    @PostMapping("/addSaleMan")
    public ResultVO addSaleMan(String compid,String saleName,String department) {
        salesmanService.addSaleMan(compid, saleName, department);
        return ResultVO.create();
    }


}
