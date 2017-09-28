package com.sky.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 默认异常信息处理
	 * 
	 * @param request
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = Exception.class)
	public Object defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
		e.printStackTrace();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("code", 500);
		result.put("msg", "发生异常 : " + e.getMessage());
		return result;
	}

	
	
	/**
	 * 业务异常信息处理
	 * 
	 * @param request
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = UserAuthenticationException.class)
	public Object serviceExceptionHandler(HttpServletRequest request, UserAuthenticationException e) throws Exception {
		e.printStackTrace();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("code", e.getCode());
		result.put("msg", "发生异常 : " + e.getMessage());
		return result;
	}
}
