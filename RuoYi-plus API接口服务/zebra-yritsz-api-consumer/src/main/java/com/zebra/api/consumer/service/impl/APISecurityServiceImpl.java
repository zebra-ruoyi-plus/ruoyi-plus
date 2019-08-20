package com.zebra.api.consumer.service.impl;

import org.springframework.stereotype.Service;

import com.zebra.api.consumer.service.APISecurityService;
import com.zebra.api.consumer.service.base.BaseService;

@Service
public class APISecurityServiceImpl extends BaseService implements APISecurityService {

	@Override
	public String getAPISecurity(String key) {
		return returnHix();
	}

}
