package com.hntxrj.txerp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.entity.PageBean;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class PageRecoedCountUtils {
    /**
     * 从jsonarary 中获取recordCount
     * @param jsonArray  dao 层  返回的Jsonarray
     * @param pageSize  分页页数
     * @param currPage 分页长度
     * @return PageBean 分页对象
     */
    public static  PageBean getPage(JSONArray jsonArray , Integer pageSize, Integer currPage){
        PageBean pageBean = null;
        JSONObject jsonObject = null;
        try {
            if (jsonArray.size() > 1){
                JSONArray j = jsonArray.getJSONArray(1);
                jsonObject = j.getJSONObject(0);
            }else {
                JSONArray j = jsonArray.getJSONArray(0);
                jsonObject = j.getJSONObject(0);
            }

            String recordCount = jsonObject.getString("recordCount");
            pageBean = new PageBean(pageSize,currPage);
            pageBean.setRecordCount(Integer.parseInt(recordCount));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return  pageBean ;
    }

    /**
     *  自定义查询的位置
     * @param jsonArray 返回的jsonarray
     * @param pageSize 分页长度
     * @param currPage 当前页
     * @param index 要去的数据
     * @return
     */
    public static  PageBean getPageIndex(JSONArray jsonArray,Integer pageSize ,Integer currPage,Integer index){
        PageBean   pageBean  = null;
        try {
            if (jsonArray!=null){
                JSONObject jsonObject = (JSONObject) jsonArray.getJSONArray(index).get(0);
                String recordCount = jsonObject.getString("recordCount");
                pageBean = new PageBean(pageSize,currPage);
                pageBean.setRecordCount(Integer.parseInt(recordCount));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("创建分页对象出错",e);
        }
        return  pageBean ;
    }

    /**
     * 返回的jsonArry   长度为1   jsonArray.getJSONArray(0).get(0);
     * @param jsonArray
     * @return
     */
    public String iudResult(JSONArray jsonArray ){
        JSONObject jsonObject = (JSONObject) jsonArray.getJSONArray(0).get(0);
        return jsonObject.getString("result");
    }

    /**
     * 返回的jsonArry   长度为1   jsonArray.getJSONArray(0).get(0);
     * @param jsonArray
     * @return
     */
    public boolean iudBoolean(JSONArray jsonArray){
        JSONObject jsonObject = (JSONObject) jsonArray.getJSONArray(0).get(0);
        String result = jsonObject.getString("result");
        return result.contains("成功");
    }


}
