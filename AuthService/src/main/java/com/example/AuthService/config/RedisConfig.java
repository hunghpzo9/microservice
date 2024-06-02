package com.example.AuthService.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Configuration
@Slf4j
public class RedisConfig {
    @Value("classpath:application.yml")
    private Resource resource;
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws IOException {
        try {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlMap = yaml.load(resource.getInputStream());

            Map<String, Object> redissonConfigMap = (Map<String, Object>) yamlMap.get("redisson");

            Yaml redissonYaml = new Yaml();
            String redissonConfigYaml = redissonYaml.dump(redissonConfigMap);

            Config config = Config.fromYAML(redissonConfigYaml);

            return Redisson.create(config);
        } catch (IOException e) {
            log.error("Failed to load Redisson configuration", e);
        } catch (Exception e) {
            log.error("Failed to create Redisson client", e);
        }
        return null;
    }
}
