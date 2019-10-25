package com.hntxrj.dao;


import com.alibaba.fastjson.JSONArray;

public interface FileUPDao {
    //小票签收保存图片
    JSONArray sp_insert_fileImage(String compid, String opid, String base64, String id, Double qiannum, String mark);

    //小票签收查看图片
    JSONArray sp_filedown(String id, String compid, String opid);
}
