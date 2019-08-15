package com.zebra.api.consumer.util;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.zebra.api.consumer.util.SignConstants.SignType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SignUtil {

	/**
	 * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
	 *
	 * @param data
	 *            待签名数据
	 * @param key
	 *            API密钥
	 * @param signType
	 *            签名方式
	 * @return 签名
	 */
	public static String generateSignature(final Map<String, String> data, String key, SignType signType)
			throws Exception {
		Set<String> keySet = data.keySet();
		String[] keyArray = keySet.toArray(new String[keySet.size()]);
		Arrays.sort(keyArray);
		StringBuilder sb = new StringBuilder();
		for (String k : keyArray) {
			if (k.equals(SignConstants.FIELD_SIGN)) {
				continue;
			}
			if (data.get(k) != null && data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
				sb.append(k).append("=").append(data.get(k).trim()).append("&");
		}
		sb.append("key=").append(key);
		if (SignType.MD5.equals(signType)) {
			return EncryptionUtils.md5(sb.toString()).toUpperCase();
		} else if (SignType.HMACSHA256.equals(signType)) {
			return EncryptionUtils.HMACSHA256(sb.toString(), key);
		} else {
			throw new Exception(String.format("Invalid sign_type: %s", signType));
		}
	}

	/**
	 * 判断核心key是否存在
	 *
	 * @param tokenKey
	 * @return
	 */
	public static Boolean getSignIsNotKey(String tokenKey) {
		if (!tokenKey.contains(SignConstants.FIELD_SIGN) || !tokenKey.contains(SignConstants.TOKEN_TIME)
				|| !tokenKey.contains(SignConstants.TOKEN_NONCE_STR)) {
			log.info("[信息]校验签名失败：签名（sign）或时间（time）或随机字符(nonce)不存在-" + tokenKey);
			return false;
		}
		return true;

	}

	/**
	 * 判断核心key值是否存在
	 *
	 * @param sgin
	 * @param time
	 * @param nonceStr
	 * @return
	 */
	public static Boolean getSignIsNull(String sgin, String time, String nonceStr) {
		if (StringUtils.isEmpty(sgin) || StringUtils.isEmpty(time) || StringUtils.isEmpty(nonceStr)) {
			log.info("[信息]校验签名失败：签名（sign）或时间（time）或随机字符(nonce)值为空");
			return false;
		}
		if (nonceStr.length() != SignConstants.TOKEN_NONCE_STR_lENGTH) {
			log.info("[信息]随机字符串长度不符：" + nonceStr.length());
			return false;

		}
		Date date = DateUtil.format(time, SignConstants.TOKEN_TIME_FORMT);
		if (date == null) {
			log.info("[信息]时间戳错误：" + time);
			return false;
		}
		log.info("[信息]客户端请求时间：" + DateUtil.format(date) + ",服务当前时间：" + DateUtil.format(new Date()));
		return true;

	}

	public static void main(String[] args) {

	}

}
