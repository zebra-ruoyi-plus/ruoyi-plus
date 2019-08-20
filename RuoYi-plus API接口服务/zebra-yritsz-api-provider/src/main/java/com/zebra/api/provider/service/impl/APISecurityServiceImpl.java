package com.zebra.api.provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zebra.api.provider.bean.Json;
import com.zebra.api.provider.config.ConfigServerRedis;
import com.zebra.api.provider.enums.ResultEnum;
import com.zebra.api.provider.service.APISecurityService;
import com.zebra.api.provider.util.ResultUtil;
import com.zebra.bussiness.domain.ApiSecurity;
import com.zebra.common.redis.realize.base.RealizeBase;

@Service
public class APISecurityServiceImpl implements APISecurityService {
	@Autowired
	private RealizeBase<ApiSecurity> apiSecurityRealize;

	@Autowired
	private ResultUtil resultUtil;

	@Autowired
	private ConfigServerRedis configServerRedis;

	@Override
	public Json getAPISecurity(String key) {
		if (StringUtils.isEmpty(key)) {
			return resultUtil.returnOther(ResultEnum.PARAMERROR.getCode(), ResultEnum.PARAMERROR.getMsg());
		}

		ApiSecurity apiSecurit = apiSecurityRealize.getHk(configServerRedis.getKey(), key);
		if (apiSecurit == null) {
			return resultUtil.returnOther(ResultEnum.APINULL.getCode(), ResultEnum.APINULL.getMsg());
		}
		if (!apiSecurit.getApiStatus()) {
			return resultUtil.returnOther(ResultEnum.APINERROR.getCode(), ResultEnum.APINERROR.getMsg());
		}
		return resultUtil.returnSuccess(apiSecurit);
	}

}
