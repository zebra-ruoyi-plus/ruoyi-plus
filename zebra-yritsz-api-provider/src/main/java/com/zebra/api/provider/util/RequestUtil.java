package com.zebra.api.provider.util;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * IP工具类
 *
 * @author zebra
 *
 */
@Slf4j
public class RequestUtil {

	/**
	 * 获取登录用户的IP地址
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "127.0.0.1";
		}
		if (ip.split(",").length > 1) {
			ip = ip.split(",")[0];
		}
		return ip;
	}

	/**
	 * 获取拦截json
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String readJSONString(HttpServletRequest request) throws Exception {
		StringBuffer json = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				json.append(line);
			}
		} catch (Exception e) {
			log.error("[信息]解析json异常", e);
			throw new Exception();
		}
		return json.toString();
	}

	public static String getUrlLast(String url) {
		int index = url.lastIndexOf("/");
		if (index < 0) {
			return "";
		}
		url = url.substring(index + 1);

		int index_2 = url.lastIndexOf("?");
		if (index_2 > 0) {
			url = url.substring(index_2 + 1);
		}
		int index_3 = url.lastIndexOf("#");
		if (index_3 > 0) {
			url = url.substring(index_3 + 1);
		}
		return url;
	}

	public static String getUrlLastTwo(String url) {
		int index = url.lastIndexOf("/");
		if (index < 0) {
			return "";
		}
		url = url.substring(0, index);
		index = url.lastIndexOf("/");
		if (index < 0) {
			return "";
		}
		return url.substring(index + 1);
	}

}
