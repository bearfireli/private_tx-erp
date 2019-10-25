package com.hntxrj.server;


/**
 * redis缓存
 */
public interface RedisService {


    /**
     * 读取字符串缓存
     * @param key
     * @return
     */
    String getStr(String key);

    /**
     * 存入
     * @param key
     * @param val
     */
    void setStr(String key, String val);

    /**
     * 删除缓存
     * @param key
     */
    void delStr(String key);


    /**
     * 获取对象
     * @param obj
     * @return
     */
    Object getObj(Object obj);

    /**
     * 设置对象
     * @param key
     * @param val
     */
    void setObj(Object key, Object val);


    /**
     * 删除对象
     * @param obj
     */
    void delObj(Object obj);


}
