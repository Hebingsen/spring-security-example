package com.sky.web.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.web.user.mapper.UserMapper;
import com.sky.web.user.pojo.User;
import com.sky.web.user.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User findByPhone(String phone) {
		User user = userMapper.selectOne(new User().setPhone(phone));
		return user;
	}
	
}
