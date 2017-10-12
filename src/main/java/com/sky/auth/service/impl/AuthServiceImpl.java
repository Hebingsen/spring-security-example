package com.sky.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.sky.auth.service.AuthService;
import com.sky.security.Md5PasswordEncoder;
import com.sky.security.MyUserDetailsService;
import com.sky.utils.JwtTokenUtil;
import com.sky.utils.MD5;
import com.sky.web.user.mapper.UserMapper;
import com.sky.web.user.pojo.User;

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

	/**
	 * security用户详情
	 */
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	/**
	 * 用户
	 */
	@Autowired
	private UserMapper userMapper;

	@Override
	public User register(User user) {
		user.setPassword(MD5.encode(user.getPassword()));
		int result = userMapper.insert(user);
		log.info("用户注册结果:" + result);
		return user;
	}

	@Override
	public String login(String username, String password) {
		
		//根据用户名与密码生成token认证
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
		
		//将当前认证管理设置为token认证,返回获取到一个新的认证器
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		
		//设置为当前认证
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//获取用户详情信息
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		
		return jwtTokenUtil.generateToken(userDetails);
	}

	@Override
	public String refresh(String token) {
		// TODO Auto-generated method stub
		return null;
	}

}
