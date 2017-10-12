package com.sky.auth.service;

import com.sky.web.user.pojo.User;

public interface AuthService {
	
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	User register(User user);
	
	/**
	 * 用户登录,返回新的token
	 */
	String login(String username,String password);
	
	/**
	 * 刷新token
	 */
	String refresh(String token);
	
}
