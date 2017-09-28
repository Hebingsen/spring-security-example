package com.sky.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sky.user.pojo.User;


public class SecurityUser extends User implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private Collection<? extends GrantedAuthority> authorities;

	/**
	 * 查询并返回权限信息
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		ArrayList<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		roles.add(new SimpleGrantedAuthority("ADMIN"));
		roles.add(new SimpleGrantedAuthority("SUPER_ADMIN"));
		roles.add(new SimpleGrantedAuthority("USER"));
		
		return roles;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		return super.getUserName();
	}

	// 账户是否未过期
	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	// 账户是否未锁定
	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	// 密码是否未过期
	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 账户是否激活
	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

}
