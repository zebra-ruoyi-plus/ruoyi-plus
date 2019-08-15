package com.zebra.common.utils;

import com.zebra.common.config.ConfigServerApplication;
import com.zebra.common.json.JSON;
import com.zebra.common.json.JSONObject;
import com.zebra.common.utils.http.HttpUtils;
import com.zebra.common.utils.spring.SpringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 获取地址类
 *
 * @author ruoyi
 */
@Slf4j
public class AddressUtils {
	public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

	public static String getRealAddressByIP(String ip) {
		String address = "XX XX";

		// 内网不查询
		if (IpUtils.internalIp(ip)) {
			return "内网IP";
		}
		ConfigServerApplication configServerApplication = SpringUtils.getBean(ConfigServerApplication.class);
		if (Boolean.valueOf(configServerApplication.getAddressEnabled())) {
			String rspStr = HttpUtils.sendPost(IP_URL, "ip=" + ip);
			if (StringUtils.isEmpty(rspStr)) {
				log.error("获取地理位置异常 {}", ip);
				return address;
			}
			JSONObject obj;
			try {
				obj = JSON.unmarshal(rspStr, JSONObject.class);
				JSONObject data = obj.getObj("data");
				String region = data.getStr("region");
				String city = data.getStr("city");
				address = region + " " + city;
			} catch (Exception e) {
				log.error("获取地理位置异常 {}", ip);
			}
		}
		return address;
	}
}
