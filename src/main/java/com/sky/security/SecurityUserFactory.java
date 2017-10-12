package com.sky.security;

import org.springframework.beans.BeanUtils;

import com.sky.web.user.pojo.User;
/**
 * security用户信息的工厂类,目的是创建securityUser对象
 * @作者 乐此不彼
 * @时间 2017年9月28日
 * @公司 sky工作室
 */
public final class SecurityUserFactory {

	private SecurityUserFactory() {}
	
	/**
	 * 创建SecurityUser实体对象
	 * @param user UserDetailsService根据username提供的用户信息
	 * @return
	 */
	public static SecurityUser create(User user) {
		SecurityUser securityUser = new SecurityUser();
		BeanUtils.copyProperties(user, securityUser);
		return securityUser;
	}

}
