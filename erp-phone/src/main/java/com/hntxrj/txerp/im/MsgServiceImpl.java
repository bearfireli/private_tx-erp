package com.hntxrj.txerp.im;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.vo.SendmsgVO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;



@Service
@Slf4j
public class MsgServiceImpl implements MsgService {
    @Value("${app.cloud.CommunicationUrl}")
    private String url;
    private static final MediaType _JSON = MediaType.parse("application/json; charset=utf-8");
    private String urlParams = "?sdkappid={{sdkappid}}&identifier=system&usersig={{usersig}}&random={{random}}&contenttype=json";

    @Autowired
    public MsgServiceImpl(@Value("${app.timsdk.sdkAppID}") Integer sdkAppId, @Value("${app.timsdk.key}") String key) {
        TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(sdkAppId, key);
        urlParams = urlParams
                .replace("{{sdkappid}}", String.valueOf(sdkAppId))
                .replace("{{usersig}}", tlsSigAPIv2.genSig("system", (365 * 24 * 60 * 60)))
                .replace("{{random}}", String.valueOf(new Random().nextInt()));
    }

    /**
     * 单发单聊消息
     * @return 接口返回参数
     * @param sendmsgVO 消息类
     */
    @Override
    public void sendMsg(SendmsgVO sendmsgVO) throws ErpException {
        if ("".equals(sendmsgVO.getToAccount()) || null ==sendmsgVO.getToAccount()){
            throw new ErpException(ErrEumn.TOACCOUNT_IS_NULL);
        }
        if ("".equals(sendmsgVO.getMsgContent()) || null ==sendmsgVO.getMsgContent()){
            throw new ErpException(ErrEumn.MsgBody_IS_NULL);
        }
        JSONObject jsonObject =new JSONObject();
        //消息同步 1消息同步/2消息不同步 默认情况下会将消息同步
        if (sendmsgVO.getSyncOtherMachine()==2){
            jsonObject.put("SyncOtherMachine",2);
        }else{
            jsonObject.put("SyncOtherMachine",1);
        }
        //消息发送方
        if (!"".equals(sendmsgVO.getFromAccount()) && null !=sendmsgVO.getFromAccount()){
            jsonObject.put("From_Account",sendmsgVO.getFromAccount());
        }
        //消息接收方
        jsonObject.put("To_Account",sendmsgVO.getToAccount());
        //消息保存时长 （单位：秒）最长为7天（604800秒）
        //若设置该字段为0，则消息只发在线用户，不保存离线
        //若设置该字段超过7天（604800秒），仍只保存7天
        //若不设置该字段，则默认保存7天
        if (sendmsgVO.getMsgLifeTime()>0){
            //前台传递的天， 把天转换成秒。
            int time= sendmsgVO.getMsgLifeTime()*60*60*24;
            jsonObject.put("MsgLifeTime",time);
        }
        //消息随机数
        jsonObject.put("MsgRandom",ImBaseData.getRandom());
        int time = (int) (new Date().getTime()/1000);
        //消息时间戳
        jsonObject.put("MsgTimeStamp", time);

        JSONObject o =new JSONObject();
        o.put("MsgType","TIMTextElem");
        JSONObject jsonObject2 =new JSONObject();
        JSONArray arr = new JSONArray();
        //消息内容
        jsonObject2.put("Text",sendmsgVO.getMsgContent());
        o.put("MsgContent",jsonObject2);
        arr.add(o);
        jsonObject.put("MsgBody",arr);

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(_JSON, JSON.toJSONBytes(jsonObject));
        Request request = new Request.Builder()
                .url(url + "/openim/sendmsg" + urlParams)
                .post(requestBody)
                .build();
        ResponseBody responseBody = null;
        try {
            Response response = client.newCall(request).execute();
            responseBody = response.body();
            if (responseBody != null) {
                String result = responseBody.string();
                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("请求失败!");
        } finally {
            if (responseBody != null) {
                responseBody.close();
            }
        }

    }

    /**
     * 批量发单聊消息
     * @return 接口返回参数
     * @param sendmsgVO 消息类
     */
    @Override
    public Boolean batchSendMsg(SendmsgVO sendmsgVO) throws ErpException {
        if ("".equals(sendmsgVO.getToAccount()) || null ==sendmsgVO.getToAccount()){
            throw new ErpException(ErrEumn.TOACCOUNT_IS_NULL);
        }
        if ("".equals(sendmsgVO.getMsgContent()) || null ==sendmsgVO.getMsgContent()){
            throw new ErpException(ErrEumn.MsgBody_IS_NULL);
        }
        JSONObject jsonObject =new JSONObject();
        //消息同步 1消息同步/2消息不同步 默认情况下会将消息同步
        if (sendmsgVO.getSyncOtherMachine()==2){
            jsonObject.put("SyncOtherMachine",2);
        }else{
            jsonObject.put("SyncOtherMachine",1);
        }
        //消息发送方
        if (!"".equals(sendmsgVO.getFromAccount()) && null !=sendmsgVO.getFromAccount()){
            jsonObject.put("From_Account",sendmsgVO.getFromAccount());
        }
        //消息接收方
        String [] toAccountList =sendmsgVO.getToAccount().split(",");
        JSONArray array =new JSONArray();
        array.addAll(Arrays.asList(toAccountList));
        jsonObject.put("To_Account",array);
        //消息保存时长 （单位：秒）最长为7天（604800秒）
        //若设置该字段为0，则消息只发在线用户，不保存离线
        //若设置该字段超过7天（604800秒），仍只保存7天
        //若不设置该字段，则默认保存7天
        if (sendmsgVO.getMsgLifeTime()>0){
            //前台传递的天， 把天转换成秒。
            int time= sendmsgVO.getMsgLifeTime()*60*60*24;
            jsonObject.put("MsgLifeTime",time);
        }
        //消息随机数
        jsonObject.put("MsgRandom",ImBaseData.getRandom());
        Date date =new Date();
        int time = (int) date.getTime();
        //消息时间戳
        jsonObject.put("MsgTimeStamp", time);

        JSONObject jsonObject1 =new JSONObject();
        jsonObject1.put("MsgType","TIMTextElem");
        JSONArray jsonArray =new JSONArray();
        //消息内容
        JSONObject jsonObject2 =new JSONObject();
        jsonObject2.put("Text",sendmsgVO.getMsgContent());
        jsonObject1.put("jsonObject2",jsonObject2);
        jsonArray.add(jsonObject1);
        jsonObject.put("MsgBody",jsonArray);
        RequestBody requestBody = RequestBody.create(_JSON, com.alibaba.fastjson.JSON.toJSONBytes(jsonObject));
        Request request = new Request.Builder()
                .url(url + "/openim/batchsendmsg" + urlParams)
                .post(requestBody)
                .build();
        ResponseBody responseBody = null;
        com.alibaba.fastjson.JSONObject resultJSON = null;
        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(request).execute();
            responseBody = response.body();
            if (responseBody != null) {
                String result = responseBody.string();
                System.out.println(result);
                resultJSON = com.alibaba.fastjson.JSONObject.parseObject(result);
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

}
