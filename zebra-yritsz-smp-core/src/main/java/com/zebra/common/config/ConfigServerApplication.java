package com.zebra.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RefreshScope
@RestController
@Repository
@Data
public class ConfigServerApplication {
	/**
	 * 名称
	 */
	@Value("${zebra.name}")
	private String name;
	/**
	 * 版本
	 *
	 */
	@Value("${zebra.version}")
	private String version;
	/**
	 * 版权年份
	 */
	@Value("${zebra.copyrightYear}")
	private String copyrightYear;

	/**
	 * 实例演示开关
	 */
	@Value("${zebra.demoEnabled}")
	private String demoEnabled;

	/**
	 * 获取头像上传路径
	 */
	@Value("${zebra.avatarPath}")
	private String avatarPath;

	/**
	 * 获取下载路径
	 */
	@Value("${zebra.uploadPath}")
	private String uploadPath;

	/**
	 * 获取ip地址开关
	 */
	@Value("${zebra.addressEnabled}")
	private String addressEnabled;

	/**
	 * 文件路径
	 */
	@Value("${zebra.profile}")
	private String profile;

	/**
	 * 获取下载路径
	 */
	@Value("${zebra.downloadPath}")
	private String downloadPath;

	/**
	 * 限制操作ip
	 */
	@Value("${zebra.ips}")
	private String ips;

	/**
	 * 刷新配置中心地址
	 */
	@Value("${zebra.config.refresh}")
	private String refresh;

}
