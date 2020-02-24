package com.hntxrj.txerp.im;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.core.util.TLSSigAPIv2;
import com.hntxrj.txerp.vo.FriendsVO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.Header;
import org.json.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
@Slf4j
public class FriendServiceImpl implements FriendService {
    private static final MediaType _JSON = MediaType.parse("application/json; charset=utf-8");
    private TLSSigAPIv2 tlsSigAPIv2;
    private String urlParams = "?sdkappid={{sdkappid}}&identifier=system&usersig={{usersig}}&random={{random}}" +
            "&contenttype=json";


    @Value("${app.cloud.CommunicationUrl}")
    private String url;
    @Value("${app.cloud.host}")
    private String erpurl;

    @Autowired
    public FriendServiceImpl(@Value("${app.timsdk.sdkAppID}") Integer sdkAppId, @Value("${app.timsdk.key}") String key) {
        tlsSigAPIv2 = new TLSSigAPIv2(sdkAppId, key);
        urlParams = urlParams
                .replace("{{sdkappid}}", String.valueOf(sdkAppId))
                .replace("{{usersig}}", tlsSigAPIv2.genSig("system", (365 * 24 * 60 * 60)))
                .replace("{{random}}", String.valueOf(new Random().nextInt()));
    }

    /**
     * 添加好友
     * 添加好友，支持批量添加好友。
     *
     * @param userID  当前用户
     * @param friends 添加的好友
     * @return
     */
    @Override
    public Boolean friendAdd(String userID, List<FriendsVO> friends) throws ErpException {
        if ("".equals(userID) && null == userID) {
            throw new ErpException(ErrEumn.USER_IS_NULL);
        }
        if (friends.size() < 0) {
            throw new ErpException(ErrEumn.ADD_FRIENDS_NULL);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("From_Account", userID);
        JSONArray jsonArray = new JSONArray();

        for (FriendsVO friendsVO : friends) {
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("To_Account", friendsVO.getToAccount());
            if (friendsVO.getRemark()!=null && !"".equals(friendsVO.getRemark())){
                jsonObject2.put("Remark",friendsVO.getRemark());
            }
            if (friendsVO.getGroupName()!=null && !"".equals(friendsVO.getGroupName())){
                jsonObject2.put("GroupName",friendsVO.getGroupName());
            }
            if (friendsVO.getAddSource()==null || "".equals(friendsVO.getAddSource())){
                //为测试通过，默认添加
                jsonObject2.put("AddSource","AddSource_Type_erpPhone");
                throw new ErpException(ErrEumn.ADDSOURCE_IS_NULL);
            }else{
                jsonObject2.put("AddSource",friendsVO.getAddSource());
            }
            if (friendsVO.getAddWording()!=null && !"".equals(friendsVO.getAddWording())){
                jsonObject2.put("AddWording",friendsVO.getAddWording());
            }
            if (friendsVO.getAddType()!=null && !"".equals(friendsVO.getAddType())){
              //加好友方式（默认双向加好友方式)
                jsonObject2.put("AddType","Add_Type_Both");
            }else {
                jsonObject2.put("AddType",friendsVO.getAddType());
            }
            if (friendsVO.getForceAddFlags()==1){
                jsonObject2.put("ForceAddFlags",1);
            }
            if (friendsVO.getForceAddFlags()==0){
                jsonObject2.put("ForceAddFlags",0);
            }
            jsonArray.put(jsonObject2);
        }
        jsonObject.put("AddFriendItem", jsonArray);
        RequestBody requestBody = RequestBody.create(_JSON, com.alibaba.fastjson.JSON.toJSONBytes(jsonObject));
        Request request = new Request.Builder()
                .url(url + "/sns/friend_add" + urlParams)
                .post(requestBody)
                .build();
        ResponseBody responseBody = null;
        JSONObject resultJSON = null;
        OkHttpClient client = new OkHttpClient();
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

    /**
     * 导入好友
     * 支持批量导入单向好友。
     * 往同一个用户导入好友时建议采用批量导入的方式，避免并发写导致的写冲突。
     *
     * @param userID 当前用户
     * @param eid    企业代号
     * @return 接口返回参数
     */
    @Override
    public Boolean friendImport(String userID, Integer eid) {
        //创建json,保存该用户ID
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("From_Account", userID);

        //查询这个企业下所有用户。
        List<String> objects = getuserAll(eid);
        JSONArray jsonArray = new JSONArray();

        //把查询的用户拼接到参数中，
        for (String s : objects) {
            //判断不导入自己为好友
            if (!s.equals(userID)) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("To_Account", s);
                jsonObject2.put("AddSource", "AddSource_Type_erpPhone");
                jsonArray.put(jsonObject2);
            }
        }

        RequestBody requestBody = RequestBody.create(_JSON, com.alibaba.fastjson.JSON.toJSONBytes(jsonObject));
        Request request = new Request.Builder()
                .url(url + "/sns/friend_import" + urlParams)
                .post(requestBody)
                .build();
        ResponseBody responseBody = null;
        JSONObject resultJSON = null;
        OkHttpClient client = new OkHttpClient();
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

    /**
     * 删除好友
     * 删除好友，支持单向删除好友和双向删除好友。
     * @param userID 当前用户
     * @param friends  选择删除的好友ID
     * @param deleteType 删除模式 Delete_Type_Single 单向删除/Delete_Type_Both 双向删除
     * @return 接口返回参数
     */
    @Override
    public Boolean friendDelete(String userID, List<FriendsVO> friends, String deleteType) throws ErpException {
        if ("".equals(userID) || null == userID) {
            throw new ErpException(ErrEumn.USER_IS_NULL);
        }
        if (friends.size() == 0) {
            throw new ErpException(ErrEumn.DEL_FRIENDS_NULL);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("From_Account", userID);
        JSONArray jsonArray = new JSONArray();
        for (FriendsVO friendsVO : friends) {
            if (friendsVO.getToAccount() != null && !"".equals(friendsVO.getToAccount())) {
                jsonArray.put(friendsVO.getToAccount());
            }
        }
        jsonObject.put("To_Account",jsonArray);
        //判断删除模式
        if (!"".equals(deleteType) && null!=deleteType){
            //单向删除
            if (deleteType.equals("Delete_Type_Single")){
                jsonObject.put("DeleteType","Delete_Type_Single");
            }else {
                //双向删除
                jsonObject.put("DeleteType","Delete_Type_Both");
            }
        }

        RequestBody requestBody = RequestBody.create(_JSON, com.alibaba.fastjson.JSON.toJSONBytes(jsonObject));
        Request request = new Request.Builder()
                .url(url + "/sns/friend_import" + urlParams)
                .post(requestBody)
                .build();
        ResponseBody responseBody = null;
        JSONObject resultJSON = null;
        OkHttpClient client = new OkHttpClient();
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

    /**
     * 根据企业id查询该企业所有用户
     *
     * @param eid 企业id
     * @return 返回是否通过
     */
    private List<String> getuserAll(int eid) {
        String baseUrl = "";
        baseUrl = erpurl + "/user/userAll";
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
        List<String> list = new ArrayList<>();
        try {
            String result = HttpClientUtil.post(config);
            com.alibaba.fastjson.JSONArray array = JSONObject.parseObject(result).getJSONArray("data");

            for (Object o : array) {
                String uid = JSONObject.parseObject(JSONObject.toJSONString(o)).getString("uid");
                list.add(uid);
            }
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
        baseUrl = "";
        return list;
    }

}
