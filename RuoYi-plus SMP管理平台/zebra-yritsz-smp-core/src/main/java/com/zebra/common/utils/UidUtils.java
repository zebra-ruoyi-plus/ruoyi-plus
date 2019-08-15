package com.zebra.common.utils;

import java.util.UUID;

public class UidUtils {
	/**
	 * 获取uuid
	 * 
	 * @param isConcise
	 * @return
	 */
	public static String getUUID(boolean isConcise) {
		if (!isConcise) {
			return UUID.randomUUID().toString();
		}
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
