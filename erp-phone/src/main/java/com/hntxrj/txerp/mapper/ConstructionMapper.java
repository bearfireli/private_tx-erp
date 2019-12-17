package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.InvitationVO;
import com.hntxrj.txerp.vo.bdBindVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface ConstructionMapper {

    void  getInvitationCode(String build_Invitation_Code, String compid, String ccontractCode,
                           Integer use_Status, Integer opid, Date date,String contractUID);

    List<InvitationVO> getInvitationList(String compid, String buildCode, String useStatus,
                                         String createUser, String beginTime, String endTime);

    void updateUseStatus(String contractUID,String ccontractCode,String buildInvitationCode, int useStatus);

    List<InvitationVO> selectInvitation(String buildInvitationCode);

    void saveInvitation(String buildId, String compid, String ccontractCode,String contractUID);

    List<bdBindVO> selectBind(String buildId);

    bdBindVO selectCompid(String ccontractCode,String contractUID,String buildId);

    List<String> getContractodeList(Integer buildId);

    List<String> getContractUID(Integer buildId);
}
