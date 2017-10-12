package com.sky.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import com.google.gson.Gson;
import com.sky.base.ResponseEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录失败处理,可在此返回json格式
 * 
 * @作者 乐此不彼
 * @时间 2017年9月27日
 * @公司 sky工作室
 */
@Slf4j
public class HandlerLoginFailure implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		String username = request.getParameter("username");
		log.info(username + "登录失败");
		ResponseEntity responseEntity = ResponseEntity.fail(405, username + "登录失败");
		String result = new Gson().toJson(responseEntity);

		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(result);
		writer.flush();
		writer.close();
	}

}
