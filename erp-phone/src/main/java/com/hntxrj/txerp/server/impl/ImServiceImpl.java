/*
这是浩然写的消息，因为idea报错，所以把整个类全部注释了；
这个类没有被调用，代码可以为以后写消息功能当作参考，所以没有删除，暂时注释掉
package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.util.TLSSigAPIv2;
import com.hntxrj.txerp.timsdk.ImService;
//import com.hntxrj.txerp.timsdk.po.UserStateResult;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;

@Service
@Slf4j
public class ImServiceImpl implements ImService {

    private TLSSigAPIv2 tlsSigAPIv2;

    private final static String baseUrl = "https://console.tim.qq.com/";
    public static final MediaType _JSON = MediaType.parse("application/json; charset=utf-8");

    private String urlParams = "?sdkappid={{sdkappid}}&identifier=system&usersig={{usersig}}&random={{random}}&contenttype=json";


//    @Autowired
//    public ImServiceImpl(@Value("${app.timsdk.sdkAppID}") Integer sdkAppId, @Value("${app.timsdk.key}") String key) {
//        tlsSigAPIv2 = new TLSSigAPIv2(sdkAppId, key);
//        urlParams = urlParams
//                .replace("{{sdkappid}}", String.valueOf(sdkAppId))
//                .replace("{{usersig}}", tlsSigAPIv2.genSig("system", (365 * 24 * 60 * 60)))
//                .replace("{{random}}", String.valueOf(new Random().nextInt()));
//    }

    @Override
    public Boolean accountImport(String identifier, String nick, String faceUrl, Integer type) {
        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        json.put("Identifier", identifier);
        json.put("Nick", nick);
        json.put("FaceUrl", faceUrl);
        json.put("Type", type);

        RequestBody requestBody = RequestBody.create(_JSON, JSON.toJSONBytes(json));
        Request request = new Request.Builder()
                .url(baseUrl + "v4/im_open_login_svc/account_import" + urlParams)
                .post(requestBody)
                .build();
        ResponseBody responseBody = null;
        JSONObject resultJSON = null;
        try {
            Response response = client.newCall(request).execute();
            responseBody = response.body();
            if (responseBody != null) {
                String result = responseBody.string();
                resultJSON = JSONObject.parseObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("请求失败!");
            return false;
        } finally {
            if (responseBody != null) {
                responseBody.close();
            }
        }
        if (resultJSON != null && resultJSON.getInteger("ErrorCode") == 0) {
            return true;
        }


        if (resultJSON != null) {
            // 打印tim rest api 返回的错误信息
            log.error("【tim sdk】error:{}", resultJSON.getString("ErrorInfo"));
        }
        return false;

    }

//    @Override
//    public UserStateResult queryState(String[] accounts) {
//
//        OkHttpClient client = new OkHttpClient();
//
//        JSONObject json = new JSONObject();
//        json.put("To_Account", JSONArray.parseArray(JSON.toJSONString(accounts)));
//
//        RequestBody requestBody = RequestBody.create(_JSON, JSON.toJSONBytes(json));
//        Request request = new Request.Builder()
//                .url(baseUrl + "v4/openim/querystate" + urlParams)
//                .post(requestBody)
//                .build();
//        ResponseBody responseBody = null;
//        UserStateResult resultUserStates = null;
//        try {
//            Response response = client.newCall(request).execute();
//            responseBody = response.body();
//            if (responseBody != null) {
//                String result = responseBody.string();
//                resultUserStates = JSONObject.parseObject(result, UserStateResult.class);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            log.error("请求失败!");
//            return null;
//        } finally {
//            if (responseBody != null) {
//                responseBody.close();
//            }
//        }
//        if (resultUserStates != null && resultUserStates.getErrorCode() == 0) {
//            return resultUserStates;
//        }
//
//
//        if (resultUserStates != null) {
//            // 打印tim rest api 返回的错误信息
//            log.error("【tim sdk】error:{}", resultUserStates.getErrorInfo());
//        }
//        return null;
//    }

    @Override
    public void sendMsg() {
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
        obj.put("SyncOtherMachine", 2);
        obj.put("To_Account", "zzlhr");
        obj.put("MsgLifeTime", 60);
        obj.put("MsgRandom", 1287657);
        obj.put("MsgTimeStamp", 1557387418);
        JSONObject o = new JSONObject();
        o.put("MsgType", "TIMTextElem");
        JSONObject oo = new JSONObject();
        oo.put("Text", "您有一个新的任务单需要审核，等待您处理.");
        o.put("MsgContent", oo);
        arr.add(o);
        obj.put("MsgBody", arr);

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(_JSON, JSON.toJSONBytes(obj));
        Request request = new Request.Builder()
                .url(baseUrl + "v4/openim/sendmsg" + urlParams)
                .post(requestBody)
                .build();
        ResponseBody responseBody = null;
        try {
            Response response = client.newCall(request).execute();
            responseBody = response.body();
            if (responseBody != null) {
                String result = responseBody.string();
                System.out.println(result);
//                resultUserStates = JSONObject.parseObject(result, UserStateResult.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("请求失败!");
        } finally {
            if (responseBody != null) {
                responseBody.close();
            }
        }
//        return null;
    }
}*/
