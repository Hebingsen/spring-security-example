package com.sky.web.user.service;

import com.sky.web.user.pojo.User;

/**
 * 用户相关service层
 * @作者 乐此不彼
 * @时间 2017年10月12日
 * @公司 sky工作室
 */
public interface UserService {

	/**
	 * 根据手机号查询用户信息
	 * @param username
	 * @return
	 */
	User findByPhone(String username);

}
 