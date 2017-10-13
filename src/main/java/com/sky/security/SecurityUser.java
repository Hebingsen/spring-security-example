package com.sky.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sky.web.user.pojo.Role;
import com.sky.web.user.pojo.User;

public class SecurityUser extends User implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public SecurityUser(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	public SecurityUser(Long id, String userName, String password, String phone, Date createTime, List<Role> roles) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.phone = phone;
		this.createTime = createTime;
		List<GrantedAuthority> grantedAuthoritys =  new ArrayList<GrantedAuthority>();
		for (Role role : roles) {
			grantedAuthoritys.add(new SimpleGrantedAuthority(role.getRoleCode()));
		}
		this.authorities = grantedAuthoritys;
	}
	
	public SecurityUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 查询并返回权限信息
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
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
