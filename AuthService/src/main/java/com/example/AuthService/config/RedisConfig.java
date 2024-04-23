package com.example.point.service.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean(destroyMethod = "shutdown")
    RedissonClient redisson() {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000) // adjust the scan interval if needed
                .addNodeAddress("redis://192.168.1.10:8021", "redis://192.168.1.10:8022", "redis://192.168.1.10:8023")
                .addNodeAddress("redis://192.168.1.11:8021", "redis://192.168.1.11:8022", "redis://192.168.1.11:8023")
                .addNodeAddress("redis://192.168.1.12:8021", "redis://192.168.1.12:8022", "redis://192.168.1.12:8023")
                .setPassword("Redis@2024");
        return Redisson.create(config);
    }


}
