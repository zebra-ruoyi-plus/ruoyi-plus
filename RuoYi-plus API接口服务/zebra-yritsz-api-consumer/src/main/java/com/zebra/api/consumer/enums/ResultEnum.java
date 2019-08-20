package com.zebra.api.consumer.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
	ERROR("-1", "Trigger Fuse Server Busy"), ERROR_400("400", "this is 400"), ERROR_404("404",
			"this is 404"), ERROR_500("500", "this is 500"), ERROR_TOKEN("9998", "This is an illegal request.");

	private final String code;
	private final String msg;

	ResultEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
