package com.sky.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sky.exception.AuthException;
import com.sky.exception.ServiceException;

/**
 * 通用工具类
 * 
 * @作者 乐此不彼
 * @时间 2017年9月28日
 * @公司 sky工作室
 */
public class U {
	
	/**
	 * 抛出业务异常
	 * 
	 * @param flag
	 * @param msg
	 */
	public static void assertException(boolean flag, String msg) {
		throw new ServiceException(500, msg);
	}

	/**
	 * 抛出业务异常
	 * 
	 * @param flag
	 * @param msg
	 */
	public static void assertAuthException(boolean flag, String msg) {
		throw new AuthException(500, msg);
	}

	/**
	 * 获取对应类型的当前时间,clazz参数可传可不传,默认返回Date类型的日期
	 * 
	 * @param <T>
	 * @throws Exception
	 * @throws InstantiationException
	 */
	@SuppressWarnings("all")
	public static <T> T now(Class... clazz) {
		try {
			if (clazz == null || clazz.length <= 0)
				return (T) new Date();

			Object obj = clazz[0].newInstance();
			if (obj instanceof Date)
				return (T) new Date();
			else if (obj instanceof String)
				return (T) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("当前时间获取失败");
		}
	}

}
