package com.hntxrj.txerp.im.impl;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.im.GroupService;
import com.hntxrj.txerp.vo.IMGroupMemberVO;
import com.hntxrj.txerp.vo.IMGroupVO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class GroupServiceImpl implements GroupService {

    @Autowired
    private AccountServiceImpl accountService;
    @Override
    public JSONObject createGroup(IMGroupVO imGroupVO) throws ErpException {
        JSONObject jsonObject = new JSONObject();

        if (imGroupVO == null) {
            throw new ErpException(ErrEumn.ACCOUNT_NULL_ERROR);
        }

        if (imGroupVO.getOwnerAccount() != null) {
            jsonObject.put("Owner_Account",imGroupVO.getOwnerAccount());
        }
        if (imGroupVO.getName() == null) {
            throw new ErpException(ErrEumn.GROUP_NAME_NULL_ERROR);
        }
        if (imGroupVO.getType() == null) {
            throw new ErpException(ErrEumn.GROUP_TYPE_NULL_EOORO);
        }
        jsonObject.put("Name", imGroupVO.getName());
        jsonObject.put("Type", imGroupVO.getType());


        JSONObject result = accountService.getUrl("create_group", jsonObject);
        return result;
    }

    @Override
    public JSONObject importGroupMember(String groupId, List<IMGroupMemberVO> memberList) throws ErpException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if (groupId == null || "".equals(groupId)) {
            throw new ErpException(ErrEumn.GROUP_ID_NULL_ERROR);
        }

        for (IMGroupMemberVO imGroupMemberVO : memberList) {
            JSONObject json = new JSONObject();
            if (imGroupMemberVO.getMemberAccount() != null && !"".equals(imGroupMemberVO.getMemberAccount())) {
                json.put("Member_Account", imGroupMemberVO.getMemberAccount());
            }
            if (!"Admin".equals(imGroupMemberVO.getRole())) {
                json.put("Role", "Member");
            }
            json.put("JoinTime", String.valueOf(new Date()));
            jsonArray.put(json);
        }

        jsonObject.put("GroupId", groupId);
        jsonObject.put("MemberList", jsonArray);
        JSONObject result = accountService.getUrl("import_group_member", jsonObject);
        return result;
    }
}
