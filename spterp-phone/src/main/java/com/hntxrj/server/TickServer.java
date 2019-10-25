package com.hntxrj.server;


import com.alibaba.fastjson.JSONArray;

public interface  TickServer  {
    JSONArray getTicketSqlServer();

    JSONArray tokenticket(String token, String ticket);
}
