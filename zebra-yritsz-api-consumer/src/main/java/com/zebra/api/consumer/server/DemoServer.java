package com.zebra.api.consumer.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zebra.api.consumer.aop.LogAnnotation;
import com.zebra.api.consumer.aop.LogAnnotation.OPERTYPE;
import com.zebra.api.consumer.service.DemoService;

/**
 * api服务消费者（demo实例）
 *
 * @author yanshuangbin
 *
 */
@RestController
public class DemoServer {
	@Autowired
	private DemoService demoService;

	@RequestMapping(value = "/getNotice/{noticeId}", method = RequestMethod.GET)
	@ResponseBody
	@LogAnnotation(oper = OPERTYPE.getNotice)
	public String getNotice(@PathVariable(value="noticeId") Long noticeId) {
		return demoService.getNotice(noticeId);
	}

	@RequestMapping(value = "/getNoticeList", method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(oper = OPERTYPE.getNotice)
	public String getNoticeList() {
		return demoService.getNoticeList();
	}

}
