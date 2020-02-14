package com.hntxrj.txerp.im;

        import com.arronlong.httpclientutil.HttpClientUtil;
        import com.arronlong.httpclientutil.common.HttpConfig;
        import com.arronlong.httpclientutil.common.HttpHeader;
        import com.arronlong.httpclientutil.exception.HttpProcessException;
        import com.hntxrj.txerp.entity.base.User;
        import lombok.extern.slf4j.Slf4j;
        import okhttp3.FormBody;
        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.RequestBody;
        import org.apache.http.Header;
        import org.json.JSONArray;
        import com.alibaba.fastjson.JSONObject;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.stereotype.Service;

        import java.io.IOException;
        import java.util.*;

@Service
@Slf4j
public class FriendServiceImpl implements FriendService {

    @Value("${app.cloud.friendImUrl}")
    private String url;

    @Override
    public Object friendAdd() {
        return null;
    }

    /*
     * 导入好友
     * */
    @Override
    public JSONArray friendImport(User user,Integer eid) {

        String friendImportUrl;
        friendImportUrl = url + "/friend_import";
        Integer sdkAppId = ImBaseData.sdkAppId;
        String identifier = ImBaseData.identifier;
        String getUserSig = ImBaseData.getUserSig();
        long getRandom = ImBaseData.getRandom();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("From_Account", user.getPhone());

        JSONArray jsonArray = new JSONArray();
        List<Object> objects = getuserAll(eid);
//        for (User user: objects) {
//            JSONObject jsonObject2 = new JSONObject();
//            jsonObject2.put("To_Account",user.getuid);
//        }


        jsonObject.put("AddFriendItem", jsonArray);


        friendImportUrl = friendImportUrl + "?" + "sdkappid=" + sdkAppId + "&" + "identifier=" + identifier + "&" +
                "usersig=" + getUserSig + "&" + "random=" + getRandom + "&" + "contenttype=" + jsonObject;
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
//               .add("methodName", methodName)
//               .add("functionName", functionName)
//               .add("appCode", "1")
//               .add("compid", compid)
                .build();
        Request request = new Request.Builder()
                .url(friendImportUrl)
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject friendDelete() {
        return null;
    }

    /**
     * 根据企业id查询该企业所有用户
     *
     * @param eid 企业id
     * @return 返回是否通过
     */
    private List<Object> getuserAll(int eid) {
        String baseUrl = "";
        baseUrl = url + "/user/userAll";
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
        List<Object> list = new ArrayList<>();
        try {
            String result = HttpClientUtil.post(config);
            com.alibaba.fastjson.JSONArray array = JSONObject.parseObject(result).getJSONArray("data");

            for (Object o : array) {
                list.add(o.getClass());
            }
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
        baseUrl = "";
        return list;
    }

}
