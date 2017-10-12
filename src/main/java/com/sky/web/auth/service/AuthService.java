package com.sky.web.auth.service;

import com.sky.web.auth.request.RegisterReq;
import com.sky.web.user.pojo.User;

public interface AuthService {
	
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	User register(RegisterReq registerReq);
	
	/**
	 * 用户登录,返回新的token
	 */
	String login(String phone,String password);
	
	/**
	 * 刷新token
	 */
	String refresh(String token);
	
}
