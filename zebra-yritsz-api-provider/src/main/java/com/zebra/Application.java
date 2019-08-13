package com.zebra;

import java.lang.management.ManagementFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.zebra.**.mapper")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@Slf4j
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		log.info("当前项目进程号：" + jvmName.split("@")[0]);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
	PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
	c.setIgnoreUnresolvablePlaceholders(true);
	return c;
	}

}
