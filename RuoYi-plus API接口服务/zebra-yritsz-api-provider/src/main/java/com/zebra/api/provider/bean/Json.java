package com.zebra.api.provider.bean;

import lombok.Data;

/**
 *
 * 响应信息bean
 *
 * @author zebra
 *
 */
@Data
public class Json {
	/**
	 * 响应码
	 */
	private String code;
	/**
	 * 响应说明
	 */
	private String msg;
	/**
	 * 响应数据
	 */
	private Object obj;

	public Object getObj() {
		if (obj == null) {
			return "";
		}
		return obj;
	}
}
