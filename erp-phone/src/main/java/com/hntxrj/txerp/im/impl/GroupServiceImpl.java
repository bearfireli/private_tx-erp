package com.hntxrj.txerp.im.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.im.GroupService;
import com.hntxrj.txerp.vo.IMGroupMemberVO;
import com.hntxrj.txerp.vo.IMGroupVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GroupServiceImpl implements GroupService {

    private AccountServiceImpl accountService;

    //腾讯云即时通讯IM的请求路径
    String interfaceUrl = "group_open_http_svc/";

    public GroupServiceImpl(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @Override
    public JSONObject createGroup(IMGroupVO imGroupVO) throws ErpException {
        JSONObject jsonObject = new JSONObject();

        if (imGroupVO == null) {
            throw new ErpException(ErrEumn.ACCOUNT_NULL_ERROR);
        }

        if (imGroupVO.getOwnerAccount() != null) {
            jsonObject.put("Owner_Account", imGroupVO.getOwnerAccount());
        }
        if (imGroupVO.getName() == null) {
            throw new ErpException(ErrEumn.GROUP_NAME_NULL_ERROR);
        }
        if (imGroupVO.getType() == null) {
            throw new ErpException(ErrEumn.GROUP_TYPE_NULL_EOORO);
        }
        jsonObject.put("Name", imGroupVO.getName());
        jsonObject.put("Type", imGroupVO.getType());


        return accountService.requestIMApi(interfaceUrl, "create_group", jsonObject);
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
                json.put("Role", imGroupMemberVO.getRole());
            }
            json.put("JoinTime", String.valueOf(System.currentTimeMillis()));
            jsonArray.add(json);
        }

        jsonObject.put("GroupId", groupId);
        jsonObject.put("MemberList", jsonArray);
        return accountService.requestIMApi(interfaceUrl, "import_group_member", jsonObject);
    }
}
