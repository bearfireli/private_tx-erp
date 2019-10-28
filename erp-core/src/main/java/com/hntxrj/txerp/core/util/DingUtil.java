package com.hntxrj.txerp.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.request.OapiWorkrecordAddRequest;
import com.dingtalk.api.request.OapiWorkrecordUpdateRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.dingtalk.api.response.OapiWorkrecordAddResponse;
import com.dingtalk.api.response.OapiWorkrecordUpdateResponse;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.taobao.api.ApiException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 钉钉功能封装
 */
public class DingUtil {

    private static String corpid = "ding8cb59c9c2f98d51035c2f4657eb6378f";
    private static String corpSecret = "Yhw5d8SZl3e-3vjP0UHftEwc-A8-YmvC65hZ4NJALRl8aOS1eIxa8GTto-WLEPPM";
    private static String accessToken = "";
    private static Date lastGetTokenTime = new Date(0);

    public static String getAccessToken() throws IOException {
        // need get new token?
        if (new Date().getTime() - lastGetTokenTime.getTime() >= 7200L * 1000L) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://oapi.dingtalk.com/gettoken?corpid=" + corpid + "&corpsecret=" + corpSecret)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                accessToken = JSONObject.parseObject(response.body().string()).getString("access_token");
            } catch (Exception e) {
                throw new RuntimeException("获取钉钉AccessToken失败");
            }
        }

        return accessToken;

    }

    public static String getUserId(String requestAuthCode) throws ErpException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(requestAuthCode);
        request.setHttpMethod("GET");
        OapiUserGetuserinfoResponse response = null;
        try {
            response = client.execute(request, getAccessToken());
            System.out.println(JSON.toJSONString(response));
        } catch (Exception e) {
            throw new ErpException(ErrEumn.DING_GET_ACCESS_ERROR);
        }
        return response.getUserid();
    }

    /**
     * 添加代办事项
     *
     * @param userId      钉钉userid
     * @param title       待办事项的标题
     * @param url         待办事项的跳转链接
     * @param formItemVos 待办事项表单
     *                    formItemVo.title 表单标题
     *                    formItemVo.content 	表单内容
     * @return record_id 待办事项唯一id，更新待办事项的时候需要用到
     * @throws ErpException
     */
    public static String workrecordAdd(String userId, String title, String url,
                                       List<OapiWorkrecordAddRequest.FormItemVo> formItemVos) throws ErpException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/workrecord/add");
        OapiWorkrecordAddRequest req = new OapiWorkrecordAddRequest();
        req.setUserid(userId);
        req.setCreateTime(new Date().getTime());
        req.setTitle(title);
        req.setUrl(url);
        req.setFormItemList(formItemVos);
        OapiWorkrecordAddResponse rsp;
        try {
            rsp = client.execute(req, getAccessToken());
            System.out.println("【添加代办事项】:" + rsp);
            String respBody = rsp.getBody();
            JSONObject respJSON = JSONObject.parseObject(respBody);
            System.out.println(respJSON);
            if (respJSON.getInteger("errcode") == 0) {
                // 发送成功
                return respJSON.getString("record_id");
            }
            throw new ErpException(ErrEumn.WORK_RECORD_ADD_ERROR);
        } catch (ApiException | IOException e) {
            System.out.println(e);
            e.printStackTrace();
            throw new ErpException(ErrEumn.WORK_RECORD_ADD_ERROR);
        }
    }

    public static void overWorkrecord(String dingUserId, String recordId) throws ErpException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/workrecord/update");
        OapiWorkrecordUpdateRequest req = new OapiWorkrecordUpdateRequest();
        req.setUserid(dingUserId);
        req.setRecordId(recordId);
        OapiWorkrecordUpdateResponse rsp = null;
        try {
            rsp = client.execute(req, getAccessToken());
        } catch (ApiException | IOException e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.WORK_RECORD_UP_ERROR);
        }
        System.out.println(rsp.getBody());
    }

    public static void sendMessage(String userId, String url, String msgTitle) {
        DingTalkClient client = new DefaultDingTalkClient(
                "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg =
                new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        OapiMessageCorpconversationAsyncsendV2Request request =
                new OapiMessageCorpconversationAsyncsendV2Request();
        request.setUseridList(userId);
        request.setToAllUser(false);

        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
        msg.getActionCard().setTitle(msgTitle);
        msg.getActionCard().setMarkdown("### " + msgTitle);
        msg.getActionCard().setSingleTitle(msgTitle);
        msg.getActionCard().setSingleUrl(url);
        msg.setMsgtype("action_card");
        request.setMsg(msg);
        try {
            OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getAccessToken());
        } catch (ApiException | IOException e) {
            e.printStackTrace();
        }
    }

}
