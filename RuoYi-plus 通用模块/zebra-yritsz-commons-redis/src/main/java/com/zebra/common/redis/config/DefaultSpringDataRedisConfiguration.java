package com.zebra.common.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.zebra.common.redis.kyro.KryoSerializer;

/**
 * Spring Data Redis</tt> 配置
 *
 * @author <br/>
 *         2019-07-26
 *
 */
@Configuration
public class DefaultSpringDataRedisConfiguration {
	@Bean
	public RedisTemplate<?, ?> getRedisTemplate(RedisTemplate<?, ?> redisTemplate) {
		// 设置key的序列化规则
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		// key的序列化规则
		redisTemplate.setValueSerializer(new KryoSerializer());
		redisTemplate.setHashValueSerializer(new KryoSerializer());

		return redisTemplate;
	}
}
