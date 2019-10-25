//package com.hntxrj.util;
//
//import com.hntxrj.server.TickServer;
//import lombok.Data;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.security.DigestException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.Date;
//
//@Data
//@Component
//@ConfigurationProperties(prefix = "weixin")
//public class WeiXinUtil {
//    /**
//     * 应用appSecret
//     */
//    private String appId;
//    /**
//     * 应用appSecret
//     */
//    private String AppSecret;
//
//
//    private Long timestamp = System.currentTimeMillis();
//    private String nonceStr = "tongxinruanjian";
//    private String signature;
//
//    private String ticket;
//    private String token;
//
//
//    private String url;
//    private String params;
//
//
//    public void setUrlAndParams(String url,String params){
//        this.url = url;
//        this.params = params;
//        init(url, params);
//    }
//    //方法的入口
//    public void init(String url, String params) {
//        getTicket();
//        sign(url,params);
//    }
//    //参数加密,获取signature
//    public void sign(String sendUrl, String params) {
//        String str = "jsapi_ticket=" +  ticket +  "&noncestr=" + nonceStr + "&timestamp=" + timestamp +  "&url=" + sendUrl;
//        //sha1加密
//         signature = SHA1(str);
//    }
//    //加密算法
//    public static String SHA1(String str) {
//        try {
//            MessageDigest digest = java.security.MessageDigest
//                    .getInstance("SHA-1"); //如果是SHA加密只需要将"SHA-1"改成"SHA"即可
//            digest.update(str.getBytes());
//            byte messageDigest[] = digest.digest();
//            // Create Hex String
//            StringBuffer hexStr = new StringBuffer();
//            // 字节数组转换为 十六进制 数
//            for (int i = 0; i < messageDigest.length; i++) {
//                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
//                if (shaHex.length() < 2) {
//                    hexStr.append(0);
//                }
//                hexStr.append(shaHex);
//            }
//            return hexStr.toString();
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
////    public static String SHA1(String str) throws DigestException {
////        //获取信息摘要 - 参数字典排序后字符串
////        try {
////            //指定sha1算法
////            MessageDigest digest = MessageDigest.getInstance("SHA-1");
////            digest.update(str.getBytes());
////            //获取字节数组
////            byte messageDigest[] = digest.digest();
////            // Create Hex String
////            StringBuffer hexString = new StringBuffer();
////            // 字节数组转换为 十六进制 数
////            for (int i = 0; i < messageDigest.length; i++) {
////                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
////                if (shaHex.length() < 2) {
////                    hexString.append(0);
////                }
////                hexString.append(shaHex);
////            }
////            return hexString.toString().toUpperCase();
////
////        } catch (NoSuchAlgorithmException e) {
////            e.printStackTrace();
////            throw new DigestException("签名错误！");
////        }
////    }
//
//
////    public String getTicket() {
////        //查询数据库验证是否需要重新获取ticket
////        TickServer tickServer= new TickServerImpl();
////        JSONArray jsonArray =tickServer.getTicketSqlServer();
////        JSONObject jsonObject = (JSONObject) jsonArray.getJSONArray(1).get(0);
////        String result = jsonObject.getString("result");
////        //不需要获取
////        //设置this.token 和 this.ticket
////        //返回ticket
////        if (Integer.parseInt(result) == 0){
////            JSONObject jsonObject1 =(JSONObject) jsonArray.getJSONArray(0).get(0);
////              this.token = jsonObject1.getString("token");
////              this.ticket = jsonObject1.getString("ticket");
////            return  ticket;
////        }else {
////            //需要获取
////            String response = "";
////            try {
////                getToken();
////                String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ this.token +"&type=jsapi";
////                HttpUtil httpUtil = new HttpUtil(url);
////                response = httpUtil.get();
////                System.out.println("ticket:"+response);
////                JSONObject json = JSONObject.fromObject(response);
////                if (json.getString("ticket") != null){
////                    this.ticket =  json.getString("ticket");
////                }
////
////                //把this.token 和 this.ticket 入库 同时更新时间
////               JSONArray jsonArray1 =  tickServer.tokenticket(this.token,this.ticket);
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////            return response;
////        }
////    }
//    //获取token
//    public String getToken(){
//        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&" +
//                "appid="+appId+"&secret="+AppSecret;
//        HttpUtil httpUtil = new HttpUtil(url);
//        String response = null;
//        try {
//            response = httpUtil.get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("token:"+response);
//        JSONObject json = JSONObject.fromObject(response);
//
//        try {
//            if (json.getString("access_token") != null){
//                this.token =  json.getString("access_token");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return response;
//
//
//    }
//    //返回微信信息
//    public JSONObject getWeiXinConf() throws IOException {
//
//        JSONObject json = new JSONObject();
//        json.put("appId", this.appId);
//        json.put("timestamp", this.timestamp);
//        json.put("nonceStr", this.nonceStr);
//        json.put("signature", this.signature);
//        return json;
//    }
//
//
//}
