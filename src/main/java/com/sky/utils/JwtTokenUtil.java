package com.sky.utils;


import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import com.sky.exception.AuthException;
import com.sky.exception.ServiceException;
import com.sky.handler.Handler401Exception;
import com.sky.security.SecurityUser;
import com.xiaoleilu.hutool.lang.Base64;
import com.xiaoleilu.hutool.util.StrUtil;
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
	
	@Autowired
	private Handler401Exception authenticationEntryPoint;
	
	/**
	 * 根据token获取登录名
	 * @param token
	 * @return
	 */
	public String getUsernameFormToken(String token) {
		if(StrUtil.isBlank(token)) return null;
		String username = parser(token).get("phone", String.class);
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
	public String generateToken(SecurityUser userDetails) {
		Map<String,Object> claims = new HashMap<String,Object>();
		claims.put("username", userDetails.getUsername());//用户名
		claims.put("authorities", userDetails.getAuthorities());//角色权限信息
		claims.put("phone", userDetails.getPhone());
		claims.put("id", userDetails.getId());
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
	public boolean validateToken(SecurityUser userDetails, String token) {
		return userDetails.getPhone().equals(getUsernameFormToken(token));
	}

	
	public static void main(String[] args) {
		SecretKey key = MacProvider.generateKey();
		
		String compact = Jwts.builder().signWith(SignatureAlgorithm.HS512, Base64.encode("bingsen.he")).claim("username", "何炳森").compact();
		
		String username = Jwts.parser().setSigningKey(Base64.encode("bingsen.he")).parseClaimsJws(compact).getBody().get("username", String.class);
		
		System.out.println("username="+username);
		
		System.out.println(Base64.encode("bingsen.he"));
		
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJ1c2VybmFtZSI6IuS5kOatpOS4jeW9vCJ9.7WmjCxh46p7Mh-WeguoqB_tvbRdVLv8Q-LLzf3EfZQ_zclZp"
		+ "c_yNXkl5WIebMD8TuBwAAxJIH8ZG_Lw_5CNVdg";
		Claims parser = new JwtTokenUtil().parser(token);
		System.err.println(parser);
	}
	
}
