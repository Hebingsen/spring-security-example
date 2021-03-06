package com.sky.controller;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sky.user.mapper.UserMapper;
import com.sky.user.pojo.User;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserMapper userMapper;
	
	@GetMapping("/")
	public Object getUserInfo() {
		
		for (int i = 1; i < 100; i++) {
			User user = new User();
			user.setUserName("user-"+i);
			user.setPassword("123456");
			user.setPhone("1812274375"+i);
			user.setCreateTime(new Date());
			userMapper.insertSelective(user);
		}
		
		List<User> users = userMapper.findAll();
		return users;
	}
	
	
	@GetMapping("/test/{id}")
	public Object userTest(@PathVariable String id) {
		User user = userMapper.selectOne(new User().setId(Integer.parseInt(id)));
		return user;
	}
	
	
	
}
