package com.sky.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sky.base.ResponseEntity;

/**
 * 定义 403 处理器，实现 AccessDeniedHandler 接口
 * 
 * @作者 乐此不彼
 * @时间 2017年10月12日
 * @公司 sky工作室
 */
@Component
public class Handler403Exception implements AccessDeniedHandler {

	/**
	 * 无权限触发的操作
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// 返回json形式的错误信息
		String result = ResponseEntity.fail(401, "权限不足").toJson();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().println(result);
		response.getWriter().flush();
	}

}
