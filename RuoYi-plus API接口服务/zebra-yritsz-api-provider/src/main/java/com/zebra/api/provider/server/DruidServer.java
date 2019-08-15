package com.zebra.api.provider.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * druid 监控
 *
 * @author zebra
 */
@Controller
@RequestMapping("/monitor/data")
public class DruidServer {
	@GetMapping()
	public String index() {
		return "redirect:/monitor/druid/index";
	}
}
