package com.sky.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
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
//@PostAuthorize("hasRole('USER')")
public class UserController {
	
	@Autowired
	private UserMapper userMapper;
	
	@GetMapping("/")
	public Object get() {
		return ResponseEntity.success("正在访问UserController");
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
