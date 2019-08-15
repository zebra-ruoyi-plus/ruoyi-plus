package com.zebra.api.provider.druid;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.zebra.api.provider.config.ConfigServerDruid;

import lombok.extern.slf4j.Slf4j;

/**
 * druid配置
 *
 * @author zebra
 *
 */
@Slf4j
@Configuration
public class DruidConfig {

	@Autowired
	private ConfigServerDruid configServerDruid;

	@Bean
	public DataSource dataSourceOne() throws Exception {
		log.info("[信息]：数据库加载.");
			DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
			return this.druidDataSource(dataSource);
	}

	/**
	 * 注册一个StatFilter
	 *
	 * @return
	 */
	@Bean
	@Primary
	public StatFilter statFilter() {
		StatFilter statFilter = new StatFilter();
		statFilter.setMergeSql(configServerDruid.isMergeSql());
		statFilter.setLogSlowSql(configServerDruid.isLogSlowSql());
		statFilter.setSlowSqlMillis(configServerDruid.getLowSqlMillis());
		return statFilter;
	}

	/**
	 * 注册一个StatViewServlet
	 *
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ServletRegistrationBean druidStatViewServlet() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
				"/monitor/druid/*");
		servletRegistrationBean.addInitParameter("allow", configServerDruid.getAllow());
		servletRegistrationBean.addInitParameter("deny", configServerDruid.getDeny());
		servletRegistrationBean.addInitParameter("loginUsername", configServerDruid.getLoginUsername());
		servletRegistrationBean.addInitParameter("loginPassword", configServerDruid.getLoginPassword());
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
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.addInitParameter("exclusions", configServerDruid.getExclusions());
		return filterRegistrationBean;
	}

	private DataSource druidDataSource(DruidDataSource dataSource) throws Exception
	{
		log.info("[信息]：初始化Druid的数据源");

		dataSource.setUrl(configServerDruid.getMasterUrl());

		dataSource.setUsername(configServerDruid.getMasterName());

		dataSource.setPassword(configServerDruid.getMasterPassword());

		dataSource.setDbType(configServerDruid.getType());

		dataSource.setDriverClassName(configServerDruid.getDriverClassName());

		/** 配置初始化大小、最小、最大 */
		dataSource.setInitialSize(configServerDruid.getInitialSize());
		dataSource.setMaxActive(configServerDruid.getMaxActive());
		dataSource.setMinIdle(configServerDruid.getMinIdle());

		/** 配置获取连接等待超时的时间 */
		dataSource.setMaxWait(configServerDruid.getMaxWait());

		/** 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 */
		dataSource.setTimeBetweenEvictionRunsMillis(configServerDruid.getTimeBetweenEvictionRunsMillis());

		/** 配置一个连接在池中最小、最大生存的时间，单位是毫秒 */
		dataSource.setMinEvictableIdleTimeMillis(configServerDruid.getMinEvictableIdleTimeMillis());
		dataSource.setMaxEvictableIdleTimeMillis(configServerDruid.getMaxEvictableIdleTimeMillis());

		/**
		 * 用来检测连接是否有效的sql，要求是一个查询语句，常用select
		 * 'x'。如果validationQuery为null，testOnBorrow、testOnReturn、
		 * testWhileIdle都不会起作用。
		 */
		dataSource.setValidationQuery(configServerDruid.getValidationQuery());
		/**
		 * 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，
		 * 如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
		 */
		dataSource.setTestWhileIdle(configServerDruid.isTestWhileIdle());
		/** 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。 */
		dataSource.setTestOnBorrow(configServerDruid.isTestOnBorrow());
		/** 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。 */
		dataSource.setTestOnReturn(configServerDruid.isTestOnReturn());

		if (!StringUtils.isEmpty(configServerDruid.getConnectionProperties())) {
			dataSource.setConnectionProperties(configServerDruid.getConnectionProperties());
		}
		try {
			/** 配置监控统计拦截的filters */
			dataSource.setFilters(configServerDruid.getFilters());
		} catch (SQLException e) {
			log.error("[信息]filters错误", e);

		}
		/** 打开PSCache，并且指定每个连接上PSCache的大小 */
		dataSource.setPoolPreparedStatements(configServerDruid.isPoolPreparedStatements());
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(
				configServerDruid.getMaxPoolPreparedStatementPerConnectionSize());
		return dataSource;
	}

}
