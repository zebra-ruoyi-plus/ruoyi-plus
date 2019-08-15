package com.zebra.framework.config;

import java.time.Duration;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zebra.common.config.ConfigServerRedis;

@Configuration
public class RedisConfig<T, V> {
	@Autowired
	private ConfigServerRedis configServerRedis;

	/**
	 * 本地数据源 redis template
	 *
	 * @param database
	 * @param timeout
	 * @param maxActive
	 * @param maxWait
	 * @param maxIdle
	 * @param minIdle
	 * @param hostName
	 * @param port
	 * @param password
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Bean
	public RedisTemplate<T, V> redisTemplate() {

		/* ========= 基本配置 ========= */
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(configServerRedis.getHost());
		configuration.setPort(configServerRedis.getPort());
		configuration.setDatabase(configServerRedis.getDatabase());
		if (!StringUtils.isEmpty(configServerRedis.getPassword())) {
			RedisPassword redisPassword = RedisPassword.of(configServerRedis.getPassword());
			configuration.setPassword(redisPassword);
		}
		/* ========= 连接池通用配置 ========= */
		GenericObjectPoolConfig<T> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
		genericObjectPoolConfig.setMaxTotal(configServerRedis.getMaxActive());
		genericObjectPoolConfig.setMinIdle(configServerRedis.getMinIdle());
		genericObjectPoolConfig.setMaxIdle(configServerRedis.getMaxIdle());
		genericObjectPoolConfig.setMaxWaitMillis(configServerRedis.getMaxWait());

		LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration
				.builder();
		builder.poolConfig(genericObjectPoolConfig);
		builder.commandTimeout(Duration.ofSeconds(configServerRedis.getTimeout()));
		LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration, builder.build());
		connectionFactory.afterPropertiesSet();
		return createRedisTemplate(connectionFactory);
	}

	/**
	 * json 实现 redisTemplate
	 * <p>
	 * 该方法不能加 @Bean 否则不管如何调用，connectionFactory都会是默认配置
	 *
	 * @param redisConnectionFactory
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public RedisTemplate createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
				Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

}
