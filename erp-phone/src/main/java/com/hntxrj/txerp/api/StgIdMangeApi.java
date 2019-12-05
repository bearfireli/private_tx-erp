package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.StgIdMangeService;
import com.hntxrj.txerp.vo.ResultVO;
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
    public ResultVO getStgIdManage(String compid, String stgId, String grade,
                                   @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(stgIdMangeService.getStgidManage(compid,stgId,grade,page,pageSize));
    }




    /**
     * 砼标号详情
     *
     * @param compid    企业id
     * @param stgId  砼标号
     * @param grade  强度等级
     * @return  砼标号管理
     */
    @PostMapping("/getStgIdManageDetail")
    public ResultVO getStgIdManageDetail(String compid, String stgId, String grade) {
        return ResultVO.create(stgIdMangeService.getStgIdManageDetail(compid,stgId,grade));
    }

    /**
     * 编辑砼标号
     *
     * @param compid          企业id
     * @param stgId           砼标号
     * @param grade           强度等级
     * @param pumpPrice       泵送价格
     * @param notPumpPrice    自卸价格
     * @param towerCranePrice 塔吊价格
     */
    @PostMapping("/updateStgIdManage")
    public ResultVO updateStgIdManage(String compid, String stgId, String grade, String pumpPrice, String notPumpPrice, String towerCranePrice) {
        stgIdMangeService.updateStgIdManage(compid, stgId, grade, pumpPrice, notPumpPrice, towerCranePrice);
        return ResultVO.create();
    }

    /**
     * 添加砼标号
     *
     * @param compid          企业id
     * @param stgId           砼标号
     * @param grade           强度等级
     * @param pumpPrice       泵送价格
     * @param notPumpPrice    自卸价格
     * @param towerCranePrice 塔吊价格
     */

    @PostMapping("/insertStgIdManage")
    public ResultVO insertStgIdManage(String compid, String stgId, String grade, String pumpPrice, String notPumpPrice, String towerCranePrice) {
        try {
            stgIdMangeService.insertStgIdManage(compid, stgId, grade, pumpPrice, notPumpPrice, towerCranePrice);
            return ResultVO.create();
        } catch (Exception e) {

            ResultVO resultVO = new ResultVO();
            resultVO.setCode(1);
            resultVO.setMsg("此标号已存在");
            return resultVO;
        }


    }

    /**
     * 删除砼标号
     *
     * @param compid 企业id
     * @param stgId  砼标号
     */
    @PostMapping("/deleteStgManage")
    public ResultVO deleteStgIdManage(String compid, String stgId) {
        stgIdMangeService.deleteStgManage(compid, stgId);
        return ResultVO.create();
    }




}
