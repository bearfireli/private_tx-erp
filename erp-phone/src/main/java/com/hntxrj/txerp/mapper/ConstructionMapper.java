package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.InvitationVO;
import com.hntxrj.txerp.vo.bdBindVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface ConstructionMapper {

    void  getInvitationCode(String build_Invitation_Code, String compid, String buildCode,
                           Integer use_Status, Integer opid, Date date);

    List<InvitationVO> getInvitationList(String compid, String buildCode, String useStatus,
                                         String createUser, String beginTime, String endTime);

    void updateUseStatus(String compid, String buildInvitationCode, int useStatus);

    InvitationVO selectInvitation(String buildInvitationCode);

    void saveInvitation(String buildId, String compid, String buildCode);

    List<bdBindVO> selectBind(String buildId);

    bdBindVO selectCompid(String compid,String buildId);
}
