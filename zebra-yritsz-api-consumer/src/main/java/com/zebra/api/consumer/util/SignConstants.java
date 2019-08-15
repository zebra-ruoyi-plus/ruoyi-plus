package com.zebra.api.consumer.util;


public class SignConstants {
	/**
	 * 认证key
	 */
	public static final String TOKEN_KEY = "tokenKey";
	/**
	 * 认证时间字段
	 */
	public static final String TOKEN_TIME = "tokenTime";
	/**
	 * 认证时间字段格式
	 */
	public static final String TOKEN_TIME_FORMT = "yyyyMMddHHmmss";
	/**
	 * 认证随机字符
	 */
	public static final String TOKEN_NONCE_STR = "tokenNonceStr";
	/**
	 * 认证随机字符长度
	 */
	public static final int TOKEN_NONCE_STR_lENGTH = 36;
	/**
	 * 认证签名字符
	 */
	public static final String FIELD_SIGN = "sign";
	/**
	 * 正确响应
	 */
	public static final String RESULT_SUCCESS = "0000";

	public enum SignType {
		MD5, HMACSHA256
	}
}
