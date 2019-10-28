//package com.hntxrj.server.impl;
//
//import com.hntxrj.server.RedisService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * redis缓存操作
// * @author lhr
// * @create 2017/9/27
// */
//@Service
//public class RedisServiceImpl implements RedisService{
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//
//    @Override
//    public String getStr(String key) {
//        return stringRedisTemplate.opsForValue().get(key);
//    }
//
//    @Override
//    public void setStr(String key, String val) {
//        stringRedisTemplate.opsForValue().set(key, val);
//    }
//
//    @Override
//    public void delStr(String key) {
//        stringRedisTemplate.delete(key);
//    }
//
//    @Override
//    public Object getObj(Object obj) {
//        return redisTemplate.opsForValue().get(obj);
//    }
//
//    @Override
//    public void setObj(Object key, Object val) {
//        redisTemplate.opsForValue().set(key, val);
//    }
//
//    @Override
//    public void delObj(Object obj) {
//        redisTemplate.delete(obj);
//    }
//}
