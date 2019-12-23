package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.InvitationVO;
import com.hntxrj.txerp.vo.bdBindVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface ConstructionMapper {

    void  getInvitationCode(String buildInvitationCode, String compid, String contractDetailCode,
                           Integer useStatus, Integer opid, Date date,String contractUID);

    List<InvitationVO> getInvitationList(String compid, String buildCode, String useStatus,
                                         String createUser, String beginTime, String endTime);

    void updateUseStatus(String contractUID,String contractDetailCode,String buildInvitationCode, int useStatus);

    List<InvitationVO> selectInvitation(String buildInvitationCode);

    void saveInvitation(String buildId, String compid, String contractDetailCode,String contractUID);

    List<bdBindVO> checkBind(String buildId);

    bdBindVO selectCompid(String contractDetailCode,String contractUID,String buildId);

    List<String> getContractCodeList(Integer buildId);

    List<String> getContractUID(Integer buildId);
}
