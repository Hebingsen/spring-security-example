package com.sky.web.auth.service;

import io.jsonwebtoken.Claims;

/**
 * 鉴权相关业务层
 * @作者 乐此不彼
 * @时间 2017年10月20日
 * @公司 sky工作室
 */
public interface AuthService {
	
	/**
	 * 用户登录,返回新的token
	 */
	String login(String phone,String password);
	
	/**
	 * 刷新token
	 */
	String refresh(String refreshToken);

	/**
	 * 解析token
	 * @param token
	 * @return
	 */
	Claims parser(String token);
	
	
}
