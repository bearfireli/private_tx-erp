package com.hntxrj.txerp.dao;


import com.alibaba.fastjson.JSONArray;

public interface TickDao {
    JSONArray getTicketSqlServer();

    JSONArray tokenticket(String token, String ticket);
}
