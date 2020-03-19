package com.hntxrj.txerp.im.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.im.AccountService;
import com.hntxrj.txerp.im.ImBaseData;
import com.hntxrj.txerp.vo.IMUserVO;
import lombok.extern.log4j.Log4j;
import okhttp3.*;
import org.apache.http.Header;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j
public class AccountServiceImpl implements AccountService {
    public static final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
    //腾讯云即时通讯API接口路径
    String IMBaseUrl = "https://console.tim.qq.com/v4/";
    String interfaceUrl = "im_open_login_svc/";
    //请求腾讯云需要的标识
    Integer sdkAppId = ImBaseData.sdkAppId;
    String identifier = ImBaseData.identifier;
    String userSig = ImBaseData.getUserSig();
    Long random = ImBaseData.getRandom();

    @Value("${app.cloud.host}")
    private String erpUrl;

    @Override
    public JSONObject accountImport(IMUserVO imUserVO) throws ErpException {

        JSONObject jsonObject = new JSONObject();
        if (imUserVO == null) {
            throw new ErpException(ErrEumn.ACCOUNT_NULL_ERROR);
        }
        jsonObject.put("Identifier", imUserVO.getIdentifier());
        jsonObject.put("Nick", imUserVO.getNick());
        jsonObject.put("FaceUrl", imUserVO.getFaceUrl());


        return requestIMApi(interfaceUrl, "account_import", jsonObject);


    }

    @Override
    public JSONObject multiAccountImport() throws ErpException {
        JSONObject result = new JSONObject();
        int i = 1;
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<String> userList = getUserAll();
        for (String uid : userList) {
            jsonArray.add(uid);
            i++;
            //批量导入用户一次最多只能导入100个。
            if (i >= 100) {
                //调用腾讯云即时通讯IM批量导入用户的接口
                jsonObject.put("Accounts", jsonArray);
                result = requestIMApi(interfaceUrl, "multiaccount_import", jsonObject);
                jsonArray = new JSONArray();
                i = 1;
            }

        }
        //最后一批小于100人的用户导入即时通讯
        if (jsonArray.size() != 0) {
            jsonObject.put("Accounts", jsonArray);
            result = requestIMApi(interfaceUrl, "multiaccount_import", jsonObject);
        }
        return result;
    }

    @Override
    public JSONObject accountDelete(List<String> imUserIDs) throws ErpException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (String imUserID : imUserIDs) {
            JSONObject json = new JSONObject();
            json.put("UserID", imUserID);
            jsonArray.add(json);
        }
        jsonObject.put("DeleteItem", jsonArray);
        return requestIMApi(interfaceUrl, "account_delete", jsonObject);
    }

    @Override
    public JSONObject accountCheck(List<String> imUserIDs) throws ErpException {


        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (String imUserID : imUserIDs) {
            JSONObject json = new JSONObject();
            json.put("UserID", imUserID);
            jsonArray.add(json);
        }
        jsonObject.put("CheckItem", jsonArray);

        return requestIMApi(interfaceUrl, "account_check", jsonObject);
    }

    @Override
    public JSONObject kick(IMUserVO imUserVO) throws ErpException {
        JSONObject jsonObject = new JSONObject();
        if (imUserVO == null) {
            throw new ErpException(ErrEumn.ACCOUNT_NULL_ERROR);
        }
        jsonObject.put("Identifier", imUserVO.getIdentifier());

        return requestIMApi(interfaceUrl, "kick", jsonObject);
    }

    @Override
    public void multiAccountDelete() throws ErpException {
        JSONObject result = new JSONObject();
        int i = 1;

        JSONArray jsonArray = new JSONArray();
        List<String> userList = getUserAll();
        for (String uid : userList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserID", uid);
            jsonArray.add(jsonObject);
            i++;
            if (i >= 100) {
                //调用腾讯云即时通讯IM批量导入用户的接口
                result.put("DeleteItem", jsonArray);
                JSONObject jsonObject1 = requestIMApi(interfaceUrl, "account_delete", result);
                System.out.println(jsonObject1);
                jsonArray = new JSONArray();
                i = 1;
            }
        }
    }

    //请求腾讯云即时通讯IM的api的接口
    public JSONObject requestIMApi(String interfaceUrl, String method, JSONObject jsonObject) throws ErpException {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONBytes(jsonObject));
        //请求腾讯云即时通讯IM的路径
        String url = IMBaseUrl + interfaceUrl + method + "?sdkappid=" + sdkAppId + "&identifier=" + identifier +
                "&usersig=" + userSig + "&random=" + random + "&contenttype=json";
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        JSONObject resultJSON = null;
        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String result = responseBody.string();
                resultJSON = JSONObject.parseObject(result);
            }

            return resultJSON;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.IM_SDK_ERROR);
        }
    }


    /**
     * 查询所有企业所有用户
     *
     * @return 所有用户的id集合
     */
    private List<String> getUserAll() {
        String baseUrl = erpUrl + "/user/selectAllUser";
        Header[] headers = HttpHeader.custom()
                .other("version", "1")
                .build();
        //插件式配置请求参数（网址、请求参数、编码、client）
        HttpConfig config = HttpConfig.custom()
                .headers(headers)
                .url(baseUrl)
                .encoding("utf-8")
                .inenc("utf-8");
        List<String> list = new ArrayList<>();
        try {
            String result = HttpClientUtil.post(config);
            JSONArray array = JSONObject.parseObject(result).getJSONArray("data");

            for (Object o : array) {
                String uid = JSONObject.parseObject(JSONObject.toJSONString(o)).getString("uid");
                list.add(uid);
            }
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
        return list;
    }
}
