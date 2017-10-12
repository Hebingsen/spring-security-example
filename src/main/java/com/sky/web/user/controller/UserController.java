package com.sky.web.user.controller;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sky.base.ResponseEntity;

/**
 * 用户API接口,需要有ROLE_USER权限才可以进行访问@PreAuthorize("hasRole('USER')")
 * @作者 乐此不彼
 * @时间 2017年10月12日
 * @公司 sky工作室
 */
@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
public class UserController {

	@Autowired
	private HttpServletRequest request;

	/**
	 * 获取当前登录用户信息
	 */
	@GetMapping("/login-info")
	public Object loginInfo() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.err.println("principal=" + principal);
		
		Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
		System.err.println("credentials=" + credentials);
		
		Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
		System.err.println("details=" + details);
		
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		System.err.println("authorities=" + authorities);

		boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
		System.err.println("authenticated=" + authenticated);

		String header = request.getHeader("token");
		System.err.println("header=" + header);

		return ResponseEntity.success("登录信息", details);
	}

}
