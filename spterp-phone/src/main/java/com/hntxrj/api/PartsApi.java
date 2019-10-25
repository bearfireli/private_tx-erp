package com.hntxrj.api;
import com.hntxrj.entity.WmConFigureApply;
import com.hntxrj.exception.ErpException;
import com.hntxrj.server.PartsService;
import com.hntxrj.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author qyb
 *  PartsApi
 *  仓库配件
 *  19-7-9 上午8:35
 **/
@RestController
@RequestMapping("/api/parts")
public class PartsApi {

    private final PartsService partsService;

    @Autowired
    public PartsApi(PartsService partsService) {
        this.partsService = partsService;
    }

    /**
     *   申请人列表
     * @param compid       企业
     * @param page         分页
     * @param pageSize     每页显示条数
     * @return        申请人列表
     */
    @PostMapping("/getBuyerList")
    public ResultVO getBuyerList(String compid,
                                 @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(partsService.getBuyerList(compid, page, pageSize));
    }

    /**
     *   部门列表
     * @param compid       企业
     * @param page         分页
     * @param pageSize     每页显示条数
     * @return        部门列表
     */
    @PostMapping("/getDepartmentList")
    public ResultVO getDepartmentList(String compid,
                                 @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(partsService.getDepartmentList(compid, page, pageSize));
    }


    /**
     * 申请模式
     * @param compid       企业
     * @param page         分页
     * @param pageSize     每页显示条数
     * @return          申请模式
     */
    @PostMapping("/getRequestMode")
    public ResultVO getRequestMode(String compid,
                                 @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(partsService.getRequestMode(compid, page, pageSize));
    }

    /**
     *   申请单状态
     * @param page         分页
     * @param pageSize     每页显示条数
     * @return        申请单状态
     */
    @PostMapping("/getRequestStatusList")
    public ResultVO getRequestStatusList(String compid,
                                      @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(partsService.getRequestStatusList(compid,page, pageSize));
    }


    /**
     * 配件列表
     * @param compid       企业
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param goodsName　　物品名称
     * @param buyer　　　　　申请人
     * @param specification　物品规格
     * @param department　　　申请部门
     * @param requestNumber　　申请单号
     * @param requestStatus　　申请单状态
     * @param requestDep　　　申请描述
     * @param page         分页
     * @param pageSize     每页显示条数
     * @return  　　　　　　　配件列表
     * */
    @PostMapping("/getPartsList")
    public ResultVO getPartsList(String compid,Long beginTime, Long endTime,String goodsName,
                                                   String buyer,String specification,String department,
                                           String requestNumber,String requestStatus,
                                           String requestDep,
                                           @RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(partsService.getPartsList(
                compid,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),goodsName,
                buyer, specification, department,requestNumber,requestStatus,requestDep,
                 page, pageSize));
    }

    /**
     * 申请配件查询详情
     * @param requestNumber  申请单号
     * @param compid       企业
     * @return   申请配件查询详情
     */
    @PostMapping("/getRequestNumberDetail")
    public ResultVO getRequestNumberDetail(String requestNumber,String compid){
        return  ResultVO.create(partsService.getRequestNumberDetail(requestNumber,compid));
    }

    /**
     * 添加配件申请单
     * @param wmConFigureApply      配件实体类
     * @return    添加配件申请单
     * @throws ErpException 异常处理
     */
    @PostMapping("/addWmConFigureApply")
    public ResultVO addWmConFigureApply(WmConFigureApply wmConFigureApply) throws ErpException {
        partsService.addWmConFigureApply(wmConFigureApply);
        return ResultVO.create();
    }

    /**
     * 修改申请单
     * @param wmConFigureApply  配件实体类
     * @return      修改申请单
     * @throws ErpException  异常处理
     */
    @PostMapping("/editWmConFigureApply")
    public ResultVO editWmConFigureApply(WmConFigureApply wmConFigureApply) throws ErpException {
        partsService.editWmConFigureApply(wmConFigureApply);
        return ResultVO.create();
    }

    /**
     *配件申请审核
     * @param compid    企业
     * @param requestNumber  申请单号
     * @param requestStatus   审核结果
     * @param verifyIdOne     操作员
     * @return      配件申请审核
     * @throws ErpException  异常处理
     */
    @PostMapping("/editRequestStatus")
    public ResultVO editRequestStatus(String compid,String  requestNumber,String requestStatus,String verifyIdOne)
            throws ErpException {
        partsService.editRequestStatus(compid,requestNumber,requestStatus,verifyIdOne);
        return ResultVO.create();
    }

    /**
     * 取消配件申请审核
     * @param compid    企业
     * @param requestNumber  申请单号
     * @param requestStatus   审核结果
     * @param verifyIdOne     操作员
     * @return      配件申请审核
     * @throws ErpException  异常处理
     */
    @PostMapping("/cancelRequestStatus")
    public ResultVO cancelRequestStatus(String compid,String  requestNumber,String requestStatus,String verifyIdOne)
            throws ErpException {
        partsService.cancelRequestStatus(compid,requestNumber,requestStatus,verifyIdOne);
        return ResultVO.create();
    }

    /**
     * 助记码
     * @param compid  企业id
     * @param page        当前页
     * @param pageSize      每页显示数
     * @return      助记码
     */
    @PostMapping("/getMnemonicCodeList")
    public ResultVO getMnemonicCodeList(String compid,
                                        @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer pageSize){
        return  ResultVO.create(partsService.getMnemonicCodeList(compid,page,pageSize));
    }
}
