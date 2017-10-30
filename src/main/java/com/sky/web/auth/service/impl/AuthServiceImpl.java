package com.sky.web.auth.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.sky.exception.ServiceException;
import com.sky.redis.RedisUtil;
import com.sky.security.MyUserDetailsService;
import com.sky.security.SecurityUser;
import com.sky.security.SecurityUserUtil;
import com.sky.utils.JwtTokenUtil;
import com.sky.utils.U;
import com.sky.web.auth.service.AuthService;
import com.sky.web.user.pojo.User;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户鉴权service
 * 
 * @作者 乐此不彼
 * @时间 2017年9月28日
 * @公司 sky工作室
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

	/**
	 * 认证管理器
	 */
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private RedisUtil redis;
	
	@Autowired
	private SecurityUserUtil securityUserUtil;

	@Override
	public String login(String phone, String password) {
		
		try {
			//根据用户名与密码生成token认证
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phone,password);
			
			//将当前认证管理设置为token认证,返回获取到一个新的认证器
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			
			//设置为当前认证
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			//获取用户详情信息
			SecurityUser userDetails = (SecurityUser) userDetailsService.loadUserByUsername(phone);
			
			return jwtTokenUtil.generateToken(userDetails);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServiceException("登录失败");
		} 
	}

	@Override
	public String refresh(String refreshToken) {
		
		//1.判断refreshToken是否有效
		log.info("refreshToken={}",refreshToken);
		
		//2.无效->返回错误响应结果
		U.assertException(!redis.exists(refreshToken), "无效刷新令牌");
		
		//3.有效->解析refreshToken
		Claims claims = jwtTokenUtil.parser(refreshToken);
		String password = claims.get("password", String.class);
		String phone = claims.get("phone", String.class);
		
		//4.生成新的token
		String newToken = login(phone, password);
		User user = jwtTokenUtil.parser2User(newToken);
		securityUserUtil.storeToken(newToken, user, 3600L);
		
		
		return newToken;
	}

	@Override
	public Claims parser(String token) {
		return jwtTokenUtil.parser(token);
	}

}
