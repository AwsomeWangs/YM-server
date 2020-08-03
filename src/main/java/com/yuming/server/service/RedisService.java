package com.yuming.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Autowired
    RedisTemplate redisTemplate;

    public void set(String key,Object value){
        try {
            redisTemplate.opsForValue().set(key,value);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Redis保存异常");
        }
    }

    public void set(String key,Object value,long timeout){
        try {
            redisTemplate.opsForValue().set(key,value,timeout);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Redis保存异常");
        }
    }

    public Object get(String key){
        try {
            return redisTemplate.opsForValue().get(key);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Redis保存异常");
        }
        return null;
    }
}
