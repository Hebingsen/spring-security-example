package com.sky.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sky.auth.filter.JwtAuthenticationFilter;
import com.sky.auth.handler.Handler401Exception;
import com.sky.auth.handler.Handler403Exception;
import com.sky.auth.security.Md5PasswordEncoder;
import com.sky.auth.security.MyUserDetailsService;

/**
 * 
 * @作者 乐此不彼
 * @时间 2017年9月29日
 * @公司 sky工作室
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private Handler403Exception accessDeniedHandler;

	@Autowired
	private Handler401Exception entryPointUnauthorizedHandler;
	
	/**
	 * 鉴权规则
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 使用jwt,不存在csrf的问题
		http.csrf().disable();

		// 禁用缓存
		http.headers().cacheControl();

		// 添加JwtFilter
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		// 基于token，所以不需要session
		// SessionCreationPolicy:表示会话创建政策,STATELESS:表示无状态
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and()
				// 鉴权规则
				.authorizeRequests()
				// 匹配无须鉴权的规则:/home,/,静态资源允许匿名访问
				.antMatchers(HttpMethod.GET, "/", "/home").permitAll()

				// 对于获取token的rest api要允许匿名访问
				.antMatchers("/auth/**").permitAll()

				// 除上面外的所有请求全部需要鉴权认证
				.anyRequest().authenticated();

		// 配置401与403错误处理器
		http.exceptionHandling().authenticationEntryPoint(entryPointUnauthorizedHandler)
				.accessDeniedHandler(accessDeniedHandler);
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
	 * WebSecurity配置,比如拦截的匹配规则等等..
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		// swagger 在线api文档路径规则
		web.ignoring().antMatchers("/v2/api-docs"
				,"/swagger-resources/configuration"
				,"/swagger-resources"
				,"/swagger-resources/configuration/security"
				,"/swagger-ui.html"
				,"/webjars/**"
				,"/images/**");
		//web.ignoring().antMatchers("/configuration/**", "/images/**", "/v2/api-docs", "/swagger-ui.html",
				//"/swagger-resources/**", "/webjars/**");

		// 静态资源
		web.ignoring().antMatchers("/resource/**", "/favicon.ico", "/**/*.html", "/**/*.js", "/**/*.css");
		
	}

	/**
	 * json web token 鉴权过滤器
	 * @return
	 */
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
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
