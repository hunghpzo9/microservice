package com.example.AuthService.service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface RedisService {
    <T> T get(String key);
    <T> void set(String key, T value);
    <T> boolean setNx(String key, T value);
    <T> void set(String key, T value, long timeToLive, TimeUnit timeUnit);
    <T> boolean setNx(String key, T value, long timeToLive, TimeUnit timeUnit);
    boolean hset(String key,Map<String,String>map, long timeToLive, TimeUnit timeUnit);
    String hget(String key,String field);

    void deleteValue(String key);
}
