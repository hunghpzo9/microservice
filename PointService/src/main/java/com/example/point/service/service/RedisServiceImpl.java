package com.example.point.service.service;

import lombok.extern.log4j.Log4j2;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RedisServiceImpl implements RedisService {
    @Autowired
    RedissonClient redissonClient;

    @Override
    public Object getBucket(String key) {
        try {
            return redissonClient.getBucket(key);
        } catch (Exception ex) {
            log.info(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public boolean setNx(String key) {
        try {
            RBucket<Object> bucket = redissonClient.getBucket("key");
            return bucket.trySet(key);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return false;
    }

    @Override
    public void setKey(String key, Object value) {
        try {
            redissonClient.getBucket(key).set(value);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public long incrBy(String key, int increaseMeant) {
        try {
            RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
            long newValue = atomicLong.addAndGet(increaseMeant);
            return newValue;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return -1;
    }
}
