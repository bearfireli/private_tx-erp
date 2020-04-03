package com.hntxrj.txerp.api;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.server.ConstructionService;
import com.hntxrj.txerp.vo.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author qyb
 *  ConstructionApi
 *  施工单位
 *  19-7-25 下午4:06
 **/
@RestController
@RequestMapping("/api/construction")
public class ConstructionApi {

    private  final ConstructionService constructionService;

    public ConstructionApi(ConstructionService constructionService) {
        this.constructionService = constructionService;
    }

    /**
     * 生成施工方邀请码
     * @param compid   企业
     * @param opid     操作人
     * @param contractDetailCodes     子合同号集合
     * @return   生成邀请码
     */
    @PostMapping("/getInvitationCode")
    public ResultVO getInvitationCode(String compid, Integer  opid, String contractDetailCodes) throws ErpException {
        return ResultVO.create(constructionService.getInvitationCode(compid,opid,contractDetailCodes));
    }

    /**
     * 施工方邀请码列表
     * @param compid            企业
     * @param buildCode       施工方代码
     * @param useStatus        状态 已使用1、未使用0
     * @param createUser       创建人
     * @param beginTime         开始时间
     * @param endTime           结束时间
     * @param page              页码
     * @param pageSize          每页数量
     * @return                  施工方邀请码列表
     */
    @PostMapping("/getInvitationList")
    public ResultVO getInvitationList(String compid, String buildCode, String useStatus, String createUser,
                                                  Long beginTime, Long endTime,
                                                  @RequestParam(defaultValue = "1") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(constructionService.getInvitationList(compid, buildCode,
                useStatus, createUser, beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    /**
     * 作废施工方邀请码
     * @param contractUID  主合同
     * @param contractDetailCode  子合同
     * @param buildInvitationCode  邀请码
     */
    @PostMapping("/invalidInvitationCode")
    public ResultVO invalidInvitationCode(String contractUID,String contractDetailCode, String buildInvitationCode) throws ErpException {
        constructionService.invalidInvitationCode(contractUID,contractDetailCode, buildInvitationCode);
        return ResultVO.create();
    }

    /**
     * 绑定施工方邀请码
     * @param buildId  用户id
     * @param buildInvitationCode 邀请码
     * @throws ErpException 异常处理
     */
    @PostMapping("/saveInvitation")
    public ResultVO saveInvitation(String buildId, String buildInvitationCode) throws ErpException, SQLException {
        constructionService.saveInvitation(buildId, buildInvitationCode);
        return ResultVO.create();
    }


    /**
     * 查询绑定合同
     * @param buildId  用户id
     * @return        查询绑定合同
     */
    @PostMapping("/checkBind")
    public ResultVO checkBind(String buildId) {
        return ResultVO.create(constructionService.checkBind(buildId));
    }

    /**
     *  删除合同
     * @param buildId   用户id
     * @param ccontractCode   子合同号
     * @param compid 企业代号
     */
    @PostMapping("deleteBuildId")
    public ResultVO deleteBuildId(String buildId,String ccontractCode,String compid){
        constructionService.deleteBuildId(buildId,ccontractCode,compid);
        return  ResultVO.create();
    }

}
