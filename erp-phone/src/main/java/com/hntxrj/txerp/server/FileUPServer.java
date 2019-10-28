package com.hntxrj.txerp.server;


import com.alibaba.fastjson.JSONArray;

public interface FileUPServer {

    //小票签字
    JSONArray sp_insert_fileImage(String compid, String opid, String base64, String id, Double qiannum, String mark);

    /**
     * 查询小票签收图片
     * @param id
     * @param compid
     * @param opid
     * @return
     */
    JSONArray sp_filedown(String id, String compid, String opid);
}
