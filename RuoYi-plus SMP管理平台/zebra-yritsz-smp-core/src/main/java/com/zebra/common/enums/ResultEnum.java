package com.zebra.common.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
	INFONULL("1001", "该信息不存在"), INFONOTNULL("1002", "该信息已存在");

	private final String code;
	private final String msg;

	ResultEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
