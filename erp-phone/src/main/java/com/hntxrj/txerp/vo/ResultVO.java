package com.hntxrj.txerp.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * @author haoranliu
 */
@Data
public class ResultVO<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;


    public static <T> ResultVO<T> create(T date) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMsg("ok");
        resultVO.setData(date);
        return resultVO;
    }

    public static <T> ResultVO<T> create() {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMsg("ok");
        return resultVO;
    }

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
