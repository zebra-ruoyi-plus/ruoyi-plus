package com.zebra.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 浮点类型数据工具类
 *
 * @author zebra
 *
 */
public class DoubleUtils {
	/**
	 * 保留ponit位小数
	 *
	 * @param value
	 * @param ponit
	 * @return
	 */
	public static String getDouble(Double value) {
		if (value == null) {
			return "0.00";
		}
		DecimalFormat df = new DecimalFormat("######0.00");
		return df.format(value);
	}

	/**
	 * 保留一位小数
	 *
	 * @param value
	 * @return
	 */
	public static String getDoubleOne(Double value) {
		if (value == null) {
			return "0.0";
		}
		DecimalFormat df = new DecimalFormat("######0.0");
		return df.format(value);
	}

	/**
	 * 保留整数
	 *
	 * @param value
	 * @return
	 */
	public static String getDoubleNone(Double value) {
		if (value == null) {
			return "0";
		}
		DecimalFormat df = new DecimalFormat("######0");
		return df.format(value);
	}

	/**
	 * 两个Double数相加
	 *
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double add(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.add(b2).doubleValue();
	}

	/**
	 * 两个Double数相减
	 *
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double sub(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 向上取整保留
	 *
	 * @param d
	 * @param n
	 * @return
	 */
	public static double getCeil(double d, int n) {
		BigDecimal b = new BigDecimal(String.valueOf(d));
		b = b.divide(BigDecimal.ONE, n, BigDecimal.ROUND_HALF_UP);
		return b.doubleValue();
	}

	public static void main(String[] args) {
		System.out.println(getCeil(0.0036, 2));
	}

}
