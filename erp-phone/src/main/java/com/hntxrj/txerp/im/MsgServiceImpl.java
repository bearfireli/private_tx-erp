package com.hntxrj.txerp.im;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.vo.SendmsgVO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
public class MsgServiceImpl implements MsgService {
    @Value("${app.cloud.CommunicationUrl}")
    private String url;

    @Override
    public String sendMsg(SendmsgVO sendmsgVO) throws ErpException {
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
        if (sendmsgVO.getMsgLifeTime()==0){
            jsonObject.put("MsgLifeTime",0);
        }else if (sendmsgVO.getMsgLifeTime()==7){
            jsonObject.put("MsgLifeTime",604800);
        }
        //消息随机数
        jsonObject.put("MsgRandom",ImBaseData.getRandom());
        //消息时间戳
        jsonObject.put("MsgTimeStamp", new Date());

        JSONObject jsonObject1 =new JSONObject();
        JSONArray jsonArray =new JSONArray();
        if (!"".equals(sendmsgVO.getMsgType()) && null !=sendmsgVO.getMsgType()){
            jsonObject1.put("MsgType",sendmsgVO.getMsgType());
        }
        //消息内容
        JSONObject jsonObject2 =new JSONObject();
        jsonObject2.put("Text",sendmsgVO.getMsgContent());
        jsonObject1.put("jsonObject2",jsonObject2);
        jsonArray.put(jsonObject1);
        jsonObject.put("MsgBody",jsonObject);

        String sendMsgUrl="";
        sendMsgUrl = url + "/openim/sendmsg"+ "?" + "sdkappid=" + ImBaseData.sdkAppId + "&" + "identifier=" + ImBaseData.identifier + "&" +
                "usersig=" + ImBaseData.getUserSig() + "&" + "random=" + ImBaseData.getRandom();
        Map<String, Object> map = new HashMap<>();
        map.put("contenttype", jsonObject);
//        Header[] headers = HttpHeader.custom()
//                .other("version", "1")
//                .build();
        //插件式配置请求参数（网址、请求参数、编码、client）
        HttpConfig config = HttpConfig.custom()
//                .headers(headers)
                .url(url)
                .map(map)
                .url(sendMsgUrl)
                .encoding("utf-8")
                .inenc("utf-8");
        String result=null;
        try {
            result = HttpClientUtil.post(config);
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
        sendMsgUrl = "";
        return result;
    }

    @Override
    public String batchSendMsg(SendmsgVO sendmsgVO) throws ErpException {
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
        for (int i =0;i<toAccountList.length;i++){
            array.put(toAccountList[i]);
        }
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
        if (sendmsgVO.getMsgLifeTime()==0){
            jsonObject.put("MsgLifeTime",0);
        }else if (sendmsgVO.getMsgLifeTime()==7){
            jsonObject.put("MsgLifeTime",604800);
        }
        //消息随机数
        jsonObject.put("MsgRandom",ImBaseData.getRandom());
        //消息时间戳
        jsonObject.put("MsgTimeStamp", new Date());

        JSONObject jsonObject1 =new JSONObject();
        JSONArray jsonArray =new JSONArray();
        if (!"".equals(sendmsgVO.getMsgType()) && null !=sendmsgVO.getMsgType()){
            jsonObject1.put("MsgType",sendmsgVO.getMsgType());
        }
        //消息内容
        JSONObject jsonObject2 =new JSONObject();
        jsonObject2.put("Text",sendmsgVO.getMsgContent());
        jsonObject1.put("jsonObject2",jsonObject2);
        jsonArray.put(jsonObject1);
        jsonObject.put("MsgBody",jsonObject);

        String sendMsgUrl="";
        sendMsgUrl = url + "/openim/batchsendmsg"+ "?" + "sdkappid=" + ImBaseData.sdkAppId + "&" + "identifier=" + ImBaseData.identifier + "&" +
                "usersig=" + ImBaseData.getUserSig() + "&" + "random=" + ImBaseData.getRandom();
        Map<String, Object> map = new HashMap<>();
        map.put("contenttype", jsonObject);
//        Header[] headers = HttpHeader.custom()
//                .other("version", "1")
//                .build();
        //插件式配置请求参数（网址、请求参数、编码、client）
        HttpConfig config = HttpConfig.custom()
//                .headers(headers)
                .url(url)
                .map(map)
                .url(sendMsgUrl)
                .encoding("utf-8")
                .inenc("utf-8");
        String result=null;
        try {
            result = HttpClientUtil.post(config);
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
        sendMsgUrl = "";
        return result;
    }

}
