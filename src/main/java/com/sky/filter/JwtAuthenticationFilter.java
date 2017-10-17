package com.sky.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sky.exception.AuthException;
import com.sky.handler.Handler401Exception;
import com.sky.redis.RedisUtil;
import com.sky.security.MyUserDetailsService;
import com.sky.security.SecurityUser;
import com.sky.utils.JwtTokenUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * jwt鉴权过滤器
 * 
 * @作者 乐此不彼
 * @时间 2017年9月28日
 * @公司 sky工作室
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Value("${jwt.header}")
	private String tokenHeader;

	@Value("${jwt.token-head}")
	private String jwtHead;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private Handler401Exception authenticationEntryPoint;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 是否放行请求标记
		boolean passFlag = true;

		log.info("请求经过JwtAuthenticationFilter过滤器.....");

		String url = request.getRequestURI();
		log.info("当前访问的url = {}", url);

		// 1.获取token请求头
		final String authHeader = request.getHeader(tokenHeader);

		if (StrUtil.isNotBlank(authHeader) && authHeader.startsWith(jwtHead)) {
			
			// 通过规则截取真正的token
			String authToken = null;
			try {
				authToken = authHeader.substring(jwtHead.length(), authHeader.length());
			} catch (Exception e) {
				passFlag = false;
				AuthException authException = new AuthException(403, "非法令牌");
				authenticationEntryPoint.handler(request, response, authException);
			}
			
			// 判断token是否为空
			if(StrUtil.isBlank(authToken) && passFlag) {
				passFlag = false;
				AuthException authException = new AuthException(403, "令牌为空");
				authenticationEntryPoint.handler(request, response, authException);
			}

			// 判断token 是否已过期-->将token置为null
			if (!redisUtil.exists(authToken) && passFlag) {
				passFlag = false;
				AuthException authException = new AuthException(403, "无效令牌");
				authenticationEntryPoint.handler(request, response, authException);
			}

			// 设置认证信息
			if (passFlag) {
				check(request, authToken);
			}

		}

		if (passFlag) {
			// 放行请求
			filterChain.doFilter(request, response);
		}
	}

	/**
	 * 根据token校验信息
	 * 
	 * @param authToken
	 */
	public void check(HttpServletRequest request, String authToken) {

		// 3.根据token获取用户名
		final String username = jwtTokenUtil.getUsernameFormToken(authToken);
		log.info("根据token获取用户名={}", username);

		// 4.获取当前的security的身份认证(如果身份认证为空的话,即代表无权限,只能访问"匿名访问资源")
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// 5.如果通过token能获取到用户信息,则设置当前security的身份认证
		if (StrUtil.isNotBlank(username) && authentication == null) {
			// 5.1.获取用户详情
			SecurityUser userDetails = (SecurityUser) userDetailsService.loadUserByUsername(username);
			// 5.2.将token与用户详情进行校验
			boolean flag = jwtTokenUtil.validateToken(userDetails, authToken);
			// 5.3.通过检验,则设置通过当前的身份认证
			if (flag) {
				setAuthentication(request, userDetails);
			}
		}
	}

	/**
	 * 设置认证信息
	 */
	public void setAuthentication(HttpServletRequest request, SecurityUser userDetails) {
		// 用户名,密码(在此不做存储),设置权限角色表示通过验证
		UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(
				userDetails.getUsername(), null, userDetails.getAuthorities());

		// 当它希望创建一个新的身份验证细节实例时被一个类调用。
		WebAuthenticationDetails webAuthenticationDetails = new WebAuthenticationDetailsSource().buildDetails(request);
		userAuthentication.setDetails(webAuthenticationDetails);
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);
	}

}
