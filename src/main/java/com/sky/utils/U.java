package com.sky.utils;

import com.sky.exception.AuthException;
import com.sky.exception.ServiceException;

/**
 * 通用工具类
 * @作者 乐此不彼
 * @时间 2017年9月28日
 * @公司 sky工作室
 */
public class U {

	/**
	 * 抛出业务异常
	 * @param flag
	 * @param msg
	 */
	public static void assertException(boolean flag,String msg) {
		throw new ServiceException(500, msg);
	}
	
	/**
	 * 抛出业务异常
	 * @param flag
	 * @param msg
	 */
	public static void assertAuthException(boolean flag,String msg) {
		throw new AuthException(500, msg);
	}
}
