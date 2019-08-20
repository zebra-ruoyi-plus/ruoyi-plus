package com.zebra.api.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zebra.api.consumer.service.impl.DemoServiceImpl;

@FeignClient(value = "config", fallback = DemoServiceImpl.class)
public interface APISecurityService {
	/**
	 * 根据key获取api安全信息
	 *
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/getAPISecurity/{key}", method = RequestMethod.POST)
	public String getAPISecurity(String key);

}
