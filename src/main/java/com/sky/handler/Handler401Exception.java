package com.sky.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.sky.base.ResponseEntity;
import com.sky.exception.AuthException;
import com.xiaoleilu.hutool.http.HttpStatus;

/**
 * 定义 401 处理器，实现 AuthenticationEntryPoint 接口
 * 
 * @作者 乐此不彼
 * @时间 2017年10月12日
 * @公司 sky工作室
 */
@Component
public class Handler401Exception implements AuthenticationEntryPoint {

	/**
	 * 未登录时触发的操作
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		String msg = "请先登录";
		int code = HttpStatus.HTTP_UNAUTHORIZED;
		
		if(authException instanceof AuthException) {
			msg = authException.getMessage();
			code = ((AuthException) authException).getCode();
		}
		
		// 返回json形式的错误信息
		String result = ResponseEntity.fail(code, msg).toJson();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().println(result);
		response.getWriter().flush();
	}
	
	
	public void handler(HttpServletRequest request, HttpServletResponse response,AuthenticationException authException) throws IOException, ServletException {
		commence(request, response, authException);
	}

}
