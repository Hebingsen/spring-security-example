package com.sky.handler;

import javax.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.sky.base.ResponseEntity;
import com.sky.exception.AuthException;
import com.sky.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class HandlerGlobalException {

	/**
	 * 处理默认异常信息
	 */
	@ExceptionHandler(value = Exception.class)
	public Object handlerDefaultException(HttpServletRequest request, Exception e) throws Exception {
		log.error("异常信息:{}", e.getMessage());
		return ResponseEntity.fail(500, e.getMessage()).toJson();
	}
	
	
	/**
	 * 数据库异常
	 */
	@ExceptionHandler(value = DataAccessException.class)
	public Object handlerDataAccessException(HttpServletRequest request, DataAccessException e) throws Exception {
		log.error("异常信息:{}", e.getMessage());
		return ResponseEntity.fail(500, "数据库服务器错误");
	}
	

	/**
	 * 处理认证相关异常
	 */
	@ExceptionHandler(value = AuthException.class)
	public Object handlerUserAuthException(HttpServletRequest request, AuthException e) throws Exception {
		log.error("异常信息:{},异常状态码:{}", e.getMessage(), e.getCode());
		return ResponseEntity.fail(e.getCode(), e.getMessage());
	}

	/**
	 * 处理业务异常
	 */
	@ExceptionHandler(value = ServiceException.class)
	public Object handlerServiceException(HttpServletRequest request, ServiceException e) throws Exception {
		log.error("异常信息:{},异常状态码:{}", e.getMessage(), e.getCode());
		return ResponseEntity.fail(e.getCode(), e.getMessage());
	}
	
	/**
	 * 处理用户名不存在异常
	 */
	@ExceptionHandler(value = UsernameNotFoundException.class)
	public Object handlerUsernameNotFoundException(HttpServletRequest request, UsernameNotFoundException e) throws Exception {
		log.error("异常信息:{}", e.getMessage());
		return ResponseEntity.fail(500, e.getMessage());
	}
	
	
}
