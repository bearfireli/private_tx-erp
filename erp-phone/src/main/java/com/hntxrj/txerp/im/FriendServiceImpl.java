package com.hntxrj.txerp.im;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.vo.FriendsVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class FriendServiceImpl implements FriendService {

    @Value("${app.cloud.CommunicationUrl}")
    private String url;
    @Value("${app.cloud.host}")
    private String erpurl;
    @Override
    public String friendAdd(String userID, List<FriendsVO> friends) throws ErpException {
        if (!"".equals(userID) && null != userID) {
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
        String friendAddUrl ="";
        friendAddUrl = url + "/sns/friend_add";
        Map<String, Object> map = new HashMap<>();
        map.put("contenttype", jsonObject);
        Header[] headers = HttpHeader.custom()
                .other("sdkappid", ImBaseData.sdkAppId.toString())
                .other("identifier",ImBaseData.identifier)
                .other("usersig",ImBaseData.getUserSig())
                .other("random", String.valueOf(ImBaseData.getRandom()))
//                .other("contenttype", String.valueOf(jsonObject))
                .build();
        //插件式配置请求参数（网址、请求参数、编码、client）
        HttpConfig config = HttpConfig.custom()
                .headers(headers)
                .url(friendAddUrl)
                .encoding("utf-8")
                .map(map)
                .inenc("utf-8");
        String result = null;
        try {
            result = HttpClientUtil.post(config);
            
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }

        
        return result;
    }

    /*
     * 导入好友
     * */
    @Override
    public String friendImport(String userID, Integer eid) throws IOException {
        String friendImportUrl;
        friendImportUrl = url + "/sns/friend_import"+ "?" + "sdkappid=" + ImBaseData.sdkAppId + "&" + "identifier=" + ImBaseData.identifier + "&" +
                "usersig=" + ImBaseData.getUserSig() + "&" + "random=" + ImBaseData.getRandom();

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
        jsonObject.put("AddFriendItem", jsonArray);

        //拼接get请求地址。https://console.tim.qq.com/v4/sns/friend_import?sdkappid=88888888&identifier=admin&
        // usersig=xxx&random=99999999&contenttype=json
//        HttpPost httpPost = new HttpPost(friendImportUrl);
//        CloseableHttpClient client = HttpClients.createDefault();
//        StringEntity entity = new StringEntity(jsonObject.toString(),"utf-8");//解决中文乱码问题
//        entity.setContentEncoding("UTF-8");
//        entity.setContentType("application/json");
//        httpPost.setEntity(entity);
//        String respContent = null;
//        HttpResponse resp = client.execute(httpPost);
//        if(resp.getStatusLine().getStatusCode() == 200) {
//            HttpEntity he = resp.getEntity();
//            respContent = EntityUtils.toString(he,"UTF-8");
//        }
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = new FormBody.Builder()
////               .add("methodName", methodName)
////               .add("functionName", functionName)
//               .add("contenttype", String.valueOf(jsonObject))
////               .add("compid", compid)
//                .build();
//        Request request = new Request.Builder()
//                .url(friendImportUrl)
//                .post(body)
//                .build();
//
//        try {
//            client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        HttpClient httpClient = new DefaultHttpClient();//httpClient(org.apache.http.client.HttpClient)
        HttpPost httpPost = new HttpPost(friendImportUrl);//post(org.apache.http.client.methods.HttpPost)
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");//请求头
        StringEntity stringEntity = new StringEntity(String.valueOf(jsonObject),"UTF-8");//(org.apache.http.entity.StringEntity)
        httpPost.setEntity(stringEntity);//请求主体
        HttpResponse httpResponse = httpClient.execute(httpPost);//发送请求(org.apache.http.HttpResponse)
        HttpEntity httpEntity = httpResponse.getEntity();//获取请求返回体(org.apache.http.HttpEntity)
        String backResult =EntityUtils.toString(httpEntity,"UTF-8");//请求返回结果(org.apache.http.util.EntityUtils)
        if(httpResponse != null){
        try{
            EntityUtils.consume(httpResponse.getEntity());
        }catch(IOException e){
            e.printStackTrace();
        }
        }//释放资源
        return backResult;
    }

    @Override
    public String friendDelete(String userID, List<FriendsVO> friends, String deleteType) throws ErpException {
        if (!"".equals(userID) && null != userID) {
            throw new ErpException(ErrEumn.USER_IS_NULL);
        }
        if (friends.size() < 0) {
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

        String friendDeleteUrl ="";
        friendDeleteUrl = url + "/sns/friend_delete";
        Header[] headers = HttpHeader.custom()
                .other("sdkappid", ImBaseData.sdkAppId.toString())
                .other("identifier",ImBaseData.identifier)
                .other("usersig",ImBaseData.getUserSig())
                .other("random", String.valueOf(ImBaseData.getRandom()))
                .other("contenttype", String.valueOf(jsonObject))
                .build();
        //插件式配置请求参数（网址、请求参数、编码、client）
        HttpConfig config = HttpConfig.custom()
                .headers(headers)
                .url(friendDeleteUrl)
                .encoding("utf-8")
                .inenc("utf-8");
        String result = null;
        try {
            result = HttpClientUtil.get(config);

        } catch (HttpProcessException e) {
            e.printStackTrace();
        }


        return result;
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
