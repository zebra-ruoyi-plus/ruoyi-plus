package com.zebra.api.provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zebra.api.provider.bean.Json;
import com.zebra.api.provider.enums.ResultEnum;
import com.zebra.api.provider.service.DemoService;
import com.zebra.api.provider.util.ResultUtil;
import com.zebra.system.domain.SysNotice;
import com.zebra.system.mapper.SysNoticeMapper;

@Service
public class DemoServiceImpl implements DemoService {

	@Autowired
	private ResultUtil resultUtil;

	@Autowired
	private SysNoticeMapper sysNoticeMapper;

	@Override
	public Json getNotice(Long noticeId) {
		if (StringUtils.isEmpty(noticeId)) {
			return resultUtil.returnOther(ResultEnum.PARAMERROR.getCode(), ResultEnum.PARAMERROR.getMsg());
		}
		SysNotice notice = sysNoticeMapper.selectNoticeById(noticeId);
		if (notice == null) {
			return resultUtil.returnOther(ResultEnum.PARAMNULL.getCode(), ResultEnum.PARAMNULL.getMsg());
		}
		return resultUtil.returnSuccess(notice);
	}

	@Override
	public Json getNoticeList() {
		return resultUtil.returnSuccess(sysNoticeMapper.selectNoticeList(null));
	}

}
