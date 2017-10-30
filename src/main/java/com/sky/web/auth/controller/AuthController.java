package com.sky.web.auth.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import com.sky.annotation.RestfulApi;
import com.sky.base.ResponseEntity;
import com.sky.security.SecurityUserUtil;
import com.sky.utils.MD5;
import com.sky.utils.U;
import com.sky.web.auth.service.AuthService;
import com.sky.web.user.pojo.User;
import com.sky.web.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @作者 乐此不彼
 * @时间 2017年10月15日
 * @公司 sky工作室
 */
@Slf4j
@RestfulApi("/auth")
@Api(description = "鉴权模块")
public class AuthController {

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityUserUtil securityUserUtil;

	@Autowired
	private HttpServletRequest request;

	@Value("${jwt.header}")
	private String tokenHeader;

	@ApiOperation("用户登录")
	@PostMapping("/login")
	public ResponseEntity login(@RequestParam String phone, @RequestParam String password) {

		// 1.生成token
		String token = authService.login(phone, MD5.encode(password));
		log.info("用户登录成功,生成token={}", token);

		// 2.根据手机号获取用户信息
		User user = userService.findByPhone(phone);
		user.setLastLoginTime(U.now());
		userService.updateUser(user);

		// 3.将成功生成后的token存储进去redis中进行管理
		securityUserUtil.storeToken(token, user, 3600L);
		
		return ResponseEntity.success("登录成功", token);
	}

	@ApiOperation("退出登录")
	@PostMapping("/logout")
	public ResponseEntity logout() {
		boolean result = securityUserUtil.clearToken(request.getHeader(tokenHeader));
		if (result)
			return ResponseEntity.success("退出成功");
		else
			return ResponseEntity.fail(500, "退出失败");
	}
	
	@ApiOperation("刷新令牌")
	@PostMapping("/refresh")
	public ResponseEntity refresh(@RequestHeader(name = "refreshToken") String refreshToken) {
		String newToken = authService.refresh(refreshToken);
		return ResponseEntity.success("令牌刷新成功",newToken);
	}

	
	@ApiOperation("解析token")
	@PostMapping("/parse")
	public ResponseEntity parseToken(@RequestParam String token) {
		Claims result = authService.parser(token);
		return ResponseEntity.success("解析成功", result);
	}

}
