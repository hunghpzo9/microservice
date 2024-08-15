package com.example.AuthService.service.impl;

import com.example.AuthService.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedissonClient redisson;

    @Override
    public <T> T get(String key) {
        try {
            RBucket<T> bucket = redisson.getBucket(key);
            return bucket.get();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public <T> void set(String key, T value) {
        try {
            RBucket<T> bucket = redisson.getBucket(key);
            bucket.set(value);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public <T> boolean setNx(String key, T value) {
        try {
            RBucket<T> bucket = redisson.getBucket(key);
            return bucket.trySet(value);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    @Override
    public <T> void set(String key, T value, long timeToLive, TimeUnit timeUnit) {
        try {
            RBucket<T> bucket = redisson.getBucket(key);
            bucket.set(value, timeToLive, timeUnit);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public <T> boolean setNx(String key, T value, long timeToLive, TimeUnit timeUnit) {
        try {
            RBucket<T> bucket = redisson.getBucket(key);
            return bucket.trySet(value, timeToLive, timeUnit);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;

    }

    @Override
    public boolean hset(String key, Map<String,String> map, long timeToLive, TimeUnit timeUnit) {
        try {
            RMap<String, String> rMap = redisson.getMap(key);
            rMap.putAll(map);
           return rMap.expire(timeToLive, timeUnit);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    @Override
    public String hget(String key, String field) {
        try {
            RMap<String, String> rMap = redisson.getMap(key);

            return rMap.get(field);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteValue(String key) {
        try {
            RBucket<Object> bucket = redisson.getBucket(key);
            bucket.delete();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
