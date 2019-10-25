package com.hntxrj.server.impl;

import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.exception.ErpException;
import com.hntxrj.exception.ErrEumn;
import com.hntxrj.mapper.ConstructionMapper;
import com.hntxrj.server.ConstructionService;
import com.hntxrj.vo.InvitationVO;
import com.hntxrj.vo.PageVO;
import com.hntxrj.vo.bdBindVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qyb
 *  ConstructionServiceImpl
 *  TODO
 *  19-7-25 下午4:08
 **/
@Service
@Slf4j
public class ConstructionServiceImpl implements ConstructionService {

    @Value("${app.cloud.host}")
    private String url;

    private final ConstructionMapper constructionMapper;


    public ConstructionServiceImpl(ConstructionMapper constructionMapper) {
        this.constructionMapper = constructionMapper;
    }

    @Override
    public InvitationVO getInvitationCode(String compid, Integer opid, String buildCode) throws ErpException {
        String build_Invitation_Code = UUID.randomUUID().toString().replace("-", "");
        Date date = new Date();
        Integer use_Status = 0;
        try {
            constructionMapper.getInvitationCode(build_Invitation_Code, compid, buildCode, use_Status, opid, date);
            InvitationVO invitationVO = new InvitationVO();
            invitationVO.setBuildinvitationcode(build_Invitation_Code);
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
        }
        PageInfo<InvitationVO> pageInfo = new PageInfo<>(vehicleWorkloadSummaryVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public void updateUseStatus(String compid, String buildInvitationCode) throws ErpException {
        try {
            int useStatus = 2;
            constructionMapper.updateUseStatus(compid, buildInvitationCode, useStatus);
        } catch (Exception e) {
            throw new ErpException(ErrEumn.ADJUNCT_UPDATE_ERROR);
        }
    }


    @Override
    @Transactional
    public void saveInvitation(String buildId, String buildInvitationCode) throws ErpException {
        InvitationVO invitationVO = constructionMapper.selectInvitation(buildInvitationCode);
        if (invitationVO != null) {
            bdBindVO bdBindVOS = constructionMapper.selectCompid(invitationVO.getCompid(),buildId);
            if (bdBindVOS == null) {
                if (Integer.parseInt(invitationVO.getUsestatus()) == 0) {
                    int usestatus = 1;
                    String compid = invitationVO.getCompid();
                    String buildCode = invitationVO.getBuildcode();
                    constructionMapper.updateUseStatus(compid, buildInvitationCode, usestatus);
                    constructionMapper.saveInvitation(buildId, compid, buildCode);
                } else if (Integer.parseInt(invitationVO.getUsestatus()) == 1) {
                    throw new ErpException(ErrEumn.INVITATION_USESTATUS_EXIST);
                } else {
                    throw new ErpException(ErrEumn.INVITATION_USESTATUS_VOID);
                }
            }else{
                throw new ErpException(ErrEumn.INVITATION_COMPID_VOID);
            }
        } else {
            throw new ErpException(ErrEumn.INVITATION_NULL);
        }

    }

    @Override
    public PageVO<bdBindVO> selectBind(String buildId, Integer page, Integer pageSize) {
        PageVO<bdBindVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize);
        List<bdBindVO> bdBindVOS =
                constructionMapper.selectBind(buildId);
        //去重
        bdBindVOS = bdBindVOS.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()
                -> new TreeSet<>(Comparator.comparing(bdBindVO::getCompid))), ArrayList::new));

        for (bdBindVO b : bdBindVOS) {
            String epShortName = getEnterprise(Integer.parseInt(b.getCompid()));
            b.setEpShortName(epShortName);
        }
        PageInfo<bdBindVO> pageInfo = new PageInfo<>(bdBindVOS);
        pageVO.format(pageInfo);
        return pageVO;
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
