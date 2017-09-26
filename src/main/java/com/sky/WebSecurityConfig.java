package com.sky;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	/**
	 * 鉴权规则
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		//1./home与/不需要鉴权
		.antMatchers("/","/home").permitAll()
		//2.其他都需要鉴权
		.anyRequest().authenticated()
		.and()
		//3.表单登录,permitAll()无需鉴权
		.formLogin().loginPage("/login").permitAll()
		.and()
		//4.退出登录操作,默认/logout
		.logout().logoutUrl("/logout").permitAll();
		
	}
	
	
	/**
	 * 用户信息,先将用户保存在内存中,后续改为查询数据库
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("USER");
	}
	
	
	
	
}
