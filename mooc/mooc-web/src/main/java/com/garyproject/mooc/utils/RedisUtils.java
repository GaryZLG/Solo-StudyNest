package com.garyproject.mooc.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.testng.collections.CollectionUtils;

import java.util.Collection;
import java.util.Set;

import jakarta.annotation.Resource;

@Component
public class RedisUtils {

  @Resource
  private RedisTemplate<String, Object> redisTemplate;

  public Set<String> keys(String keys) {
    try {
      return redisTemplate.keys(keys);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  //指定缓存失效时间
  public boolean expire(String key, long time){
    try {
      if(time>0) {
        redisTemplate.expire(key, time, java.util.concurrent.TimeUnit.SECONDS);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  //根据key 获取过期时间
  /**
   * 获取key的过期时间
   * @param key
   * @return
   */
  public long getExpire(String key){
    return redisTemplate.getExpire(key, java.util.concurrent.TimeUnit.SECONDS);
  }

  //删除缓存
  public void del(String... key) {
    if(key != null && key.length > 0){
      if (key.length == 1) {
        redisTemplate.delete(key[0]);
      } else {
        //redisTemplate.delete(CollectionUtils.newArrayList(key));
      }
    }
  }

  //看key是否存在
  public boolean hasKey(String key) {
    try {
      return redisTemplate.hasKey(key);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  //普通缓存获取
  public Object get(String key) {
    return key == null ? null : redisTemplate.opsForValue().get(key);
  }

  //普通缓存放入
  public boolean set(String key, Object value) {
    try {
      redisTemplate.opsForValue().set(key, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  //普通缓存放入并设置时间
  public boolean set(String key, Object value, long time) {
    try {
      if(time > 0) {
        redisTemplate.opsForValue().set(key, value, time, java.util.concurrent.TimeUnit.SECONDS);
      }else{
        set(key, value);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
