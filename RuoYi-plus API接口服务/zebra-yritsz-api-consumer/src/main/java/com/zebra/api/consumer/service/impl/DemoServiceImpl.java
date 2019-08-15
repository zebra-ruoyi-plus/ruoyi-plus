package com.zebra.api.consumer.service.impl;

import org.springframework.stereotype.Service;

import com.zebra.api.consumer.service.DemoService;
import com.zebra.api.consumer.service.base.BaseService;

@Service
public class DemoServiceImpl extends BaseService implements DemoService {

	@Override
	public String getNotice(Long noticeId) {
		return returnHix();
	}

	@Override
	public String getNoticeList() {
		return returnHix();
	}

}
