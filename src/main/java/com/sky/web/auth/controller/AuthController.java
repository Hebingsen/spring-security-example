package com.sky.web.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.sky.annotation.RestfulApi;
import com.sky.base.ResponseEntity;
import com.sky.redis.RedisUtil;
import com.sky.utils.JwtTokenUtil;
import com.sky.utils.MD5;
import com.sky.web.auth.request.RegisterReq;
import com.sky.web.auth.service.AuthService;
import com.sky.web.user.pojo.User;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestfulApi("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private JwtTokenUtil jwt;

	/**
	 * 登录
	 * @param phone 登录名
	 * @param password
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity login(String phone, String password) {
		
		log.info("用户登录,phone={},password={}", phone, password);

		String token = authService.login(phone, MD5.encode(password));
		log.info("用户登录成功,生成token={}", token);

		//将成功生成后的token存储进去redis中进行管理
		String now = String.valueOf(System.currentTimeMillis());
		redisUtil.set(token, now, 3600L);
		
		return ResponseEntity.success("登录成功", token);
	}

	/**
	 * 注册
	 * 
	 * @param userReq
	 * @return
	 */
	@PostMapping("/register")
	public ResponseEntity register(RegisterReq registerReq) {
		log.info("注册用户,请求信息:{}", registerReq);

		registerReq.setPassword(MD5.encode(registerReq.getPassword()));
		User user = authService.register(registerReq);

		return ResponseEntity.success("注册成功", user);
	}
	
	
	/**
	 * 解析token
	 * @param token
	 */
	@PostMapping("/parse")
	public ResponseEntity parseToken(String token) {
		Claims parser = jwt.parser(token);
		return ResponseEntity.success("解析成功",parser);
	}
	

}
