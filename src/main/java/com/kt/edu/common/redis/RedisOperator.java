package com.kt.edu.common.redis;

import com.kt.edu.common.utils.LogUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
public class RedisOperator<T> {

    @Autowired
    @Qualifier("redisObjectTemplate")
    private RedisTemplate<String, T> redisTemplate;

    @Resource(name = "redisObjectTemplate")
    private ValueOperations<String, T> valueOps;

    @Resource(name = "redisObjectTemplate")
    private ValueOperations<String, List<T>> valueOpsList;

    public RedisOperator(){
    }
    public T getValue(String key) {
        try {
            T result = valueOps.get(key);
            LogUtil.info("[CTG:CMMN] RedisOperator getValue - key:{}, value:{}", key, result);
            return result;

        } catch (Exception e) {
            LogUtil.error("[CTG:CMMN] RedisOperator getValue error : {} " , e.getMessage());
            return null;
        }
    }
    public List<T> getListValue(String key) {
        try {
            return valueOpsList.get(key);
        } catch (Exception e) {
            LogUtil.error("[CTG:CMMN] RedisOperator getListValue error : {}", e.getMessage());
            return null;
        }
    }


    public void set(String key, T value, long timeout, TimeUnit timeUnit) {
        try {
            valueOps.set(key, value, timeout,  timeUnit);
        } catch (Exception e) {
            LogUtil.error("[CTG:CMMN] RedisOperator set  error : {}", e.getMessage());
        }
    }

    public void setList(String key, List<T> list, long timeout, TimeUnit timeUnit){
        try {
            valueOpsList.set(key, list, timeout, timeUnit);
        } catch (Exception e) {
            LogUtil.error("[CTG:CMMN] RedisOperator setList  error: {}", e.getMessage());
        }
    }

    public void delete(String key) {
        try {
            redisTemplate.delete(key);
            LogUtil.info("[CTG:CMMN] RedisOperator delete --- key: {}", key);
        } catch (Exception e) {
            LogUtil.error("[CTG:CMMN] RedisOperator delete  error: {}",  e.getMessage());
        }
    }

    public Iterable<byte[]> getRedisTemplate(RedisCallback<Iterable<byte[]>> redisCallback) {
        return (Iterable<byte[]>) redisTemplate.execute((RedisCallback<T>) redisCallback);
    }
}
