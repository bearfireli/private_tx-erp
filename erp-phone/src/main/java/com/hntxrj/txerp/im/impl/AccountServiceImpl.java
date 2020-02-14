package com.hntxrj.txerp.im.impl;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.im.AccountService;
import com.hntxrj.txerp.im.ImBaseData;
import lombok.extern.log4j.Log4j;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Log4j
public class AccountServiceImpl implements AccountService {
    String IMBaseUrl = "https://console.tim.qq.com/v4/im_open_login_svc/";
    Integer sdkAppId = ImBaseData.sdkAppId;
    String identifier = ImBaseData.identifier;
    String userSig = ImBaseData.getUserSig();
    Long random = ImBaseData.getRandom();

    @Override
    public Object accountImport() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Identifier", "222");
        jsonObject.put("Nick", "222");
        jsonObject.put("FaceUrl", "http://www.qq.com");


        try {
            Object result = getUrl("account_import", jsonObject);
            return result;

        } catch (ErpException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Object multiAccountImport() {
        return null;
    }

    @Override
    public Object accountDelete() {
        return null;
    }

    @Override
    public Object accountCheck() {


        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("UserID", "111");

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("UserID", "222");

        JSONArray array = new JSONArray();
        array.put(jsonObject1);
        array.put(jsonObject2);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("CheckItem", array);



        try {
            Object result = getUrl("account_check", jsonObject);
            return result;

        } catch (ErpException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object kick() {
        return null;
    }

    private Object getUrl(String method,JSONObject jsonObject) throws ErpException {
        //拼接请求腾讯云即时通讯IM接口的url路径
        StringBuilder url=new StringBuilder(IMBaseUrl);
        url.append(method).append("?sdkappid=").append(sdkAppId).append("&identifier=").append(identifier)
                .append("&usersig=").append(userSig).append("&random=").append(random).append("&contenttype=")
                .append(jsonObject);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url.toString())
                .get()
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
}
