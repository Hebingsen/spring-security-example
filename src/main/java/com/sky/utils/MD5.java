package com.sky.utils;

import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * md5加密工具类
 * 
 * @作者 乐此不彼
 * @时间 2017年9月28日
 * @公司 sky工作室
 */
public class MD5 {
	/**
	 * 盐值(加密的东西)
	 */
	private static final String SALT = "hebingsen.he";

	public static String encode(String password) {
		password = password + SALT;
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		char[] charArray = password.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}

			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static void main(String[] args) {
		// 加盐:8bfbe47e8dc4b7e7bea1ae12266ed4f9
		// 未加盐:e10adc3949ba59abbe56e057f20f883e
		System.out.println(MD5.encode("123456"));
	}
}
