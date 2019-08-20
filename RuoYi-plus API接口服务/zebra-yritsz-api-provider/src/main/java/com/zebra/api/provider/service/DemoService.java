package com.zebra.api.provider.service;

import com.zebra.api.provider.bean.Json;

public interface DemoService {
	/**
	 * 获取公告信息
	 *
	 * @param noticeId
	 * @return
	 */
	public Json getNotice(Long noticeId);

	/**
	 * 获取公告列表
	 *
	 * @return
	 */
	public Json getNoticeList();

}
