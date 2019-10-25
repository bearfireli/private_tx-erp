package com.hntxrj.server;

import com.hntxrj.exception.ErpException;
import com.hntxrj.vo.InvitationVO;
import com.hntxrj.vo.PageVO;
import com.hntxrj.vo.bdBindVO;

import java.sql.SQLException;

public interface ConstructionService {
    InvitationVO getInvitationCode(String compid, Integer  opid, String buildCode) throws ErpException;

    PageVO<InvitationVO> getInvitationList(String compid, String buildCode, String useStatus, String createUser,
                                           String beginTime,String endTime,Integer page,Integer pageSize);

    void updateUseStatus(String compid, String buildInvitationCode) throws ErpException;

    void saveInvitation(String buildId, String buildInvitationCode) throws ErpException, SQLException;

    PageVO<bdBindVO>  selectBind(String buildId, Integer page, Integer pageSize);
}
