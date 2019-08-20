package com.zebra.common.redis.realize.base;

/**
 * redis实现父类
 *
 * @author zebra
 *
 */
public class RealizeParentBase {
	private final static String COMMON_SYMBOL = ".";

	/**
	 * 封装redis缓存key
	 *
	 * @param key
	 * @param subKeys
	 * @return
	 */
	public String getBasicKey(String key, String... subKeys) {
		StringBuffer stringBuffer = new StringBuffer(key);
		for (String subKey : subKeys) {
			stringBuffer.append(COMMON_SYMBOL);
			stringBuffer.append(subKey);
		}
		return stringBuffer.toString();
	}
}
