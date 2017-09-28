package com.sky.security;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.sky.user.mapper.UserMapper;
import com.sky.user.pojo.User;

@Service
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
		User user = userMapper.selectOne(new User().setUserName(username));
		
		System.err.println(String.format("根据用户名 %s,查询获得用户信息 %s", username,user.toString()));
		
		SecurityUser securityUser = new SecurityUser();
		BeanUtils.copyProperties(user, securityUser);
		
		System.err.println(String.format("将由数据库查询出来的用户信息转化为securityUser = %s", securityUser.toString()));
		
		//使用bCrypt加密密码
		//String encodePassword = bCryptPasswordEncoder.encode(securityUser.getPassword());
		
		//使用md5加密密码
		String encodePassword = md5PasswordEncoder.encode(securityUser.getPassword());
		
		System.err.println(String.format("用户加密前的密码 : %s ,加密后的密码 : %s ", user.getPassword(),encodePassword));
		securityUser.setPassword(encodePassword);
		return securityUser;
	}
	
	
	
	

}
