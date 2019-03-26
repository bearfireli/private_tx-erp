package com.hntxrj.txerp.core.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class ResultVO {

    private Integer code;
    private String msg;
    private Object data;


    /**
     * 主要用于转换组件之间通讯获取的字符串json为JSONObject的快捷方式。
     *
     * @param jsonData 获取的ResultVO json字符串对象
     * @return Data字段内容
     */
    public static JSONObject getObjData(String jsonData) {
        JSONObject resultVO = JSONObject.parseObject(jsonData);
        return resultVO.getJSONObject("data");
    }

    public static JSONArray getArrayData(String jsonData) {
        JSONObject resultVO = JSONObject.parseObject(jsonData);
        return resultVO.getJSONArray("data");
    }


}
