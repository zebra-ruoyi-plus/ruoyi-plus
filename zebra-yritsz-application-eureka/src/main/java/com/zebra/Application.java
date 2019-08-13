package com.zebra;

import java.lang.management.ManagementFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import lombok.extern.slf4j.Slf4j;

@EnableEurekaServer
@SpringBootApplication
@Slf4j
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		log.info("当前项目进程号：" + jvmName.split("@")[0]);
	}
}
