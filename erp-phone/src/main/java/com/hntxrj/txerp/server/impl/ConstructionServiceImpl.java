package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.mapper.ConstructionMapper;
import com.hntxrj.txerp.mapper.ContractMapper;
import com.hntxrj.txerp.server.ConstructionService;
import com.hntxrj.txerp.vo.InvitationVO;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.bdBindVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * @author qyb
 * ConstructionServiceImpl
 * TODO
 * 19-7-25 下午4:08
 **/
@Service
@Slf4j
public class ConstructionServiceImpl implements ConstructionService {

    @Value("${app.cloud.host}")
    private String url;

    private final ConstructionMapper constructionMapper;

    private final ContractMapper contractMapper;


    public ConstructionServiceImpl(ConstructionMapper constructionMapper, ContractMapper contractMapper) {
        this.constructionMapper = constructionMapper;
        this.contractMapper = contractMapper;
    }

    @Override
    public InvitationVO getInvitationCode(String compid, Integer opid, String contractDetailCodes) throws ErpException {


        String buildInvitationCode = UUID.randomUUID().toString().replace("-", "");
        Date date = new Date();
        Integer use_Status = 0;
        String[] codes = contractDetailCodes.split(",");
        try {
            for (String code : codes) {
                //先根据子合同号和compid从合同表中查询出每一个主合同号
                String contractUID = contractMapper.getContractUID(compid, code);
                //把邀请码，compid，子合同号，主合同号插入
                constructionMapper.getInvitationCode(buildInvitationCode, compid, code, use_Status, opid, date,contractUID);
            }
            InvitationVO invitationVO = new InvitationVO();
            invitationVO.setBuildinvitationcode(buildInvitationCode);
            return invitationVO;
        } catch (Exception e) {
            throw new ErpException(ErrEumn.ADD_INVITATION_ERROR);
        }
    }

    @Override
    public PageVO<InvitationVO> getInvitationList(String compid, String buildCode, String useStatus,
                                                  String createUser, String beginTime,
                                                  String endTime, Integer page, Integer pageSize) {
        PageVO<InvitationVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize, "create_time Desc");
        List<InvitationVO> vehicleWorkloadSummaryVOS =
                constructionMapper.getInvitationList(compid, buildCode, useStatus, createUser,
                        beginTime, endTime);
        for (InvitationVO invitationVO : vehicleWorkloadSummaryVOS) {
            Integer uid = Integer.valueOf(invitationVO.getCreateuser());
            String username = checkTokenIsNormal(uid);
            invitationVO.setCreateuser(username);
            invitationVO.setCreatetime(invitationVO.getCreatetime().substring(0,16));
        }
        PageInfo<InvitationVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public void invalidInvitationCode(String contractUID,String contractDetailCode, String buildInvitationCode) throws ErpException {
        try {
            int useStatus = 2;
            constructionMapper.updateUseStatus(contractUID,contractDetailCode, buildInvitationCode, useStatus);
        } catch (Exception e) {
            throw new ErpException(ErrEumn.ADJUNCT_UPDATE_ERROR);
        }
    }


    @Override
    @Transactional
    public void saveInvitation(String buildId, String buildInvitationCode) throws ErpException {
        //获取此邀请码绑定的合同集合

        List<InvitationVO> invitationVOS = constructionMapper.selectInvitation(buildInvitationCode);
        if (invitationVOS != null && invitationVOS.size() > 0) {
            for (InvitationVO invitationVO : invitationVOS) {
                //查询此施工单位是否绑定过此合同
                bdBindVO bdBindVO = constructionMapper.selectCompid(invitationVO.getContractDetailCode(),invitationVO.getContractUID(), buildId);
                if (bdBindVO == null) {
                    //说明此用户没有绑定过此合同
                    if (Integer.parseInt(invitationVO.getUsestatus()) == 0) {
                        int usestatus = 1;
                        String compid = invitationVO.getCompid();
                        String ccontractCode = invitationVO.getContractDetailCode();
                        String contractUID = invitationVO.getContractUID();
                        //修改邀请码的使用状态为已使用
                        constructionMapper.updateUseStatus(contractUID,ccontractCode, buildInvitationCode, usestatus);
                        //给此用户绑定合同
                        constructionMapper.saveInvitation(buildId, compid, ccontractCode,contractUID);
                    } else if (Integer.parseInt(invitationVO.getUsestatus()) == 1) {
                        throw new ErpException(ErrEumn.INVITATION_USESTATUS_EXIST);
                    } else {
                        throw new ErpException(ErrEumn.INVITATION_USESTATUS_VOID);
                    }
                }
            }
        } else {
            throw new ErpException(ErrEumn.INVITATION_NULL);
        }

    }

    @Override
    public Map<String, Boolean> checkBind(String buildId) {
        Map<String, Boolean> map = new HashMap<>();
        List<bdBindVO> bdBindVOs = constructionMapper.checkBind(buildId);
        if (bdBindVOs != null && bdBindVOs.size() > 0) {
            map.put("isBind", true);
        } else {
            map.put("isBind", false);
        }
        return map;
    }


    /**
     * 根据用户id查询用户名称
     *
     * @param uid 用户id
     * @return 返回是否通过
     */
    private String checkTokenIsNormal(Integer uid) {
        String baseUrl = "";
        baseUrl = url + "/user/getUser";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        Header[] headers = HttpHeader.custom()
                .other("version", "1")
                .build();
        //插件式配置请求参数（网址、请求参数、编码、client）
        HttpConfig config = HttpConfig.custom()
                .headers(headers)
                .url(baseUrl)
                .map(map)
                .encoding("utf-8")
                .inenc("utf-8");
        //使用方式：
        String pass = "";
        try {
            String result = HttpClientUtil.post(config);
            String username = JSONObject.parseObject(JSONObject.parseObject(result).getString("data"))
                    .getString("username");
            log.info("【user】username={}", username);
            pass = username;
            if (pass.isEmpty()) {
                log.warn("【username名称】username 查询失败,username={}", username);
            }
        } catch (HttpProcessException e) {
            log.warn("【username名称】请求权限服务失败");

            e.printStackTrace();
        }
        baseUrl = "";
        return pass;
    }

    /**
     * 根据企业id查询企业名称
     *
     * @param eid 企业id
     * @return 返回是否通过
     */
    private String getEnterprise(int eid) {
        String baseUrl = "";
        baseUrl = url + "/enterprise/getEnterprise";
        Map<String, Object> map = new HashMap<>();
        map.put("eid", eid);
        Header[] headers = HttpHeader.custom()
                .other("version", "1")
                .build();
        //插件式配置请求参数（网址、请求参数、编码、client）
        HttpConfig config = HttpConfig.custom()
                .headers(headers)
                .url(baseUrl)
                .map(map)
                .encoding("utf-8")
                .inenc("utf-8");
        //使用方式：
        String pass = "";
        try {
            String result = HttpClientUtil.post(config);
            String epShortName = JSONObject.parseObject(JSONObject.parseObject(result).getString("data"))
                    .getString("epShortName");
            log.info("【epShortName】epShortName={}", epShortName);
            pass = epShortName;
            if (pass.isEmpty()) {
                log.warn("【epShortName名称】epShortName 查询失败,epShortName={}", epShortName);
            }
        } catch (HttpProcessException e) {
            log.warn("【epShortName名称】请求权限服务失败");

            e.printStackTrace();
        }
        baseUrl = "";
        return pass;
    }
}
