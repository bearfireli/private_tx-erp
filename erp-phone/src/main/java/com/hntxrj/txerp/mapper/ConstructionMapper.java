package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.BdBindVO;
import com.hntxrj.txerp.vo.InvitationVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface ConstructionMapper {

    /**
     * 生成施工方邀请码
     *
     * @param buildInvitationCode 邀请码
     * @param compid              企业
     * @param contractDetailCode  子合同号
     * @param useStatus           邀请码使用状态
     * @param opid                操作人
     * @param date                日期
     * @param contractUID         主合同号
     */
    void getInvitationCode(String buildInvitationCode, String compid, String contractDetailCode,
                           Integer useStatus, Integer opid, Date date, String contractUID);

    /**
     * 施工方邀请码列表
     *
     * @param compid     企业
     * @param buildCode  施工方代码
     * @param useStatus  状态 已使用1、未使用0
     * @param createUser 创建人
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @return 施工方邀请码列表
     */
    List<InvitationVO> getInvitationList(String compid, String buildCode, String useStatus,
                                         String createUser, String beginTime, String endTime);

    /**
     * 修改施工方邀请码状态
     *
     * @param contractUID         主合同号
     * @param contractDetailCode  子合同号
     * @param buildInvitationCode 邀请码
     * @param useStatus           邀请码使用状态
     */
    void updateUseStatus(String contractUID, String contractDetailCode, String buildInvitationCode, int useStatus);


    /**
     * 获取施工方邀请码绑定的合同集合
     */
    List<InvitationVO> selectInvitation(String buildInvitationCode);

    /**
     * 绑定用户和施工方邀请码
     *
     * @param buildId            施工方用户id
     * @param compid             企业
     * @param contractDetailCode 子合同号
     * @param contractUID        主合同号
     */
    void saveInvitation(String buildId, String compid, String contractDetailCode, String contractUID);

    /**
     * 查询施工方用户是否绑定邀请码
     *
     * @param buildId 施工方用户id
     */
    List<BdBindVO> checkBind(String buildId);


    /**
     * 查询该用户是否绑定过某个合同
     *
     * @param contractDetailCode 子合同号
     * @param contractUID        主合同号
     * @param buildId            施工方用户id
     */
    BdBindVO checkBindContract(String contractDetailCode, String contractUID, String buildId);

    /**
     * 获取用户绑定的子合同号集合
     *
     * @param buildId 施工方id
     * @return 子合同号集合
     */
    List<String> getContractCodeList(Integer buildId);

    /**
     * 获取用户绑定的主合同号集合
     *
     * @param buildId 施工方id
     * @return 主合同号集合
     */
    List<String> getContractUID(Integer buildId);

    /**
     *  删除合同
     * @param buildId   用户id
     * @param contractCode   主合同号
     */
    void deleteBuildId(String buildId, String contractCode);
}
