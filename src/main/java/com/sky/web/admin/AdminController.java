package com.sky.web.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.annotation.RestfulApi;
import com.sky.base.ResponseEntity;
import com.sky.exception.AuthException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 只有用户角色为admin时才可以进行访问
 * 在 @PreAuthorize 中我们可以利用内建的 SPEL 表达式：比如 'hasRole()' 来决定哪些用户有权访问。
 * 需注意的一点是 hasRole 表达式认为每个角色名字前都有一个前缀 'ROLE_'。所以这里的 'ADMIN' 其实在
 * 数据库中存储的是 'ROLE_ADMIN' 。这个 @PreAuthorize 可以修饰Controller也可修饰Controller中的方法。
 * 注:需要开启@EnableGlobalMethodSecurity(prePostEnabled = true)的支持
 * @作者 乐此不彼
 * @时间 2017年9月28日
 * @公司 sky工作室
 */
@RestfulApi("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@Api(description = "管理模块")
public class AdminController {
	
	@ApiOperation(value = "访问管理员接口")
	@GetMapping("/")
	public Object get() {
		return ResponseEntity.success("正在访问AdminController");
	}
	
}
