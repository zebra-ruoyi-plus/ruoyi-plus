package com.zebra.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

/**
 * 基础缓存容器
 *
 * @author zebra <br/>
 *         2019-07-26
 *
 */
@SuppressWarnings("unchecked")
@Component(value = "redisCache")
public class RedisCache implements IBaseCache {
	@SuppressWarnings("rawtypes")
	@Resource
	protected RedisTemplate redisTemplate;

	@Override
	public <K, V> void set(K key, V value) {
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public <K, V> boolean setNX(K key, V value) {
		return redisTemplate.opsForValue().setIfAbsent(key, value);
	}

	@Override
	public <K, V> void set(K key, V value, long timeout, TimeUnit unit) {
		redisTemplate.opsForValue().set(key, value, timeout, unit);
	}

	@Override
	public <K> long increment(K key, long value) {
		return redisTemplate.opsForValue().increment(key, value);
	}

	@Override
	public <K, V> V get(K key) {
		return (V) redisTemplate.opsForValue().get(key);
	}

	@Override
	public <K> void del(K key) {
		redisTemplate.delete(key);
	}

	@Override
	public <K, V> void sAdd(K key, V... values) {
		redisTemplate.boundSetOps(key).add(values);
	}

	@Override
	public <K, V> V sPop(K key) {
		return (V) redisTemplate.boundSetOps(key).pop();
	}

	@Override
	public <K> long sSize(K key) {
		return redisTemplate.boundSetOps(key).size();
	}

	@Override
	public <K, HK, HV> void hPut(K key, HK field, HV hv) {
		redisTemplate.boundHashOps(key).put(field, hv);
	}

	@Override
	public <K, HK, HV> boolean hPutNX(K key, HK field, HV hv) {
		return redisTemplate.boundHashOps(key).putIfAbsent(field, hv);
	}

	@Override
	public <K, HK> long incrementH(K key, HK filed, long hv) {
		return redisTemplate.boundHashOps(key).increment(filed, hv);
	}

	@Override
	public <K, HK, HV> HV hGet(K key, HK field) {
		return (HV) redisTemplate.boundHashOps(key).get(field);
	}

	@Override
	public <K, HK, HV> Map<HK, HV> hGetAllEntries(K key) {
		return redisTemplate.boundHashOps(key).entries();
	}

	@Override
	public <K, HK> void hDel(K key, HK field) {
		redisTemplate.boundHashOps(key).delete(field);
	}

	@Override
	public <K> boolean hasKey(K key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public <K, HK> boolean hExists(K key, HK field) {
		return redisTemplate.boundHashOps(key).hasKey(field);
	}

	@Override
	public <T> T execute(SessionCallback<T> callback) {
		return (T) redisTemplate.execute(callback);
	}

	@Override
	public <K> long SCard(K key) {
		return redisTemplate.boundSetOps(key).size();
	}

	@Override
	public <K, V> List<V> hGetVals(K key) {
		return redisTemplate.boundHashOps(key).values();
	}

	@Override
	public <K, V> V keys(K pattern) {
		return (V) redisTemplate.keys(pattern);
	}

	@Override
	public <K> long keysLen(K pattern) {
		Set<?> keys = keys(pattern);
		if (null == keys) {
			return 0;
		}
		return keys.size();
	}

}
