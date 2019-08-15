package com.zebra.api.consumer.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
	public enum OPERTYPE {
		getNotice("获取公告信息"), getNoticeList("获取公告列表");
		// 构造方法
		private String opertype;

		private OPERTYPE(String oper) {
			this.opertype = oper;
		}

		@Override
		public String toString() {
			return String.valueOf(this.opertype);
		}
	};

	OPERTYPE oper();
}
