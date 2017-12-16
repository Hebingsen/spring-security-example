package com.sky;

import javax.crypto.SecretKey;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GateWayApplicationTests {

	@Test
	public void contextLoads() {
		
		/**
		 * 生成秘钥
		 */
		SecretKey key = MacProvider.generateKey();
		System.out.println("生成秘钥key : " + key);

		/**
		 * 生成token
		 */
		String token = Jwts.builder().setSubject("bingsen.he").signWith(SignatureAlgorithm.HS512, key).compact();
		System.err.println(token);
		
		/**
		 * 解析token
		 */
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		String subject = claims.getSubject();
		System.err.println(claims);
		System.err.println(subject);
		
	}

}
