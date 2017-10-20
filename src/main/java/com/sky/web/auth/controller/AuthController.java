package com.sky.web.auth.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.sky.annotation.RestfulApi;
import com.sky.base.ResponseEntity;
import com.sky.redis.RedisUtil;
import com.sky.utils.MD5;
import com.sky.utils.U;
import com.sky.web.auth.request.RegisterReq;
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
	private RedisUtil redisUtil;

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
		redisUtil.set(token, user, 3600L);

		return ResponseEntity.success("登录成功", token);
	}

	@ApiOperation("用户注册")
	@PostMapping("/register")
	public ResponseEntity register(RegisterReq registerReq) {
		
		// 1.转化md5密码格式
		registerReq.setPassword(MD5.encode(registerReq.getPassword()));
		
		// 2.初始化用户信息
		User user = new User();
		BeanUtils.copyProperties(registerReq, user);
		
		// 3.注册用户
		user = userService.register(user);

		return ResponseEntity.success("注册成功", user);
	}

	@ApiOperation("解析token")
	@PostMapping("/parse")
	public ResponseEntity parseToken(@RequestParam String token) {
		Claims result = authService.parser(token);
		return ResponseEntity.success("解析成功", result);
	}
	
	@ApiOperation("测试Redis")
	@PostMapping("/redis")
	public ResponseEntity testRedis(@RequestParam String token) {
		Object object = redisUtil.get(token);
		return ResponseEntity.success("获取成功", object);
	}
	
	

}
