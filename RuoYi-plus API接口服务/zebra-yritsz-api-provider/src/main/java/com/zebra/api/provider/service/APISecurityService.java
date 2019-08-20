package com.zebra.api.provider.service;

import com.zebra.api.provider.bean.Json;

public interface APISecurityService {
	/**
	 * 根据key获取api安全信息
	 *
	 * @param key
	 * @return
	 */
	public Json getAPISecurity(String key);

}
