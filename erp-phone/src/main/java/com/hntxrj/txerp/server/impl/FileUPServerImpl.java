package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.dao.FileUPDao;
import com.hntxrj.txerp.server.FileUPServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service
@Scope("prototype")
public class FileUPServerImpl implements FileUPServer {

    private final FileUPDao fileUPDao;

    @Autowired
    public FileUPServerImpl(FileUPDao fileUPDao) {
        this.fileUPDao = fileUPDao;
    }


    @Override
    public JSONArray sp_insert_fileImage(String compid, String opid, String base64, String id, Double qiannum, String mark) {
        return fileUPDao.sp_insert_fileImage(compid, opid, base64, id, qiannum, mark);
    }

    /**
     * 查看小票图片
     *
     * @param id     小票
     * @param compid 企业
     * @param opid   当前用户
     * @return jsoN
     */
    @Override
    public JSONArray sp_filedown(String id, String compid, String opid) {

        return fileUPDao.sp_filedown(id, compid, opid);
    }
}
