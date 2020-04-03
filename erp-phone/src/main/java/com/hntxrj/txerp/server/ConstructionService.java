package com.hntxrj.txerp.server;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.InvitationVO;
import com.hntxrj.txerp.vo.PageVO;

import java.sql.SQLException;
import java.util.Map;

public interface ConstructionService {

    /**
     * 生成施工方邀请码
     * @param compid   企业
     * @param opid     操作人
     * @param contractDetailCodes     子合同号集合
     * @return   邀请码
     */
    InvitationVO getInvitationCode(String compid, Integer  opid, String contractDetailCodes) throws ErpException;

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
    PageVO<InvitationVO> getInvitationList(String compid, String buildCode, String useStatus, String createUser,
                                           String beginTime,String endTime,Integer page,Integer pageSize);

    /**
     * 作废施工方邀请码
     * @param contractUID  主合同
     * @param contractDetailCode  子合同
     * @param buildInvitationCode  邀请码
     */
    void invalidInvitationCode(String contractUID,String contractDetailCode, String buildInvitationCode) throws ErpException;

    /**
     * 绑定施工方邀请码
     * @param buildId  用户id
     * @param buildInvitationCode 邀请码
     * @throws ErpException 异常处理
     */
    void saveInvitation(String buildId, String buildInvitationCode) throws ErpException, SQLException;

    /**
     * 查询绑定合同
     * @param buildId  用户id
     * @return        查询绑定合同
     */
    Map<String,Boolean> checkBind(String buildId);

    /**
     *  删除合同
     * @param buildId   用户id
     * @param ccontractCode   子合同号
     * @param compid 企业代号
     */
    void deleteBuildId(String buildId, String ccontractCode,String compid);
}
