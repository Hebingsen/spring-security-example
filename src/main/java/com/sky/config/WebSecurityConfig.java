package com.sky.config;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.sky.exception.UserAuthenticationException;
import com.sky.security.Md5PasswordEncoder;
import com.sky.security.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
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
	

	/**
	 * 登录成功处理,可在此返回json格式
	 * 
	 * @作者 乐此不彼
	 * @时间 2017年9月27日
	 * @公司 sky工作室
	 */
	static class LoginSuccessHandler implements AuthenticationSuccessHandler {

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws IOException, ServletException {
			log.info("登录成功");
			PrintWriter writer = response.getWriter();
			writer.println("恭喜你登录成功,用户信息:"+SecurityContextHolder.getContext().getAuthentication().getName());
			writer.flush();
			writer.close();
		}

	}

	/**
	 * 登录失败处理,可在此返回json格式
	 * 
	 * @作者 乐此不彼
	 * @时间 2017年9月27日
	 * @公司 sky工作室
	 */
	static class LoginFailureHandler implements AuthenticationFailureHandler {

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			throw new UserAuthenticationException(1001,"用户登录失败");
		}

	}

}
