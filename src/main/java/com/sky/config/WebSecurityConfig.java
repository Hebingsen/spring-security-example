package com.sky.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.sky.security.Md5PasswordEncoder;
import com.sky.security.MyUserDetailsService;
import com.sky.security.handler.LoginFailureHandler;
import com.sky.security.handler.LoginSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	/**
	 * 鉴权规则
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				// 1./home与/不需要鉴权
				.antMatchers("/", "/home", "/api/user/**").permitAll()
				// 2.其他都需要鉴权
				.anyRequest().authenticated().and()
				// 3.表单登录,permitAll()无需鉴权
				.formLogin()
				// 登录成功的处理
				.successHandler(new LoginSuccessHandler()).failureHandler(new LoginFailureHandler())
				// .loginPage("/login")
				.permitAll().and()
				// 4.退出登录操作,默认/logout
				.logout().logoutUrl("/logout").permitAll();

	}

	/**
	 * 配置一个内存中的用户认证器,后续改为查询数据库
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// 配置一个储存于内存的用户认证器
		// auth.inMemoryAuthentication().withUser("admin").password("admin").roles("USER");

		// 创建自己自定义的用户认证器
		auth.userDetailsService(myUserDetailsService).passwordEncoder(md5PasswordEncode());
	}
	
	/**
	 * 指定加密算法,官方是推荐我们使用BCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
		return bCryptPasswordEncoder;
	}
	
	/**
	 * 使用自己自定义的md5密码加密器
	 */
	@Bean
	public Md5PasswordEncoder md5PasswordEncode() {
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		return md5PasswordEncoder;
	}

}
