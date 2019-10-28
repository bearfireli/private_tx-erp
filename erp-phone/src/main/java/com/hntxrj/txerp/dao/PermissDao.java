package com.hntxrj.txerp.dao;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.entity.PermiUrl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 功能: 权限Dao接口层
 *
 * @Auther 李帅
 * @Data 2017-09-04.上午 10:39
 */
public interface PermissDao {
    /**
     *  加载权限组已选中ID
     * @param groupId  权限组ID
     * @return
     */
    JSONArray loadPermi(String groupId , String compid);

    List<PermiUrl> formathDataTwo(String token);

    List<String> formathData(String token);

    List<Map<String, Object>> formathDataNew(String token) throws SQLException;






}
