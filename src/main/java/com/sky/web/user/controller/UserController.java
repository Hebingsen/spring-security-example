package com.sky.web.user.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import com.sky.annotation.RestfulApi;
import com.sky.base.ResponseEntity;
import com.sky.security.SecurityUserUtil;
import com.sky.web.user.response.UserResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户API接口,需要有ROLE_USER权限才可以进行访问@PreAuthorize("hasRole('USER')")
 * @作者 乐此不彼
 * @时间 2017年10月12日
 * @公司 sky工作室
 */
@RestfulApi(path = "/api/user")
@PreAuthorize("hasRole('USER')")
@Api(description = "用户模块")
@Slf4j
public class UserController {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private SecurityUserUtil securityUserUtil;
	
	@Value("${jwt.header}")
	private String tokenHeader;
	
	
	@ApiOperation("获取当前登录用户信息")
	@GetMapping("/login-info")
	public Object loginInfo() {
		
		// 当前存储于security里的认证信息
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info("principal={},credentials={},details={},authorities={},authenticated={}",authentication.getPrincipal(),authentication.getCredentials()
				,authentication.getDetails(),authentication.getAuthorities(),authentication.isAuthenticated());
		
		// 根据token获取当前登录用户
		UserResp userResp = new UserResp();
		BeanUtils.copyProperties(securityUserUtil.getUser(request.getHeader(tokenHeader)), userResp);
		return ResponseEntity.success("查询用户登录信息", userResp);
	}

}
