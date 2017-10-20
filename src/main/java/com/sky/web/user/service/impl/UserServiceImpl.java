package com.sky.web.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.exception.ServiceException;
import com.sky.utils.U;
import com.sky.web.user.mapper.UserMapper;
import com.sky.web.user.pojo.User;
import com.sky.web.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User findByPhone(String phone) {
		User user = userMapper.selectOne(new User().setPhone(phone));
		return user;
	}

	@Override
	@Transactional
	public User register(User user) {
		try {
			user.setCreateTime(U.now());
			int result = userMapper.insertSelective(user);
			log.info("用户注册成功,返回结果:{}", result);
			return user;
		} catch (Exception e) {
			log.error("用户注册失败,失败信息:{}", e.getMessage());
			throw new ServiceException("用户注册失败");
		}
	}

	@Override
	@Transactional
	public void updateUser(User user) {
		int result = userMapper.updateByPrimaryKeySelective(user);
		log.info("用户信息更新结果 = {}", result);
	}

}
