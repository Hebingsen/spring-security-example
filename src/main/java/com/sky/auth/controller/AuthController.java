package com.sky.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sky.auth.service.AuthService;
import com.sky.base.ResponseEntity;
import com.sky.redis.RedisUtil;
import com.sky.utils.MD5;
import com.sky.web.user.pojo.User;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity login(String username, String password) {
		log.info("用户登录,username={},password={}",username,password);
		
		String token = authService.login(username, MD5.encode(password));
		log.info("用户登录成功,生成token={}",token);
		
		/**
		 * 将成功生成后的token存储进去redis中进行管理
		 */
		String now = String.valueOf(System.currentTimeMillis());
		redisUtil.set(token,now, 3600L);
		return ResponseEntity.success("登录成功", token);
	}

	/**
	 * 注册
	 * @param userReq
	 * @return
	 */
	@PostMapping("/register")
	public ResponseEntity register(User userReq) {
		log.info("注册用户,请求信息:{}",userReq);
		
		User user = authService.register(userReq);
		
		return ResponseEntity.success("注册成功", user);
	}

}
