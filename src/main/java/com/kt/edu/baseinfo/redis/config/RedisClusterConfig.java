package com.kt.edu.baseinfo.redis.config;

import io.lettuce.core.ReadFrom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableRedisRepositories
//@Profile({"!dev&!ait&!local&!local2"})
@Profile({"prd"})
public class RedisClusterConfig {

    @Value("${spring.data.redis.host:127.0.0.1}")
    private String host;

    @Value("${spring.data.redis.username:username}")
    private String username;

    @Value("${spring.data.redis.password:password}")
    private String password;

    @Value("${spring.data.redis.port:0}")
    private int port;

    @Value("${spring.data.redis.commandtime_duration:10}")
    private int commandTimeDuration;

    @Bean(name = "redisConnectionFactory")
    public RedisConnectionFactory redisConnectionFactory() {

        RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
        List<String> clusterNodes = Arrays.stream(host.split(","))
                .map(String::trim)
                .toList();

        for(String node: clusterNodes) {
            redisConfig.clusterNode(node, port);
        }

        redisConfig.setUsername(username);
        redisConfig.setPassword(password);
        return new LettuceConnectionFactory(redisConfig);
    }


    @Bean(name = "redisObjectTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean(name = "reactiveRedisConnectionFactory")
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
        List<String> clusterNodes = Arrays.stream(host.split(","))
                .map(String::trim)
                .toList();

        for(String node: clusterNodes) {
            redisConfig.clusterNode(node, port);
        }

        redisConfig.setUsername(username);
        redisConfig.setPassword(password);

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .commandTimeout(Duration.ofSeconds(commandTimeDuration))
                .build();

        return new LettuceConnectionFactory(redisConfig, clientConfig);
    }

}
