package com.zebra.api.consumer.util;

import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 加密工具类
 *
 * @author yanshuangbin
 *
 */
public class EncryptionUtils {

	public static void main(String[] args) {
	}

	/**
	 * 用于建立十六进制字符的输出的小写字符数组
	 */
	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	/**
	 * 用于建立十六进制字符的输出的大写字符数组
	 */
	private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	/**
	 * @Description: md5加密
	 * @Check parameters by the 【caller】
	 * @param str
	 *            待加密字符串
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public static String md5(String str) throws Exception {
		byte[] md5 = DigestUtils.md5(str);
		String binarysToHexString = binarysToHexString(md5);
		return binarysToHexString;
	}

	/**
	 * 将字节数组转换为十六进制字符串(默认小写)
	 *
	 * @Description: [1, 15, 16, -16] -> "010f10f0"
	 * @Check parameters by the 【caller】
	 * @param data
	 * @return
	 */
	public static String binarysToHexString(byte[] data) throws Exception {
		return binarysToHexString(data, true);
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 *
	 * @Description: [1, 15, 16, -16],true -> "010f10f0" [1, 15, 16,
	 *               -16],false-> "010F10F0"
	 * @Check parameters by the 【caller】
	 * @param data
	 * @param toLowerCase
	 *            响应的十六进制大小写控制 true为小写false为大写
	 * @return
	 */
	public static String binarysToHexString(byte[] data, boolean toLowerCase) throws Exception {
		return new String(binarysToHexString(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER));
	}

	/**
	 * 将字节数组转换为字符数组
	 *
	 * @Description: [1, 15, 16, -16] -> [0, 1, 0, f, 1, 0, f, 0]
	 * @Check parameters by the 【caller】
	 * @param data
	 * @param toDigits
	 *            用于控制输出的char[]
	 * @return
	 */
	public static char[] binarysToHexString(byte[] data, char[] toDigits) throws Exception {
		int l = data.length;
		char[] out = new char[l << 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
			out[j++] = toDigits[0x0F & data[i]];
		}
		return out;
	}

	/**
	 * @Description: RSA md5签名
	 * @Check parameters by the 【caller】
	 * @param data
	 *            待签名数据
	 * @param privateKey
	 *            私钥
	 * @return 签名后的数据
	 * @throws Exception
	 */
	public static byte[] signByMD5WithRSA(byte[] data, RSAPrivateKey privateKey) throws Exception {
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initSign(privateKey);
		signature.update(data);
		return signature.sign();
	}

	/**
	 * 生成 HMACSHA256
	 *
	 * @param data
	 *            待处理数据
	 * @param key
	 *            密钥
	 * @return 加密结果
	 * @throws Exception
	 */
	public static String HMACSHA256(String data, String key) throws Exception {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
		StringBuilder sb = new StringBuilder();
		for (byte item : array) {
			sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString().toUpperCase();
	}

}
