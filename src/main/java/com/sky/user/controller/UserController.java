package com.sky.user.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sky.base.ResponseEntity;
import com.sky.exception.AuthException;
import com.sky.user.mapper.UserMapper;
import com.sky.user.pojo.User;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
public class UserController {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private HttpServletRequest request;
	
	@GetMapping("/")
	public Object get() {
		return ResponseEntity.success("正在访问UserController");
	}
	
	/**
	 * 当前用户登录信息
	 */
	@GetMapping("/login-info")
	public Object loginInfo() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.err.println("principal="+principal);
		Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
		System.err.println("credentials="+credentials);
		Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
		System.err.println("details="+details);
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		System.err.println("authorities="+authorities);
		
		boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
		System.err.println("authenticated="+authenticated);
		
		String header = request.getHeader("token");
		System.err.println("header="+header);
		
		
		return ResponseEntity.success("登录信息", details);
	}
	
	
	/**
	 * 测试mybatis查询
	 * @param id
	 * @return
	 */
	@GetMapping("/test/{id}")
	public Object userTest(@PathVariable String id) {
		User user = userMapper.selectOne(new User().setId(Integer.parseInt(id)));
		return user;
	}
	
	/**
	 * 测试全局异常处理
	 * @return
	 */
	@GetMapping("/exception")
	public Object testException() {
		throw new AuthException(500, "测试全局异常是否有作用");
	}
	
	
	
}
