package com.hntxrj.txerp.server;


import com.alibaba.fastjson.JSONArray;

public interface  TickServer  {
    JSONArray getTicketSqlServer();

    JSONArray tokenticket(String token, String ticket);
}
