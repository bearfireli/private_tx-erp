package com.hntxrj.txerp.im.impl;

import com.alibaba.fastjson.JSON;
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
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j
public class AccountServiceImpl implements AccountService {
    String IMBaseUrl = "https://console.tim.qq.com/v4/im_open_login_svc/";
    Integer sdkAppId = ImBaseData.sdkAppId;
    String identifier = ImBaseData.identifier;
    String userSig = ImBaseData.getUserSig();
    Long random = ImBaseData.getRandom();

    @Value("${app.cloud.host}")
    private String erpurl;

    @Override
    public Object accountImport(IMUserVO imUserVO) throws ErpException {

        JSONObject jsonObject = new JSONObject();
        if (imUserVO == null) {
            throw new ErpException(ErrEumn.NEWS_ERROR);
        }
        jsonObject.put("Identifier", imUserVO.getIdentifier());
        jsonObject.put("Nick", imUserVO.getNick());
        jsonObject.put("FaceUrl", imUserVO.getFaceUrl());



            String result = getUrl("account_import", jsonObject);
            return result;



    }

    @Override
    public Object multiAccountImport() throws ErpException {
        String result = "";
        int i = 1;
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<String> userList = getUserAll();
        for (String uid : userList) {
            jsonArray.put(uid);
            i++;
            //批量导入用户一次最多只能导入100个。
            if (i >= 100) {
                //调用腾讯云即时通讯IM批量导入用户的接口
                jsonObject.put("Accounts", jsonArray);
                result=getUrl("multiaccount_import", jsonObject);
                jsonArray = new JSONArray();
                i=1;
            }

        }
        if (jsonArray .length()!=0) {
            jsonObject.put("Accounts", jsonArray);
            result=getUrl("multiaccount_import", jsonObject);
        }
        return result;
    }

    @Override
    public Object accountDelete(List<IMUserVO> imUserVOList) throws ErpException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (IMUserVO imUserVO : imUserVOList) {
            JSONObject json = new JSONObject();
            json.put("UserID", imUserVO.getIdentifier());
            jsonArray.put(json);
        }
        jsonObject.put("DeleteItem", jsonArray);
        String result = getUrl("account_delete", jsonObject);
        return result;
    }

    @Override
    public Object accountCheck(List<IMUserVO> imUserVOList) throws ErpException {


        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (IMUserVO imUserVO : imUserVOList) {
            JSONObject json = new JSONObject();
            json.put("UserID", imUserVO.getIdentifier());
            jsonArray.put(json);
        }
        jsonObject.put("CheckItem", jsonArray);
        String result = getUrl("account_check", jsonObject);
        return result;
    }

    @Override
    public Object kick(IMUserVO imUserVO) throws ErpException {
        JSONObject jsonObject = new JSONObject();
        if (imUserVO == null) {
            throw new ErpException(ErrEumn.NEWS_ERROR);
        }
        jsonObject.put("Identifier", imUserVO.getIdentifier());



        String result = getUrl("kick", jsonObject);
        return result;
    }

    private String getUrl(String method,JSONObject jsonObject) throws ErpException {
        //拼接请求腾讯云即时通讯IM接口的url路径
        StringBuilder url=new StringBuilder(IMBaseUrl);
        url.append(method).append("?sdkappid=").append(sdkAppId).append("&identifier=").append(identifier)
                .append("&usersig=").append(userSig).append("&random=").append(random);


        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("contenttype", String.valueOf(jsonObject))
                .build();
        Request request = new Request.Builder()
                .url(url.toString())
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            String str = response.body().string();

            System.out.println(str);
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.NEWS_ERROR);
        }
    }





    /**
     * 查询所有企业所有用户
     *
     * @return 返回是否通过
     */
    private List<String> getUserAll() {
        String baseUrl = "";
        baseUrl = erpurl + "/user/selectAllUser";
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
            com.alibaba.fastjson.JSONArray array = com.alibaba.fastjson.JSONObject.parseObject(result).getJSONArray("data");

            for (Object o : array) {
                String uid = com.alibaba.fastjson.JSONObject.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(o)).getString("uid");
                list.add(uid);
            }
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
        return list;
    }
}
