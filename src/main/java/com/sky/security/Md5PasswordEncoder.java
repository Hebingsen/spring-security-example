package com.sky.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.sky.utils.MD5;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义md5加密
 * 
 * @作者 乐此不彼
 * @时间 2017年9月28日
 * @公司 sky工作室
 */
@Slf4j
public class Md5PasswordEncoder implements PasswordEncoder {

	/**
	 * 如何加密
	 * 
	 * @param rawPassword
	 * @return
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		log.info("使用md5加密前的密码:" + rawPassword);
		String encodePassword = MD5.encode(rawPassword.toString());
		log.info("使用md5加密后的密码:" + encodePassword);
		return encodePassword;
	}

	/**
	 * 如何匹配
	 * 
	 * @param rawPassword
	 * @param encodedPassword
	 * @return
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		log.info("Spring security 加密的密码 : " + encodedPassword);
		log.info("从页面传递过来未进行加密的密码 : " + rawPassword);
		log.info("从页面传递过来进行加密后的密码 : " + MD5.encode(rawPassword.toString()));
		return MD5.encode(rawPassword.toString()).equals(encodedPassword);
	}

}
