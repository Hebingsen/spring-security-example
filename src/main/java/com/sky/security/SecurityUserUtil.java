package com.sky.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.sky.redis.RedisUtil;
import com.sky.utils.U;
import com.sky.web.user.pojo.User;
import com.xiaoleilu.hutool.util.StrUtil;

/**
 * 
 * @作者 乐此不彼
 * @时间 2017年10月20日
 * @公司 sky工作室
 */
@Component
public class SecurityUserUtil {

	@Value("${jwt.token-head}")
	private String jwtHead;

	@Autowired
	private RedisUtil redis;

	/**
	 * 处理token
	 * 
	 * @param token
	 * @return
	 */
	public String dealToken(String token) {
		// 通过规则截取真正的token
		return token.substring(jwtHead.length(), token.length());
	}

	/**
	 * 根据token获取当前用户信息
	 * 
	 * @param token
	 * @return
	 */
	public User getUser(String token) {
		
		token = dealToken(token);
		U.assertException(StrUtil.isBlank(token), "需要解析的令牌为空");

		U.assertException(!redis.exists(token), "用户登录信息不存在");

		//User user = redis.getByClass(token, User.class);
		User user = (User)redis.get(token);
		U.assertException(user == null, "用户登录信息为空");

		return user;
	}

	/**
	 * 获取用户id
	 * 
	 * @param token
	 * @return
	 */
	public Long getUserId(String token) {
		return getUser(token).getId();
	}

	/**
	 * 获取用户名
	 * 
	 * @param token
	 * @return
	 */
	public String getUserName(String token) {
		return getUser(token).getUserName();
	}

	/**
	 * 获取用户名
	 * 
	 * @param token
	 * @return
	 */
	public String getUserPhone(String token) {
		return getUser(token).getPhone();
	}

}
