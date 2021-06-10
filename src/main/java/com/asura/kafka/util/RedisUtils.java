package com.asura.kafka.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author wangx 2020/08/06 17:26
 */
@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 默认过期时长为24小时，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24L;

    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1L;


    public void set(String key, Object value, long expire) {
        redisTemplate.opsForValue().set(key, value);
        expire(key, expire, TimeUnit.SECONDS);
    }

    public void set(String key, Object value, long expire, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value);
        expire(key, expire, timeUnit);
    }

    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE, TimeUnit.SECONDS);
    }


    public void expire(String key, long expire, TimeUnit timeUnit) {
        redisTemplate.expire(key, expire, timeUnit);
    }

    public boolean has(String key) {
        return redisTemplate.hasKey(key);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 根据 key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
}
