package com.sky.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sky.utils.U;
import com.sky.web.user.mapper.RoleMapper;
import com.sky.web.user.mapper.UserMapper;
import com.sky.web.user.mapper.UserRoleMapper;
import com.sky.web.user.pojo.Role;
import com.sky.web.user.pojo.User;
import com.sky.web.user.pojo.UserRole;
import com.sky.web.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleMapper userRoleMapper; 
	
	@Autowired
	private RoleMapper roleMapper;
	
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
		User user = userService.findByPhone(username);
		if(user == null)
			throw new UsernameNotFoundException(String.format("根据用户名 %s 找不到对应的用户信息", username));
		
		List<Role> roles = user.getRoles();
		List<UserRole> userRoles = userRoleMapper.select(new UserRole().setUserId(user.getId()));
		for (UserRole userRole : userRoles) {
			Long roleId = userRole.getRoleId();
			Role role = roleMapper.selectOne(new Role().setId(roleId));
			roles.add(role);
		}
		user.setRoles(roles);
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
