package com.hntxrj.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil<T> {

    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, T> redisTemplate;

    @Autowired
    public RedisUtil(StringRedisTemplate stringRedisTemplate, RedisTemplate redisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.redisTemplate = redisTemplate;
    }

    public void stringRedisSetKey(String key, String value) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(key, value, 10, TimeUnit.MINUTES);// 10分钟后过期
    }

    public String stringRedisGetValue(String key) {
        ValueOperations<String, String> ops = this.stringRedisTemplate.opsForValue();
        return ops.get(key);
    }

    /**
     * 获取redis里面的所有key值
     * @return
     */
    public Set CountKeys(){
        Set stringKeys=redisTemplate.keys("*");
        return  stringKeys;
    }

    public void redisSetKey(String key, T object) {
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set(key, object, 100000, TimeUnit.MINUTES);// 1000000分钟后过期
    }

    public T redisGetValue(String key) {
        ValueOperations ops = this.redisTemplate.opsForValue();
        return (T) ops.get(key);
    }

    /**
     * 删除key值以及value
     * @param key
     */
    public void redisRemoveValue(String key) {
        redisTemplate.delete(key);
    }


}
