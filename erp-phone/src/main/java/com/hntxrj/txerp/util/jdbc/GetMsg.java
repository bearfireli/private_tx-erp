package com.hntxrj.txerp.util.jdbc;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.util.MsgAlert;
import com.hntxrj.txerp.vo.JsonVo;

/**
 * 功能:  获取提示消息
 *
 * @Auther 李帅
 * @Data 2017-09-05.上午 11:06
 */
public class GetMsg {

    /**
     *  获取Jsonarry的msg信息
     * @param jsonArray
     * @return
     */
    public static void getJsonMsg(JsonVo vo, JSONArray jsonArray){


        String str = jsonArray.toString();

        if (str.contains("成功")){
            vo.setCode(0);
            vo.setMsg(MsgAlert.成功.name());
        }else if (str.contains("失败")){
            vo.setCode(1);
            vo.setMsg(MsgAlert.失败.name());
        }else if (str.contains("重复") || str.contains("存在")){
            vo.setCode(1);
            vo.setMsg(MsgAlert.重复.name());
        }else if (jsonArray.size() < 1 || jsonArray.getJSONArray(0).size() < 1){
            vo.setCode(1);
            vo.setMsg(MsgAlert.返回数据为空.name());
        }else{
            vo.setCode(1);
            vo.setMsg(MsgAlert.数据库处理错误.name());
        }
    }
}
