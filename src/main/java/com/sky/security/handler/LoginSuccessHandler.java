package com.sky.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.google.gson.Gson;
import com.sky.base.ResponseEntity;
import com.sky.security.SecurityUser;

import lombok.extern.slf4j.Slf4j;

/**
 * 登录成功处理,可在此返回json格式
 * 
 * @作者 乐此不彼
 * @时间 2017年9月27日
 * @公司 sky工作室
 */
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.info("登录成功");
		
		SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ResponseEntity responseEntity = ResponseEntity.success("登录成功", securityUser);
		String result = new Gson().toJson(responseEntity);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(result);
		writer.flush();
		writer.close();
	}
}
