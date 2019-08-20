package com.zebra.api.provider.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
	SUCCESS("0000", "操作成功"), PARAMERROR("1000", "参数错误"), PARAMNULL("1001", "信息不存在"), APINULL("1002",
			"API信息不存在"), APINERROR("1003", "API状态异常"), ERROR("9999", "操作失败"), ERROR_400("400",
					"this is 400"), ERROR_404("404", "this is 404"), ERROR_500("500", "this is 500");
	private final String code;
	private final String msg;

	ResultEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
