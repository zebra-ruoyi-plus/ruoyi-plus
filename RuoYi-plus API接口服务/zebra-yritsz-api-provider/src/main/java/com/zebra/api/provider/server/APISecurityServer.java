package com.zebra.api.provider.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zebra.api.provider.bean.Json;
import com.zebra.api.provider.server.base.BaseServer;
import com.zebra.api.provider.service.APISecurityService;

/**
 * api服务提供者（api安全信息）
 *
 * @author zebra
 *
 */
@RestController
public class APISecurityServer extends BaseServer {
	@Autowired
	private APISecurityService apiSecurityService;

	/**
	 * 获取key对应api安全信息
	 *
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/getAPISecurity/{key}", method = RequestMethod.POST)
	@ResponseBody
	public Json getNotice(@PathVariable String key) {
		return apiSecurityService.getAPISecurity(key);
	}

}
