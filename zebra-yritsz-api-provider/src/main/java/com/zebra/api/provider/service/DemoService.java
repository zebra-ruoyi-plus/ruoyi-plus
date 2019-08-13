package com.zebra.api.provider.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zebra.api.provider.bean.Json;

public interface DemoService {
	/**
	 * 获取公告信息
	 *
	 * @param noticeId
	 * @return
	 */
	@RequestMapping(value = "/getNotice", method = RequestMethod.POST)
	public Json getNotice(Long noticeId);

	/**
	 * 获取公告列表
	 *
	 * @return
	 */
	@RequestMapping(value = "/getNoticeList", method = RequestMethod.POST)
	public Json getNoticeList();

}
