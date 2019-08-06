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
public class ConfigServerDruid {

	@Value("${spring.datasource.type}")
	private String type;

	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;

	@Value("${spring.datasource.druid.master.name}")
	private String masterName;

	@Value("${spring.datasource.druid.master.password}")
	private String masterPassword;

	@Value("${spring.datasource.druid.master.url}")
	private String masterUrl;

	@Value("${spring.datasource.druid.save.name}")
	private String saveName;

	@Value("${spring.datasource.druid.save.password}")
	private String savePassword;

	@Value("${spring.datasource.druid.save.url}")
	private String saveUrl;

	@Value("${spring.datasource.druid.initialSize}")
	private int initialSize;

	@Value("${spring.datasource.druid.minIdle}")
	private int minIdle;

	@Value("${spring.datasource.druid.maxActive}")
	private int maxActive;

	@Value("${spring.datasource.druid.maxWait}")
	private int maxWait;

	@Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;

	@Value("${spring.datasource.druid.maxEvictableIdleTimeMillis}")
	private int maxEvictableIdleTimeMillis;

	@Value("${spring.datasource.druid.validationQuery}")
	private String validationQuery;

	@Value("${spring.datasource.druid.testWhileIdle}")
	private boolean testWhileIdle;

	@Value("${spring.datasource.druid.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${spring.datasource.druid.testOnReturn}")
	private boolean testOnReturn;

	@Value("${spring.datasource.druid.allow}")
	private String allow;

	@Value("${spring.datasource.druid.deny}")
	private String deny;

	@Value("${spring.datasource.druid.loginUsername}")
	private String loginUsername;

	@Value("${spring.datasource.druid.loginPassword}")
	private String loginPassword;

	@Value("${spring.datasource.druid.resetEnable}")
	private String resetEnable;

	@Value("${spring.datasource.druid.exclusions}")
	private String exclusions;
}
