package com.sky.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sky.utils.U;
import com.sky.web.user.mapper.UserMapper;
import com.sky.web.user.pojo.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private Md5PasswordEncoder md5PasswordEncoder;
	
	/**
	 * 获取用户详情信息
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		//1.根据username查询数据库中的用户信息
		User user = userMapper.selectOne(new User().setPhone(username));
		if(user == null)
			throw new UsernameNotFoundException(String.format("根据用户名 %s 找不到对应的用户信息", username));
		
		SecurityUser securityUser = SecurityUserFactory.create(user);
		log.info(String.format("将由数据库查询出来的用户信息转化为securityUser = %s", securityUser.toString()));
		
		//使用bCrypt加密密码
		//String encodePassword = bCryptPasswordEncoder.encode(securityUser.getPassword());
		
		//使用md5加密密码
		//String encodePassword = md5PasswordEncoder.encode(securityUser.getPassword());
		//log.info(String.format("用户加密前的密码 : %s ,加密后的密码 : %s ", user.getPassword(),encodePassword));
		//securityUser.setPassword(encodePassword);
		
		return securityUser;
	}
	
	
	
	

}
