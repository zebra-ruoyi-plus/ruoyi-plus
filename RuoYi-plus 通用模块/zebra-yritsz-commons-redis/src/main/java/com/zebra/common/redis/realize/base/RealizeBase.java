package com.zebra.common.redis.realize.base;

public interface RealizeBase<V> {

	/**
	 * 初始化
	 *
	 * @param key
	 * @return
	 */
	public boolean init(String key);

	/**
	 * 根据key获取缓存信息
	 *
	 * @param key
	 * @param filed
	 * @return
	 */
	public V getHk(String key, String field);

	/**
	 * 根据key删除缓存
	 *
	 * @param key
	 * @return
	 */
	public void del(String key);

}
