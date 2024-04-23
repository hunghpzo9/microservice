package com.example.point.service.service;

public interface RedisService {
    Object getBucket(String key);
    boolean setNx(String key);
    void setKey(String key,Object value);
    long incrBy(String key,int increaseMeant);
}
