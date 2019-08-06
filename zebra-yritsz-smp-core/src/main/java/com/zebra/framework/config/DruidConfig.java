package com.zebra.framework.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.util.Utils;
import com.zebra.common.config.ConfigServerDruid;
import com.zebra.common.enums.DataSourceType;
import com.zebra.framework.config.properties.DruidProperties;
import com.zebra.framework.datasource.DynamicDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * druid 配置多数据源
 *
 * @author ruoyi
 */
@Configuration
@Slf4j
public class DruidConfig {
	@Autowired
	private ConfigServerDruid configServerDruid;

	@Bean
	public DataSource masterDataSource(DruidProperties druidProperties) {
		log.info("[信息]：数据库加载.");
		DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
		return druidProperties.dataSource(dataSource, configServerDruid, false);
	}

	@Bean
	@ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
	public DataSource slaveDataSource(DruidProperties druidProperties) {
		log.info("[信息]：数据库加载，开启主从模式.");
		DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
		return druidProperties.dataSource(dataSource, configServerDruid, true);
	}

	@Bean(name = "dynamicDataSource")
	@Primary
	public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource) {
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
		targetDataSources.put(DataSourceType.SLAVE.name(), slaveDataSource);
		return new DynamicDataSource(masterDataSource, targetDataSources);
	}

	/**
	 * 去除监控页面底部的广告
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	@ConditionalOnProperty(name = "spring.datasource.druid.statViewServlet.enabled", havingValue = "true")
	public FilterRegistrationBean removeDruidFilterRegistrationBean(DruidStatProperties properties) {
		// 获取web监控页面的参数
		DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
		// 提取common.js的配置路径
		String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
		String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");
		final String filePath = "support/http/resources/js/common.js";
		// 创建filter进行过滤
		Filter filter = new Filter() {
			@Override
			public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
			}

			@Override
			public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
					throws IOException, ServletException {
				chain.doFilter(request, response);
				// 重置缓冲区，响应头不会被重置
				response.resetBuffer();
				// 获取common.js
				String text = Utils.readFromResource(filePath);
				// 正则替换banner, 除去底部的广告信息
				text = text.replaceAll("<a.*?banner\"></a><br/>", "");
				text = text.replaceAll("powered.*?shrek.wang</a>", "");
				response.getWriter().write(text);
			}

			@Override
			public void destroy() {
			}
		};
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(filter);
		registrationBean.addUrlPatterns(commonJsPattern);
		return registrationBean;
	}

	/**
	 * 注册一个StatViewServlet
	 *
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ServletRegistrationBean druidStatViewServlet() {
		// org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
				"/monitor/druid/*");
		// 添加初始化参数：initParams
		// 白名单：
		servletRegistrationBean.addInitParameter("allow", configServerDruid.getAllow());
		// IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not
		// permitted to view this page.
		servletRegistrationBean.addInitParameter("deny", configServerDruid.getDeny());
		// 登录查看信息的账号密码.
		servletRegistrationBean.addInitParameter("loginUsername", configServerDruid.getLoginUsername());
		servletRegistrationBean.addInitParameter("loginPassword", configServerDruid.getLoginPassword());
		// 是否能够重置数据.
		servletRegistrationBean.addInitParameter("resetEnable", configServerDruid.getResetEnable());
		return servletRegistrationBean;
	}

	/**
	 * 注册一个：filterRegistrationBean
	 *
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean druidStatFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
		// 添加过滤规则.
		filterRegistrationBean.addUrlPatterns("/*");
		// 添加忽略资源
		filterRegistrationBean.addInitParameter("exclusions",configServerDruid.getExclusions());
		return filterRegistrationBean;
	}
}
