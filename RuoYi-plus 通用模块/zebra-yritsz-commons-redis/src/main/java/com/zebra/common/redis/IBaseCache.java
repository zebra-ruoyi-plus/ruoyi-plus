package com.zebra.common.redis;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.SessionCallback;

/**
 * 基础容器操作定义
 *
 * @author zebra <br/>
 *         2019-07-26
 *
 */
public interface IBaseCache {
	/**
	 * 添加指定 <tt>k-v</tt> 到容器
	 *
	 * @param key
	 * @param value
	 */
	<K, V> void set(K key, V value);

	/**
	 * 当<tt>Key</tt>不存在时，则进行<tt>SET</tt>
	 *
	 * @param key
	 *            key
	 * @param value
	 *            value
	 * @param <K>
	 * @param <V>
	 * @return {@code true}-操作成功/{@code false}-操作失败
	 */
	<K, V> boolean setNX(K key, V value);

	/**
	 * 添加指定 <tt>k-v</tt> 到容器，并指定过期时间
	 *
	 * @param key
	 *            key
	 * @param value
	 *            value
	 * @param timeout
	 *            timeout
	 * @param unit
	 *            unit
	 * @param <K>
	 * @param <V>
	 */
	<K, V> void set(K key, V value, long timeout, TimeUnit unit);

	/**
	 * 指定 Key 的值增加 VALUE，并返回。当 Key 不存在时，Set Key，并将 0 + value 后的值返回
	 *
	 * @param key
	 *            key
	 * @param value
	 *            增加的值
	 * @param <K>
	 * @return
	 */
	<K> long increment(K key, long value);

	/**
	 * 从容器中获取指定 KEY 的值
	 *
	 * @param key
	 * @return {@code V}
	 */
	<K, V> V get(K key);

	/**
	 * 从容器中删除指定 KEY 的值
	 *
	 * @param key
	 */
	<K> void del(K key);

	/**
	 * 添加 <tt>Value</tt> 到指令的 <tt>Set</tt> 集合 <tt>key</tt> 中
	 *
	 * @param key
	 *            <tt>Set</tt> 集合名称
	 * @param values
	 *            添加到 <tt>Set</tt> 集合的值
	 */
	<K, V> void sAdd(K key, @SuppressWarnings("unchecked") V... values);

	/**
	 * 移除并返回集合中的一个随机元素
	 *
	 * @param key
	 *            <tt>Set</tt> 集合名称
	 * @return {@link K} <tt>or null</tt>
	 */
	<K, V> V sPop(K key);

	/**
	 * 获取指定 <tt>Set</tt> 集合的大小
	 *
	 * @param key
	 *            <tt>Set</tt> 集合
	 * @param <K>
	 * @return long
	 */
	<K> long sSize(K key);

	/**
	 * 添加缓存信息到容器，存储结构类似 Redis HASH
	 *
	 * @param key
	 *            KEY
	 * @param field
	 *            FIELD
	 * @param hv
	 *            VALUE
	 */
	<K, HK, HV> void hPut(K key, HK field, HV hv);

	/**
	 * 添加<tt>key - field</tt> 到容器，仅当<tt>key - field</tt>不存在时，操作成功（返回true）。
	 *
	 * @param key
	 * @param field
	 * @param hv
	 * @param <K>
	 * @param <HK>
	 * @param <HV>
	 * @return <tt>true</tt> 成功/ <tt>false</tt> 失败
	 */
	<K, HK, HV> boolean hPutNX(K key, HK field, HV hv);

	/**
	 * 对指定的<tt>key - filed</tt>增加值<tt>hv</tt>（当且仅当<tt>hv</tt>为正整数时），如果
	 * <tt>hv</tt>为负整数时，为减操作
	 *
	 * @param key
	 *            KEY
	 * @param filed
	 *            FIELD
	 * @param hv
	 *            VALUE
	 * @param <K>
	 * @param <HK>
	 * @return long
	 */
	<K, HK> long incrementH(K key, HK filed, long hv);

	/**
	 * 获取 HASH（类似 Redis HASH） 表中，指定 KEY 的指定 FILED
	 *
	 * @param key
	 * @param field
	 * @param <HK>
	 * @return
	 */
	<K, HK, HV> HV hGet(K key, HK field);

	/**
	 * 获取指定 <tt>HASH MAP</tt> 中全部实体
	 *
	 * @param key
	 *            hashmap
	 * @param <K>
	 * @param <HK>
	 * @param <HV>
	 * @return Map<HK, HV>
	 */
	<K, HK, HV> Map<HK, HV> hGetAllEntries(K key);

	/**
	 * 删除 HASH 表中，指定 KEY 的指定 FIELD 的值
	 *
	 * @param key
	 *            KEY
	 * @param field
	 *            FILED
	 * @param <HK>
	 */
	<K, HK> void hDel(K key, HK field);

	/**
	 * 检查缓存容器中是否存在指定<tt>Key</tt>
	 *
	 * @param key
	 *            缓存<tt>Key</tt>
	 * @param <K>
	 * @return {@code true}-存在/{@code false}-不存在
	 */
	<K> boolean hasKey(K key);

	/**
	 * 检查缓存容器中是否存在指定<tt>Key-Field</tt>，基于<tt>HASH MAP</tt>
	 *
	 * @param key
	 *            缓存<tt>Key</tt>
	 * @param field
	 *            缓存<tt>Field</tt>
	 * @param <K>
	 * @param <HK>
	 * @return {@code true}-存在/{@code false}-不存在
	 */
	<K, HK> boolean hExists(K key, HK field);

	/**
	 * Redis事务处理(乐观锁支持)： Redis通过使用WATCH, MULTI, and
	 * EXEC组成的事务来实现乐观锁(注意没有用DISCARD),Redis事务没有回滚操作。
	 * 在SpringDataRedis当中通过RedisTemplate的SessionCallback中来支持(否则事务不生效)。
	 * discard的话不需要自己代码处理， callback返回null，成功的话，返回非null，依据这个来判断事务是否成功(没有抛异常)。
	 * <p>
	 *
	 * <pre>
	 *     redisTemplate.execute(new {@link SessionCallback}() {
	 *          &#64;Override
	 *          public Object execute(RedisOperations operations) throws DataAccessException {
	 *              operations.watch(key);
	 *              String origin = (String) operations.opsForValue().get(key);
	 *              operations.multi();
	 *              operations.opsForValue().set(key, origin + idx);
	 *              Object rs = operations.exec();
	 *              return rs;
	 *          }
	 *      }
	 * </pre>
	 *
	 * @param callback
	 *            see {@link SessionCallback}
	 * @param <T>
	 * @return {@link T}
	 */
	<T> T execute(SessionCallback<T> callback);

	/**
	 * @param key
	 * @return
	 * @Description: 根据key获取set集合中元素的数量
	 * @Check parameters by the 【caller】
	 */
	<K> long SCard(K key);

	/**
	 * @param key
	 * @return
	 * @Description: 根据K获取Hash中全部的元素
	 * @Check parameters by the 【caller】
	 */
	<K, V> List<V> hGetVals(K key);

	/**
	 * 获取匹配<tt>Redis</tt>中符合指定正则表达式的<tt>Key</tt>
	 *
	 * @param pattern
	 *            正则表达式
	 * @param <K>
	 * @param <V>
	 * @return 符合规则的<tt>Key</tt>
	 */
	<K, V> V keys(K pattern);

	/**
	 * 获取匹配<tt>Redis</tt>中符合指定正则表达式的<tt>Key</tt>数量
	 *
	 * @param pattern
	 * @param <K>
	 * @return 符合规则的<tt>Key</tt>数量
	 */
	<K> long keysLen(K pattern);
}
