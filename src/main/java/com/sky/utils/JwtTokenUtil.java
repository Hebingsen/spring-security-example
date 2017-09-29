package com.sky.utils;


import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.sky.exception.AuthException;
import com.sky.security.SecurityUser;
import com.xiaoleilu.hutool.lang.Base64;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * jwt 工具类
 * @作者 乐此不彼
 * @时间 2017年9月28日
 * @公司 sky工作室
 */
@Component
public class JwtTokenUtil {
	
	@Autowired
	private Environment env;
	
	/**
	 * 根据token获取用户名
	 * @param token
	 * @return
	 */
	public String getUsernameFormToken(String token) {
		String username = parser(token).get("username", String.class);
		return username;
	}
	
	/**
	 * 根据token解析jwt内容
	 * @param token
	 * @return
	 */
	public Claims parser(String token) {
		Claims claims = Jwts.parser().setSigningKey(env.getProperty("jwt.secret")).parseClaimsJws(token).getBody();
		if(claims == null)
			throw new AuthException(500, "token解析异常");
		return claims;
	}

	/**
	 * 根据用户详情生成token
	 * @param userDetails
	 * @return
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String,Object> claims = new HashMap<String,Object>();
		claims.put("username", userDetails.getUsername());
		claims.put("authorities", userDetails.getAuthorities());
		String compact = Jwts.builder().signWith(SignatureAlgorithm.HS512, env.getProperty("jwt.secret"))
				.setClaims(claims)
				.compact();
		return compact;
	}
	
	/**
	 * 校验token
	 * 1.校验用户详情的用户名是否与token解析的用户名相等
	 * 2.校验token是否已经过期
	 * @param userDetails 	用户详情信息
	 * @param token			用户token
	 * @return
	 */
	public boolean validateToken(UserDetails userDetails, String token) {
		return userDetails.getUsername().equals(getUsernameFormToken(token));
	}

	
	public static void main(String[] args) {
		SecretKey key = MacProvider.generateKey();
		
		String compact = Jwts.builder().signWith(SignatureAlgorithm.HS512, Base64.encode("bingsen.he")).claim("username", "何炳森").compact();
		
		String username = Jwts.parser().setSigningKey(Base64.encode("bingsen.he")).parseClaimsJws(compact).getBody().get("username", String.class);
		
		System.out.println("username="+username);
		
		System.out.println(Base64.encode("bingsen.he"));
	}
	
}
