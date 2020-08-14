package com.hntxrj.txerp.rabbitmq;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.vo.MessagePushVO;
import com.hntxrj.txerp.vo.RecipientVO;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;


/**
 * 极光推送工具类
 */
@Service
public class JPushUtil {


    @Value("${app.jPush.erpPhone.appKey}")
    private String erpPhoneAppKey;

    @Value("${app.jPush.erpPhone.masterSecret}")
    private String erpPHoneMasterSecret;

    @Value("${app.jPush.erpDriver.appKey}")
    private String erpDriverAppKey;

    @Value("${app.jPush.erpDriver.masterSecret}")
    private String erpDriverMasterSecret;

    @Value("${app.jPush.url}")
    private String url;

    private final Logger log = LoggerFactory.getLogger(JPushUtil.class);


    //手机erp的消息推送
    public void erpPhoneMessagePush(MessagePushVO messagePushVO, List<RecipientVO> aliasList) throws ErpException {

        if (messagePushVO == null) {
            throw new ErpException(ErrEumn.MESSAGE_PUSH_IS_NULL);
        }
        //根据appKey和masterSecret获取项目的authorization
        String authorization = getErpPhoneAuthorization();

        //获取请求极光消息推送的json类型参数
        JSONObject pushParam = getErpPhonePushParam(messagePushVO, aliasList);


        //用okhttp请求,向极光推送发送消息
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(pushParam));
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", authorization)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            if (response.body() != null) {
                log.info("推送消息成功:{}", response.body().string());
                response.body().close();
            }
            response.close();
        } catch (IOException e) {
            log.error("推送消息失败", e.getMessage());
            e.printStackTrace();
        }
    }


    //司机App的消息推送
    public void erpDriverMessagePush(String compid, MessagePushVO messagePushVO, List<String> aliasList) throws ErpException {

        if (messagePushVO == null) {
            throw new ErpException(ErrEumn.MESSAGE_PUSH_IS_NULL);
        }
        //根据appKey和masterSecret获取项目的authorization
        String authorization = getErpDriverAuthorization();

        //获取请求极光消息推送的json类型参数
        JSONObject pushParam = getErpDriverPushParam(compid, messagePushVO, aliasList);


        //用okhttp请求,向极光推送发送消息
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(pushParam));
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", authorization)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            if (response.body() != null) {
                log.info("推送消息成功:{}", response.body().string());
                response.body().close();
            }
            response.close();
        } catch (IOException e) {
            log.error("推送消息失败", e.getMessage());
            e.printStackTrace();
        }
    }

    private String getErpPhoneAuthorization() {
        String key = erpPhoneAppKey + ":" + erpPHoneMasterSecret;
        String encode = Base64.getEncoder().encodeToString(key.getBytes());
        return "Basic " + encode;
    }

    private String getErpDriverAuthorization() {
        String key = erpDriverAppKey + ":" + erpDriverMasterSecret;
        String encode = Base64.getEncoder().encodeToString(key.getBytes());
        return "Basic " + encode;
    }

    private JSONObject getErpPhonePushParam(MessagePushVO messagePushVO, List<RecipientVO> aliasList) {

        //扩展字段 用户可以自定义key/value
        JSONObject extrasJson = new JSONObject();

        //ios Json对象 包括ios相关配置
        JSONObject iosJson = new JSONObject();

        //android Json对象 包括android相关配置
        JSONObject androidJson = new JSONObject();

        //推送参数  消息保存时长等设定
        JSONObject optionsJson = new JSONObject();

        //消息推送内容
        JSONObject notificationJson = new JSONObject();

        //推送目标 本项目针对别名推送
        JSONObject audienceJson = new JSONObject();

        //别名数组
        JSONArray aliasArray = new JSONArray();

        //最终请求的json参数
        JSONObject jsonObject = new JSONObject();

        log.info("需要推送的人列表:{}", aliasArray);
        for (RecipientVO recipientVO : aliasList) {
            aliasArray.add(recipientVO.getPhone() + messagePushVO.getCompid());
        }

        extrasJson.put("compid", messagePushVO.getCompid());
        extrasJson.put("typeId", messagePushVO.getTypeId());
        extrasJson.put("message", messagePushVO.getMessage());
        extrasJson.put("taskId", messagePushVO.getTaskId());
        extrasJson.put("contractUid", messagePushVO.getContractUid());
        extrasJson.put("contractDetailCode", messagePushVO.getContractDetailCode());
        extrasJson.put("stirId", messagePushVO.getStirId());

        androidJson.put("alert", messagePushVO.getMessage());
        androidJson.put("title", "消息推送");
        androidJson.put("extras", extrasJson);

        iosJson.put("alert", messagePushVO.getMessage());
        iosJson.put("sound", "sound.caf");
        iosJson.put("badge", 1);
        iosJson.put("extras", extrasJson);

        notificationJson.put("android", androidJson);
        notificationJson.put("ios", iosJson);

        log.info("发消息时设置的信息接收人:{}", aliasArray);
        audienceJson.put("alias", aliasArray);

        optionsJson.put("time_to_live", 60);
        optionsJson.put("apns_production", true);
        optionsJson.put("apns_collapse_id", "jiguang_test_201706011100");

        jsonObject.put("platform", "all");
        jsonObject.put("audience", audienceJson);
        jsonObject.put("notification", notificationJson);
        jsonObject.put("options", optionsJson);
        return jsonObject;
    }


    private JSONObject getErpDriverPushParam(String compid, MessagePushVO messagePushVO, List<String> aliasList) {

        //扩展字段 用户可以自定义key/value
        JSONObject extrasJson = new JSONObject();

        //ios Json对象 包括ios相关配置
        JSONObject iosJson = new JSONObject();

        //android Json对象 包括android相关配置
        JSONObject androidJson = new JSONObject();

        //推送参数  消息保存时长等设定
        JSONObject optionsJson = new JSONObject();

        //消息推送内容
        JSONObject notificationJson = new JSONObject();

        //推送目标 本项目针对别名推送
        JSONObject audienceJson = new JSONObject();

        //别名数组
        JSONArray aliasArray = new JSONArray();

        //最终请求的json参数
        JSONObject jsonObject = new JSONObject();
        for (String driverCode : aliasList) {
            aliasArray.add("waitCar" + compid + driverCode);
        }

        extrasJson.put("message", messagePushVO.getMessage());

        androidJson.put("alert", messagePushVO.getMessage());
        androidJson.put("title", "消息推送");
        androidJson.put("extras", extrasJson);

        iosJson.put("alert", messagePushVO.getMessage());
        iosJson.put("sound", "sound.caf");
        iosJson.put("badge", 1);
        iosJson.put("extras", extrasJson);

        notificationJson.put("android", androidJson);
        notificationJson.put("ios", iosJson);

        audienceJson.put("alias", aliasArray);

        optionsJson.put("time_to_live", 60);
        optionsJson.put("apns_production", true);
        optionsJson.put("apns_collapse_id", "jiguang_test_201706011100");

        jsonObject.put("platform", "all");
        jsonObject.put("audience", audienceJson);
        jsonObject.put("notification", notificationJson);
        jsonObject.put("options", optionsJson);
        return jsonObject;
    }
}