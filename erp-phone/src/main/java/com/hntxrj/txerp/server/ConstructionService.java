package com.hntxrj.txerp.server;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.InvitationVO;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.bdBindVO;

import java.sql.SQLException;

public interface ConstructionService {
    InvitationVO getInvitationCode(String compid, Integer  opid, String buildCode) throws ErpException;

    PageVO<InvitationVO> getInvitationList(String compid, String buildCode, String useStatus, String createUser,
                                           String beginTime,String endTime,Integer page,Integer pageSize);

    void updateUseStatus(String compid, String buildInvitationCode) throws ErpException;

    void saveInvitation(String buildId, String buildInvitationCode) throws ErpException, SQLException;

    PageVO<bdBindVO>  selectBind(String buildId, Integer page, Integer pageSize);
}
